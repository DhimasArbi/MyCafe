package org.aplas.mycaffe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class OrderActivity extends AppCompatActivity {

    private EditText edtName;
    private CheckBox krim, cokelat;
    private TextView priceTextView, quantityTextView;
    private Button inc,dec, pesan;
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
        edtName = (EditText)findViewById(R.id.edt_name);
        krim = (CheckBox) findViewById(R.id.tbhkrim);
        cokelat = (CheckBox) findViewById(R.id.tbhCoklat);
        priceTextView = (TextView) findViewById(R.id.price_textview);

        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity==1){
                    Toast.makeText(OrderActivity.this,"pesanan minimal 1", Toast.LENGTH_SHORT).show();
                    return;
                }
                quantity = quantity -1;
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
                quantity = quantity+1 ;
                display(quantity);
            }
        });

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = getIntent().getStringExtra("NAMA_MENU");
                String name = edtName.getText().toString();
                boolean haswhippedcream = krim.isChecked();
                boolean haschocolate = cokelat.isChecked();

                setHarga(title,haswhippedcream, haschocolate);
                int price = harga * quantity;
                String pricemessage = createOrderSummary(price,name,haswhippedcream,haschocolate);

                displayMessage(pricemessage);
            }
        });
    }

    private  int setHarga(String title, Boolean tbhKrim, Boolean tbhCoklat){
        if(tbhKrim){
            if(title=="Esspresso"){
                harga=hrgEsspresso+1000;
            }else if(title=="Cappucino"){
                harga = hrgCappucino+1000;
            }else if(title=="Americano"){
                harga = hrgAmericano+1000;
            }else if(title=="Frapucino"){
                harga = hrgFrapucino+1000;
            }else{
                harga = hrgLatte+1000;
            }
        }
        if (tbhCoklat){
            if(title=="Esspresso"){
                harga=hrgEsspresso+2000;
            }else if(title=="Cappucino"){
                harga = hrgCappucino+2000;
            }else if(title=="Americano"){
                harga = hrgAmericano+2000;
            }else if(title=="Frapucino"){
                harga = hrgFrapucino+2000;
            }else{
                harga = hrgLatte+2000;
            }
        }
        if (!tbhCoklat && !tbhKrim){
            if(title=="Esspresso"){
                harga=hrgEsspresso;
            }else if(title=="Cappucino"){
                harga = hrgCappucino;
            }else if(title=="Americano"){
                harga = hrgAmericano;
            }else if(title=="Frapucino"){
                harga = hrgFrapucino;
            }else{
                harga = hrgLatte;
            }
        }
        return harga;
    }
    private String createOrderSummary(int price, String name, boolean addChocolate, boolean addWhippedCream) {
        String pricemessage=" Nama = "+name;
        pricemessage+="\n Tambahkan Coklat = " +addWhippedCream;
        pricemessage+="\n Tambahkan Krim = " +addChocolate;
        pricemessage+="\n Jumlah Pemesanan = " +quantity;
        pricemessage+="\n Total = Rp " +price;
        return  pricemessage;
    }

    private void displayMessage(String message) {
        priceTextView = (TextView) findViewById(R.id.price_textview);
        priceTextView.setText(message);
    }
    private void display(int number) {
        quantityTextView = (TextView) findViewById(R.id.quantity_textview);
        quantityTextView.setText("" + number);
    }
    private void displayPrice(int number) {
        priceTextView = (TextView) findViewById(R.id.price_textview);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
}