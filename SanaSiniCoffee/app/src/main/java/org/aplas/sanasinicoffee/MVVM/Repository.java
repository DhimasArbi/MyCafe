package org.aplas.sanasinicoffee.MVVM;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.aplas.sanasinicoffee.Model.CoffeeModel;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    List<CoffeeModel> coffeeModelList = new ArrayList<>();


    coffeeList interfaceCoffeeList;

    public Repository(coffeeList interfaceCoffeeList) {
        this.interfaceCoffeeList = interfaceCoffeeList;
    }

    public void getCoffee(){
        firebaseFirestore.collection("Coffee").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    coffeeModelList.clear();
                    for (DocumentSnapshot ds: task.getResult().getDocuments()){
                        CoffeeModel coffeeModel = ds.toObject(CoffeeModel.class);
                        coffeeModelList.add(coffeeModel);
                        interfaceCoffeeList.coffeeList(coffeeModelList);
                    }
                }
            }
        });
    }

    public interface coffeeList{
        void coffeeList(List<CoffeeModel> coffeeModels);
    }
}
