package org.aplas.sanasinicoffee;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
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

import org.aplas.sanasinicoffee.Adapter.CoffeeAdapter;
import org.aplas.sanasinicoffee.MVVM.CoffeeViewModel;
import org.aplas.sanasinicoffee.Model.CartModel;
import org.aplas.sanasinicoffee.Model.CoffeeModel;

import java.util.ArrayList;
import java.util.List;

public class CoffeeListFragment extends Fragment implements CoffeeAdapter.GetOneCoffee {

    FirebaseFirestore firebaseFirestore;
    CoffeeAdapter mAdapter;
    RecyclerView recyclerView;
    CoffeeViewModel viewModel;
    NavController navController;
    FloatingActionButton fab, fabcake;
    int jumlah, jumlahsum;
    TextView jumlahCart;
    List<Integer> savequantity = new ArrayList<>();

    public CoffeeListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coffee_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recViewAll);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mAdapter = new CoffeeAdapter(this);
        navController = Navigation.findNavController(view);
        jumlahCart = view.findViewById(R.id.quantityOnfAB);
        fab = view.findViewById(R.id.fab);
        fabcake = view.findViewById(R.id.fabcake);
        viewModel = new ViewModelProvider(getActivity()).get(CoffeeViewModel.class);
        viewModel.getCofeeList().observe(getViewLifecycleOwner(), new Observer<List<CoffeeModel>>() {
            @Override
            public void onChanged(List<CoffeeModel> coffeeModels) {
                mAdapter.setCoffeeModelList(coffeeModels);
                recyclerView.setAdapter(mAdapter);
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
                    jumlahCart.setText(String.valueOf(jumlahsum));
                    jumlahsum = 0;
                    savequantity.clear();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_coffeeListFragment_to_cartFragment);
            }
        });
        fabcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_coffeeListFragment_to_cakeListFragment);
            }
        });

    }


    @Override
    public void clickedCoffee(int position, List<CoffeeModel> coffeeModels) {
        String coffeid = coffeeModels.get(position).getCoffeid();
        String deskripsi = coffeeModels.get(position).getDeskripsi();
        String nama = coffeeModels.get(position).getNama();
        int harga = coffeeModels.get(position).getHarga();
        String gambar = coffeeModels.get(position).getGambar();
        CoffeeListFragmentDirections.ActionCoffeeListFragmentToCoffeeDetailFragment
                action = CoffeeListFragmentDirections.actionCoffeeListFragmentToCoffeeDetailFragment();
        action.setNama(nama);
        action.setDeskripsi(deskripsi);
        action.setGambar(gambar);
        action.setHarga(harga);
        action.setId(coffeid);
        navController.navigate(action); //
    }
}