package org.aplas.mycaffe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        TextView coffeenamedetail = (TextView) findViewById(R.id.coffeenamedetail);
        TextView coffeetotalitem = (TextView) findViewById(R.id.coffeetotalitem);
        TextView coffeetotal = (TextView) findViewById(R.id.coffeetotal);
        TextView hargaItem = (TextView) findViewById(R.id.hargaItem);

        String title = getIntent().getStringExtra("NAMA_MENU");
        int q = getIntent().getIntExtra("QUANTITY",0);
        int h = getIntent().getIntExtra("HARGATOTAL",0);
        int hi = getIntent().getIntExtra("HARGAITEM",0);

        coffeenamedetail.setText("Pesanan\t\t\t\t"+title);
        coffeetotalitem.setText("Total item\t\t"+q);
        hargaItem.setText("Harga\t\t\t\t\t\t\tRp "+hi);
        coffeetotal.setText("Total\t\t\t\t\t\t\t\tRp "+h);
    }
}