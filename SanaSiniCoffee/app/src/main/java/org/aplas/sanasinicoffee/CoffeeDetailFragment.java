package org.aplas.sanasinicoffee;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.aplas.sanasinicoffee.Model.CoffeeModel;

import java.util.HashMap;


public class CoffeeDetailFragment extends Fragment {


    NavController navController;
    int jumlah;
    FirebaseFirestore firebaseFirestore;
    Button add, sub, order;
    TextView nama, deskripsi, quantity, orderInfo;
    ImageView gambar;
    String coffeeid, namakopi, deskripsikopi, gambarkopi;
    int harga = 0;
    int totalHarga = 0;

    public CoffeeDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coffee_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gambar = view.findViewById(R.id.CoffeeDetailImage);
        nama = view.findViewById(R.id.coffeenamedetail);
        deskripsi = view.findViewById(R.id.coffeedetaildetail);
        add = view.findViewById(R.id.incrementcoffee);
        sub = view.findViewById(R.id.decrementcoffee);
        quantity = view.findViewById(R.id.quantityDETAILnUMBER);
        firebaseFirestore = FirebaseFirestore.getInstance();
        navController = Navigation.findNavController(view);
        order = view.findViewById(R.id.orderdetail);
        orderInfo = view.findViewById(R.id.orderINFO);

        namakopi = CoffeeDetailFragmentArgs.fromBundle(getArguments()).getNama();
        coffeeid = CoffeeDetailFragmentArgs.fromBundle(getArguments()).getId();
        gambarkopi = CoffeeDetailFragmentArgs.fromBundle(getArguments()).getGambar();
        deskripsikopi = CoffeeDetailFragmentArgs.fromBundle(getArguments()).getDeskripsi();
        harga = CoffeeDetailFragmentArgs.fromBundle(getArguments()).getHarga();

        Glide.with(view.getContext()).load(gambarkopi).into(gambar);
        nama.setText(namakopi + " Rp " + harga);
        deskripsi.setText(deskripsikopi);

        firebaseFirestore.collection("Coffee").document(coffeeid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, FirebaseFirestoreException error) {
                CoffeeModel coffeeModel = value.toObject(CoffeeModel.class);
                jumlah = coffeeModel.getJumlah();
                quantity.setText(String.valueOf(jumlah));
                orderInfo.setText("Total harga: Rp " + harga * jumlah);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jumlah == 10) {
                    Toast.makeText(getContext(), "Maksimal Order: 10", Toast.LENGTH_SHORT).show();
                } else {
                    jumlah++;
                    quantity.setText(String.valueOf(jumlah));

                    totalHarga = harga * jumlah;
                    orderInfo.setText("Total harga: Rp " + totalHarga);

                    firebaseFirestore.collection("Coffee").document(coffeeid).update("jumlah", jumlah).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {

                        }
                    });
                }

            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jumlah == 0) {
                    Toast.makeText(getContext(), "Minimal Order: 1", Toast.LENGTH_SHORT).show();
                    jumlah = 0;
                    totalHarga = 0;
                } else {
                    jumlah--;
                    quantity.setText(String.valueOf(jumlah));

                    totalHarga = harga * jumlah;
                    orderInfo.setText("Total harga: Rp " + totalHarga);

                    firebaseFirestore.collection("Coffee").document(coffeeid).update("jumlah", jumlah).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {

                        }
                    });
                }
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddToCart();
            }
        });
    }

    private void AddToCart() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("nama", namakopi);
        hashMap.put("jumlah", jumlah);
        hashMap.put("totalHarga", jumlah * harga);
        hashMap.put("coffeeid", coffeeid);
        hashMap.put("gambar", gambarkopi);

        firebaseFirestore.collection("Cart").document(namakopi).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Ditambahkan ke Keranjang", Toast.LENGTH_SHORT).show();
                    CoffeeDetailFragmentDirections.ActionCoffeeDetailFragmentToCoffeeListFragment
                            action = CoffeeDetailFragmentDirections.actionCoffeeDetailFragmentToCoffeeListFragment();
                    action.setJumlah(jumlah);
                    navController.navigate(action);
                }
            }
        });

    }


}