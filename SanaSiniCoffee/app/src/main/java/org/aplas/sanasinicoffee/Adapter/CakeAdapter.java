package org.aplas.sanasinicoffee.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.aplas.sanasinicoffee.Model.CakeModel;
import org.aplas.sanasinicoffee.R;

import java.util.List;

public class CakeAdapter extends RecyclerView.Adapter<CakeAdapter.CakeListHolder> {

    List<CakeModel> cakeModelList;
    GetOneCake interfaceGetCake;

    public CakeAdapter(GetOneCake interfaceGetCake) {
        this.interfaceGetCake = interfaceGetCake;
    }

    @Override
    public CakeListHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cake_list_style, parent,false);
        return new CakeListHolder(view);
    }

    @Override
    public void onBindViewHolder( CakeListHolder holder, int position) {
        holder.nama.setText(cakeModelList.get(position).getNama());
        Glide.with(holder.itemView.getContext()).load(cakeModelList.get(position).getGambar()).into(holder.gambar);
    }

    @Override
    public int getItemCount() {
        return cakeModelList.size();
    }

    public void setCakeModelList(List<CakeModel> cakeModelList) {
        this.cakeModelList = cakeModelList;
    }

    class CakeListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nama;
        ImageView gambar;
        CardView cardView;

        public CakeListHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.cakeName);
            gambar = itemView.findViewById(R.id.cakeImage);
            cardView = itemView.findViewById(R.id.cardcakelist);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            interfaceGetCake.clickedCake(getAdapterPosition(), cakeModelList);
        }
    }
    public interface GetOneCake{
        void clickedCake(int position, List<CakeModel> cakeModels);
    }
}
