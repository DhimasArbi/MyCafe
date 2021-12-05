package org.aplas.sanasinicoffee;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.aplas.sanasinicoffee.Adapter.CartAdapter;
import org.aplas.sanasinicoffee.Model.CartModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CartFragment extends Fragment {

    CartAdapter mAdapter;
    RecyclerView recyclerView;
    FirebaseFirestore firestore;
    Button orderbutton;
    TextView orderSummary;
    NavController navController;

    List<CartModel> cartModelList = new ArrayList<>();
    int totalOrderCost = 0;
    List<Integer> saveTotalCost = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        firestore = FirebaseFirestore.getInstance();
        mAdapter = new CartAdapter();
        orderbutton = view.findViewById(R.id.orderNow);
        recyclerView = view.findViewById(R.id.cartRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orderSummary = view.findViewById(R.id.orderSummary);

        firestore.collection("Cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                cartModelList.clear();
                if (task.isSuccessful()) {
                    for (DocumentSnapshot ds: task.getResult().getDocuments()) {
                        CartModel cartModel = ds.toObject(CartModel.class);
                        cartModelList.add(cartModel);
                        mAdapter.setCartModelList(cartModelList);
                        recyclerView.setAdapter(mAdapter);
                    }
                }
            }
        });

        firestore.collection("Cart").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot value,  FirebaseFirestoreException error) {
                if (value!=null) {
                    for (DocumentSnapshot ds: value.getDocuments()) {
                        CartModel cartModel = ds.toObject(CartModel.class);
                        // we are adding all prices into a list of an integers
                        int valueofallprices = cartModel.getTotalHarga();
                        saveTotalCost.add(valueofallprices);
                    }

                    for (int i=0; i <saveTotalCost.size(); i++) {
                        totalOrderCost += Integer.parseInt(String.valueOf(saveTotalCost.get(i)));
                    }
                    orderSummary.setText("Rp "  +totalOrderCost);
                    totalOrderCost = 0;
                    saveTotalCost.clear();
                }
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove( RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped( RecyclerView.ViewHolder viewHolder, int direction) {
                int posisi = viewHolder.getAdapterPosition();
                CartModel deleteitem = cartModelList.get(posisi);


                if (cartModelList.get(posisi).getCategory().equalsIgnoreCase("Kopi")){
                    firestore.collection("Coffee").document(deleteitem.getId()).update("jumlah", 0);
                    firestore.collection("Coffee").document(deleteitem.getId()).update("jenis", "Panas");
                    firestore.collection("Coffee").document(deleteitem.getId()).update("ukuran", "Kecil");
                }else{
                    firestore.collection("Cake").document(deleteitem.getId()).update("jumlah", 0);
                }

                firestore.collection("Cart").document(deleteitem.getId()).delete();
                cartModelList.remove(viewHolder.getAdapterPosition());


                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                Snackbar.make(recyclerView, deleteitem.getNama(), Snackbar.LENGTH_LONG).setAction("Batal", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cartModelList.add(posisi, deleteitem);
                        mAdapter.notifyItemInserted(posisi);
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("nama", cartModelList.get(posisi).getNama());
                        hashMap.put("jumlah", cartModelList.get(posisi).getJumlah());
                        hashMap.put("totalHarga", cartModelList.get(posisi).getTotalHarga());
                        hashMap.put("id", cartModelList.get(posisi).getId());
                        hashMap.put("gambar", cartModelList.get(posisi).getGambar());
                        hashMap.put("category", cartModelList.get(posisi).getCategory());
                        firestore.collection("Cart").document(cartModelList.get(posisi).getNama()).set(hashMap);
                        if (cartModelList.get(posisi).getCategory().equalsIgnoreCase("kopi")){
                            hashMap.put("ukuran", cartModelList.get(posisi).getUkuran());
                            hashMap.put("jenis", cartModelList.get(posisi).getJenis());
                            firestore.collection("Coffee").document(cartModelList.get(posisi).getNama()).update("jumlah", cartModelList.get(posisi).getJumlah());
                            firestore.collection("Coffee").document(cartModelList.get(posisi).getNama()).update("jenis", cartModelList.get(posisi).getJenis());
                            firestore.collection("Coffee").document(cartModelList.get(posisi).getNama()).update("ukuran", cartModelList.get(posisi).getUkuran());
                        }else if (cartModelList.get(posisi).getCategory().equalsIgnoreCase("Kue")){
                            firestore.collection("Cake").document(cartModelList.get(posisi).getNama()).update("jumlah", cartModelList.get(posisi).getJumlah());
                        }

                    }
                }).show();
            }
        }).attachToRecyclerView(recyclerView);

        orderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartModelList.isEmpty()){
                    Toast.makeText(getContext(), "Cart kosong!!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "Silahkan pilih menu terlebih dahulu!!", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.action_cartFragment_to_coffeeListFragment);
                }else {
                    navController.navigate(R.id.action_cartFragment_to_checkout_styles);
                }
            }
        });

    }
}