package org.aplas.sanasinicoffee;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.aplas.sanasinicoffee.Model.CartModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {


    NavController navController;
    CardView card1, card2;
    FloatingActionButton fab;
    FirebaseFirestore firebaseFirestore;
    TextView quantity;
    List<Integer> savequantity = new ArrayList<>();
    int jumlahsum;
    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        navController = Navigation.findNavController(view);
        quantity = view.findViewById(R.id.quantityfabcategory);
        card1 = view.findViewById(R.id.cardC1);
        card2 = view.findViewById(R.id.cardC2);
        fab = view.findViewById(R.id.fabctg);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_categoryFragment_to_coffeeListFragment);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_categoryFragment_to_cakeListFragment);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_categoryFragment_to_cartFragment);
            }
        });

        firebaseFirestore.collection("Cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot ds : task.getResult().getDocuments()) {
                        CartModel cartModel = ds.toObject(CartModel.class);
                        int initialquantity = cartModel.getJumlah();
                        savequantity.add(initialquantity);
                    }
                    for (int i = 0; i < savequantity.size(); i++) {
                        jumlahsum += Integer.parseInt(String.valueOf(savequantity.get(i)));
                    }
                    quantity.setText(String.valueOf(jumlahsum));
                    jumlahsum = 0;
                    savequantity.clear();
                }
            }
        });

    }


}