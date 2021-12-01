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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class checkout_styles extends Fragment {

    NavController navController;
    Button home;
    FirebaseFirestore firestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.checkout_styles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        home = view.findViewById(R.id.buttonhome);
        firestore = FirebaseFirestore.getInstance();


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("Coffee").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot tasks = task.getResult();
                            for (DocumentSnapshot ds : tasks.getDocuments()) {
                                ds.getReference()
                                        .update("jumlah", 0);
                            }
                        }


                    }
                });

                // clearing the cart

                firestore.collection("Cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot tasks = task.getResult();
                            for (DocumentSnapshot ds : tasks.getDocuments()) {
                                ds.getReference()
                                        .delete();
                            }
                        }
                    }
                });
                navController.navigate(R.id.action_checkout_styles_to_coffeeListFragment);
            }
        });
    }
}