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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.aplas.sanasinicoffee.Model.CartModel;
import org.aplas.sanasinicoffee.Model.CoffeeModel;

import java.util.HashMap;


public class CoffeeDetailFragment extends Fragment {


    NavController navController;
    int jumlah;
    FirebaseFirestore firebaseFirestore;
    Button add, sub, order;
    RadioButton rhot, rcold, rsmall, rmedium, rbig;
    RadioGroup rg1, rg2;
    TextView nama, deskripsi, quantity, orderInfo;
    ImageView gambar;
    String coffeeid, namakopi, deskripsikopi, gambarkopi, jenis, ukuran;
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
        rg1 = view.findViewById(R.id.rg1);
        rg2 = view.findViewById(R.id.rg2);
        rhot = view.findViewById(R.id.rhot);
        rcold = view.findViewById(R.id.rcold);
        rsmall = view.findViewById(R.id.smallcup);
        rmedium = view.findViewById(R.id.mediumcup);
        rbig = view.findViewById(R.id.bigcup);
        firebaseFirestore = FirebaseFirestore.getInstance();
        navController = Navigation.findNavController(view);
        order = view.findViewById(R.id.orderdetail);
        orderInfo = view.findViewById(R.id.orderINFO);

        namakopi = CoffeeDetailFragmentArgs.fromBundle(getArguments()).getNama();
        coffeeid = CoffeeDetailFragmentArgs.fromBundle(getArguments()).getId();
        gambarkopi = CoffeeDetailFragmentArgs.fromBundle(getArguments()).getGambar();
        deskripsikopi = CoffeeDetailFragmentArgs.fromBundle(getArguments()).getDeskripsi();
        harga = CoffeeDetailFragmentArgs.fromBundle(getArguments()).getHarga();
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rhot:
                        jenis = "Panas";
                        break;
                    case R.id.rcold:
                        jenis = "Dingin";
                        break;
                }
                firebaseFirestore.collection("Coffee").document(coffeeid).update("jenis", jenis).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {

                    }
                });
            }
        });
        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.smallcup:
                        ukuran = "Kecil";
                        break;
                    case R.id.mediumcup:
                        ukuran = "Medium";
                        break;
                    case R.id.bigcup:
                        ukuran = "Besar";
                        break;
                }
                firebaseFirestore.collection("Coffee").document(coffeeid).update("ukuran", ukuran).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {

                    }
                });
            }
        });

        Glide.with(view.getContext()).load(gambarkopi).into(gambar);
        nama.setText(namakopi);
        deskripsi.setText(deskripsikopi);


            firebaseFirestore.collection("Coffee").document(coffeeid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
                    CoffeeModel coffeeModel = value.toObject(CoffeeModel.class);
                    jumlah = coffeeModel.getJumlah();
                    quantity.setText(String.valueOf(jumlah));
                    jenis = coffeeModel.getJenis();
                    ukuran = coffeeModel.getUkuran();

                    if (jenis.equalsIgnoreCase("Panas")){
                        rhot.setChecked(true);
                    }else if (jenis.equalsIgnoreCase("Dingin")){
                        rcold.setChecked(true);
                    }
                    if (ukuran.equalsIgnoreCase("Kecil")) {
                        rsmall.setChecked(true);
                    } else if (ukuran.equalsIgnoreCase("Medium")) {
                        rmedium.setChecked(true);
                    }else if (ukuran.equalsIgnoreCase("Besar")){
                        rbig.setChecked(true);
                    }

                    orderInfo.setText("Rp " + hitung());
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

                    orderInfo.setText("Rp " + hitung());

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

                    orderInfo.setText("Rp " + totalHarga);

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
    private int hitung(){
        if (ukuran.equalsIgnoreCase("Kecil")){
            totalHarga = harga * jumlah;
        }else if (ukuran.equalsIgnoreCase("Medium")){
            totalHarga = (harga * jumlah) + 1500;
        }else if (ukuran.equalsIgnoreCase("Besar")){
            totalHarga = (harga * jumlah) + 2000;
        }
        return totalHarga;
    }

    private void AddToCart() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("nama", namakopi);
        hashMap.put("jumlah", jumlah);
        hashMap.put("totalHarga", hitung());
        hashMap.put("coffeeid", coffeeid);
        hashMap.put("gambar", gambarkopi);
        hashMap.put("jenis", jenis);
        hashMap.put("ukuran", ukuran);

        if (jumlah == 0) {
            Toast.makeText(getContext(), "Minimal Order: 1", Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), "Menu tidak ditambahkan ke cart", Toast.LENGTH_SHORT).show();
            firebaseFirestore.collection("Coffee").document(namakopi).update("jenis","Panas");
            firebaseFirestore.collection("Coffee").document(namakopi).update("ukuran","Kecil");
            firebaseFirestore.collection("Cart").document(namakopi).delete();

            CoffeeDetailFragmentDirections.ActionCoffeeDetailFragmentToCoffeeListFragment
                    action = CoffeeDetailFragmentDirections.actionCoffeeDetailFragmentToCoffeeListFragment();
            action.setJumlah(jumlah);
            navController.navigate(action);
        } else {
            firebaseFirestore.collection("Cart").document(namakopi).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Ditambahkan ke Cart", Toast.LENGTH_SHORT).show();

                        CoffeeDetailFragmentDirections.ActionCoffeeDetailFragmentToCoffeeListFragment
                                action = CoffeeDetailFragmentDirections.actionCoffeeDetailFragmentToCoffeeListFragment();
                        action.setJumlah(jumlah);
                        navController.navigate(action);
                    }
                }
            });
        }

    }


}