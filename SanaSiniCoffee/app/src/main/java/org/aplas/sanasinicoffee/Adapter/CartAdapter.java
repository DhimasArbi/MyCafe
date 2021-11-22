package org.aplas.sanasinicoffee.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.aplas.sanasinicoffee.Model.CartModel;
import org.aplas.sanasinicoffee.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {


    List<CartModel> cartModelList;
    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_style, parent, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(CartHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(cartModelList.get(position).getGambar()).into(holder.gambar);
        holder.jumlahitem.setText("x" + cartModelList.get(position).getJumlah());
        holder.harga.setText("Rp "+cartModelList.get(position).getTotalHarga());
        holder.nama.setText(cartModelList.get(position).getNama());
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }
    public void setCartModelList(List<CartModel> cartModelList){
        this.cartModelList = cartModelList;
    }

    class CartHolder extends ViewHolder{

        TextView nama, harga, jumlahitem;
        ImageView gambar;

        public CartHolder(View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.cartcoffeename);
            harga = itemView.findViewById(R.id.orderdetailprice);
            gambar = itemView.findViewById(R.id.cartImage);
            jumlahitem = itemView.findViewById(R.id.orderdetailquantity);
        }
    }
}
