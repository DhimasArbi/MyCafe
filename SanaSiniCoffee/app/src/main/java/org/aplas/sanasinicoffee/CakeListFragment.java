package org.aplas.sanasinicoffee;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.aplas.sanasinicoffee.Adapter.CakeAdapter;
import org.aplas.sanasinicoffee.MVVM.CakeViewModel;
import org.aplas.sanasinicoffee.Model.CakeModel;
import org.aplas.sanasinicoffee.Model.CartModel;

import java.util.ArrayList;
import java.util.List;

public class CakeListFragment extends Fragment implements CakeAdapter.GetOneCake {

    FirebaseFirestore firebaseFirestore;
    CakeAdapter mAdapter;
    RecyclerView recyclerView;
    CakeViewModel viewModel;
    NavController navController;
    FloatingActionButton fab, fabcoffe;
    BottomNavigationView bottomNavigationView;
    int jumlahsum;
    TextView jumlahCart;
    List<Integer> savequantity = new ArrayList<>();

    public CakeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cake_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recViewCake);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mAdapter = new CakeAdapter(this);
        navController = Navigation.findNavController(view);
        jumlahCart = view.findViewById(R.id.quantityOnfAB);
        fab = view.findViewById(R.id.fab);
        fabcoffe = view.findViewById(R.id.fabcoffe);
        bottomNavigationView =view.findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());
        viewModel = new ViewModelProvider(getActivity()).get(CakeViewModel.class);
        viewModel.getCakeList().observe(getViewLifecycleOwner(), new Observer<List<CakeModel>>() {
            @Override
            public void onChanged(List<CakeModel> cakeModels) {
                mAdapter.setCakeModelList(cakeModels);
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
            public void onClick(View v) {
                navController.navigate(R.id.action_cakeListFragment_to_cartFragment);
            }
        });
        fabcoffe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_cakeListFragment_to_coffeeListFragment);
            }
        });
    }

    @Override
    public void clickedCake(int position, List<CakeModel> cakeModels) {
        String cakeid = cakeModels.get(position).getCakeid();
        String deskripsi = cakeModels.get(position).getDeskripsi();
        String nama = cakeModels.get(position).getNama();
        int harga = cakeModels.get(position).getHarga();
        String gambar = cakeModels.get(position).getGambar();
        CakeListFragmentDirections.ActionCakeListFragmentToCakeDetailFragment
                action = CakeListFragmentDirections.actionCakeListFragmentToCakeDetailFragment();
        action.setNama(nama);
        action.setDeskripsi(deskripsi);
        action.setGambar(gambar);
        action.setHarga(harga);
        action.setId(cakeid);
        navController.navigate(action);
    }


}