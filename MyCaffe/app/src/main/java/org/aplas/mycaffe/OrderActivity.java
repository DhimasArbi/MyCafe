package org.aplas.mycaffe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class OrderActivity extends AppCompatActivity {

    private EditText edtName;
    private CheckBox krim, cokelat;
    private TextView priceTextView, quantityTextView;
    private int quantity = 0, harga = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        String title = getIntent().getStringExtra("NAMA_MENU");
        if(title=="Esspresso"){
            harga=42000;
        }else if(title=="Cappucino"){
            harga = 42000;
        }else if(title=="Americano"){
            harga = 32000;
        }else if(title=="Frapucino"){
            harga = 44000;
        }else{
            harga = 42000;
        }
    }

    public void increment(View view){
        if(quantity==10){
            Toast.makeText(this,"Pesanan maximal 10",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity+1 ;
        display(quantity);
    }
    public void decrement(View view){
        if (quantity==1){
            Toast.makeText(this,"pesanan minimal 1",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity -1;
        display(quantity);
    }

    public void Submitorder(View view) {
        EditText nameEditText=(EditText)findViewById(R.id.edt_name);
        String name=nameEditText.getText().toString();
        Log.v("MainActivity","Nama:"+name);

        CheckBox whippedcreamChekBox= (CheckBox) findViewById(R.id.WhippedCream_checkbox);
        boolean haswhippedcream=whippedcreamChekBox.isChecked();
        Log.v("MainActivity","has whippedcream:"+haswhippedcream);

        CheckBox chocolateChekBox= (CheckBox) findViewById(R.id.Chocolate_checkbox);
        boolean haschocolate=chocolateChekBox.isChecked();
        Log.v("MainActivity","has whippedcream:"+haschocolate);

        int price = calculateprice(haswhippedcream,haschocolate);
        String pricemessage = createOrderSummary(price,name,haswhippedcream,haschocolate);

        displayMessage(pricemessage);

    }
    private int calculateprice(boolean addwhipedcream, boolean addchocolate){
        if(addwhipedcream){
            harga=harga+1000;
        }
        if (addchocolate){
            harga=harga+2000;
        }
        return quantity * harga;
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
        TextView priceTextView = (TextView) findViewById(R.id.price_textview);
        priceTextView.setText(message);
    }
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_textview);
        quantityTextView.setText("" + number);
    }
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_textview);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
}