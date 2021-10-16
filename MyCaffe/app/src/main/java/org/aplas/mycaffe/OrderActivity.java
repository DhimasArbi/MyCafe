package org.aplas.mycaffe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class OrderActivity extends AppCompatActivity {

    private EditText edtName;
    private TextView priceTextView, quantityTextView;
    private Button inc,dec, pesan;
    private ImageView gbOrder;
    private int quantity = 0, harga = 0;
    private static int hrgAmericano = 32000, hrgCappucino = 42000,
            hrgEsspresso= 42000, hrgFrapucino = 44000, hrgLatte = 42000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        inc = (Button) findViewById(R.id.buttonTambah);
        dec = (Button) findViewById(R.id.buttonKurang);
        pesan = (Button) findViewById(R.id.buttonPesan);
//        edtName = (EditText)findViewById(R.id.edt_name);
        priceTextView = (TextView) findViewById(R.id.orderINFO);
        TextView nmMenu = (TextView) findViewById(R.id.coffeenamedetail);
//        TextView descMenu = (TextView) findViewById(R.id.coffeedetaildetail);
        gbOrder = (ImageView) findViewById(R.id.CoffeeDetailImage);

        String nmCoffe = getIntent().getStringExtra("NAMA_MENU");
        nmMenu.setText(nmCoffe);
        if(nmCoffe.equalsIgnoreCase("Esspresso")){
            gbOrder.setImageResource(R.drawable.espresso);
        }else if(nmCoffe.equalsIgnoreCase("Cappucino")){
            gbOrder.setImageResource(R.drawable.capucino);
        }else if(nmCoffe.equalsIgnoreCase("Americano")){
            gbOrder.setImageResource(R.drawable.americano);
        }else if(nmCoffe.equalsIgnoreCase("Frapucino")){
            gbOrder.setImageResource(R.drawable.frapuccino);
        }else if (nmCoffe.equalsIgnoreCase("Latte")){
            gbOrder.setImageResource(R.drawable.latte);
        }
//        descMenu.setText(getIntent().getStringExtra("MENU_INFO"));

        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity==1){
                    Toast.makeText(OrderActivity.this,"pesanan minimal 1", Toast.LENGTH_SHORT).show();
                    return;
                }
                quantity = quantity - 1;
                display(quantity);
            }
        });
        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity==10){
                    Toast.makeText(OrderActivity.this,"Pesanan maximal 10",Toast.LENGTH_SHORT).show();
                    return;
                }
                quantity = quantity + 1 ;
                display(quantity);
            }
        });

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = getIntent().getStringExtra("NAMA_MENU");
                openDisplayActivity(title, quantity, hitungHarga(title),hitungHarga(title)/quantity);
            }
        });
    }

    
    private int hitungHarga(String title){
        if(title.equalsIgnoreCase("Esspresso")){
            harga=hrgEsspresso;
        }else if(title.equalsIgnoreCase("Cappucino")){
            harga = hrgCappucino;
        }else if(title.equalsIgnoreCase("Americano")){
            harga = hrgAmericano;
        }else if(title.equalsIgnoreCase("Frapucino")){
            harga = hrgFrapucino;
        }else if (title.equalsIgnoreCase("Latte")){
            harga = hrgLatte;
        }
        return harga * quantity;
    }

    private void display(int number) {
        quantityTextView = (TextView) findViewById(R.id.quantity);
        String title = getIntent().getStringExtra("NAMA_MENU");
        String pricemessage = "Rp " +hitungHarga(title);
        priceTextView.setText(pricemessage);
        quantityTextView.setText("" + number);
    }
    private void openDisplayActivity(String title, int quantity, int harga, int hrgItem) {
        Intent media = new Intent(getApplicationContext(), DisplayActivity.class);
        media.putExtra("NAMA_MENU", title);
        media.putExtra("QUANTITY", quantity);
        media.putExtra("HARGATOTAL", harga);
        media.putExtra("HARGAITEM", hrgItem);
        startActivity(media);
    }
}