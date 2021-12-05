package org.aplas.sanasinicoffee;

import android.os.Bundle;

import androidx.annotation.NonNull;
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

import org.aplas.sanasinicoffee.Model.CakeModel;

import java.util.HashMap;

public class CakeDetailFragment extends Fragment {

    NavController navController;
    int jumlah;
    FirebaseFirestore firebaseFirestore;
    Button add, sub, order;
    TextView nama, deskripsi, quantity, orderInfo;
    ImageView gambar;
    String cakeid, namacake, deskripsicake, gambarcake;
    int harga = 0;
    int totalHarga = 0;

    public CakeDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cake_detail, container, false);
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nama = view.findViewById(R.id.cakenamedetail);
        deskripsi = view.findViewById(R.id.cakedetaildetail);
        gambar = view.findViewById(R.id.CakeDetailImage);
        add = view.findViewById(R.id.incrementcake);
        sub = view.findViewById(R.id.decrementcake);
        quantity = view.findViewById(R.id.qtyDetailNumber);
        firebaseFirestore = FirebaseFirestore.getInstance();
        navController = Navigation.findNavController(view);
        order = view.findViewById(R.id.ordercake);
        orderInfo = view.findViewById(R.id.orderCakeInfo);

        namacake = CoffeeDetailFragmentArgs.fromBundle(getArguments()).getNama();
        cakeid = CoffeeDetailFragmentArgs.fromBundle(getArguments()).getId();
        gambarcake = CoffeeDetailFragmentArgs.fromBundle(getArguments()).getGambar();
        deskripsicake = CoffeeDetailFragmentArgs.fromBundle(getArguments()).getDeskripsi();
        harga = CoffeeDetailFragmentArgs.fromBundle(getArguments()).getHarga();

        Glide.with(view.getContext()).load(gambarcake).into(gambar);
        nama.setText(namacake);
        deskripsi.setText(deskripsicake);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jumlah == 10) {
                    Toast.makeText(getContext(), "Maksimal Order: 10", Toast.LENGTH_SHORT).show();
                } else {
                    jumlah++;
                    quantity.setText(String.valueOf(jumlah));
                    totalHarga = harga * jumlah;
                    orderInfo.setText("Rp " + totalHarga);

                    firebaseFirestore.collection("Cake").document(cakeid).update("jumlah", jumlah).addOnCompleteListener(new OnCompleteListener<Void>() {
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

                    orderInfo.setText("Rp " + totalHarga);

                    firebaseFirestore.collection("Cake").document(cakeid).update("jumlah", jumlah).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        hashMap.put("nama", namacake);
        hashMap.put("jumlah", jumlah);
        hashMap.put("totalHarga", totalHarga);
        hashMap.put("id", cakeid);
        hashMap.put("gambar", gambarcake);
        hashMap.put("category", "Kue");
        if (jumlah == 0) {
            Toast.makeText(getContext(), "Minimal Order: 1", Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), "Menu tidak ditambahkan ke cart", Toast.LENGTH_SHORT).show();
            firebaseFirestore.collection("Cart").document(cakeid).delete();

            CakeDetailFragmentDirections.ActionCakeDetailFragmentToCakeListFragment
                    action = CakeDetailFragmentDirections.actionCakeDetailFragmentToCakeListFragment();
            action.setJumlah(jumlah);
            navController.navigate(action);
        } else {
            firebaseFirestore.collection("Cart").document(cakeid).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Ditambahkan ke Cart", Toast.LENGTH_SHORT).show();

                        CakeDetailFragmentDirections.ActionCakeDetailFragmentToCakeListFragment
                                action = CakeDetailFragmentDirections.actionCakeDetailFragmentToCakeListFragment();
                        action.setJumlah(jumlah);
                        navController.navigate(action);
                    }
                }
            });
        }
    }
}