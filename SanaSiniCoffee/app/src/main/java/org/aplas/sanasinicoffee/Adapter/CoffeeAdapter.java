package org.aplas.sanasinicoffee.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;

import org.aplas.sanasinicoffee.Model.CartModel;
import org.aplas.sanasinicoffee.Model.CoffeeModel;
import org.aplas.sanasinicoffee.R;

import java.util.List;

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.CoffeListHolder> {

    List<CoffeeModel> coffeeModelList;
    GetOneCoffee interfaceGetCoffee;

    public CoffeeAdapter(GetOneCoffee interfaceGetCoffee) {
        this.interfaceGetCoffee = interfaceGetCoffee;
    }

    @Override
    public CoffeListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coffee_list_style, parent, false);
        return new CoffeListHolder(view);
    }

    @Override
    public void onBindViewHolder(CoffeListHolder holder, int position) {
        holder.nama.setText(coffeeModelList.get(position).getNama());
//        holder.deskripsi.setText(coffeeModelList.get(position).getDeskripsi());

        Glide.with(holder.itemView.getContext()).load(coffeeModelList.get(position).getGambar()).into(holder.gambar);
    }

    @Override
    public int getItemCount() {
        return coffeeModelList.size();
    }

    public void setCoffeeModelList(List<CoffeeModel> coffeeModelList) {
        this.coffeeModelList = coffeeModelList;
    }

    class CoffeListHolder extends ViewHolder implements View.OnClickListener{

        TextView nama, deskripsi;
        ImageView gambar;
        CardView container;

        public CoffeListHolder(View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.coffeeName);
//            deskripsi = itemView.findViewById(R.id.coffeedetail);
            gambar = itemView.findViewById(R.id.coffeeImage);
            container = itemView.findViewById(R.id.cardcoffeelist);

            nama.setOnClickListener(this);
//            deskripsi.setOnClickListener(this);
            gambar.setOnClickListener(this);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            interfaceGetCoffee.clickedCoffee(getAdapterPosition(), coffeeModelList);
        }
    }

    public interface GetOneCoffee{
        void clickedCoffee(int position, List<CoffeeModel> coffeeModels);
    }
}
