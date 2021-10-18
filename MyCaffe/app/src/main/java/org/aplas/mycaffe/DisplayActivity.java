package org.aplas.mycaffe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    private Button homeBtn;
    private TextView coffeenamedetail, coffeetotalitem, coffeetotal, hargaItem, nama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        coffeenamedetail = (TextView) findViewById(R.id.coffeenamedetail);
        coffeetotalitem = (TextView) findViewById(R.id.coffeetotalitem);
        coffeetotal = (TextView) findViewById(R.id.coffeetotal);
        hargaItem = (TextView) findViewById(R.id.hargaItem);
        nama = (TextView) findViewById(R.id.nama);
        homeBtn = (Button) findViewById(R.id.homeBtn);

        nama.setText("Nama\t\t\t\t\t\t\t\t"+getIntent().getStringExtra("NAMA"));
        coffeenamedetail.setText("Pesanan\t\t\t\t\t"+getIntent().getStringExtra("NAMA_MENU"));
        coffeetotalitem.setText("Total item\t\t\t"+getIntent().getIntExtra("QUANTITY",0));
        hargaItem.setText("Harga\t\t\t\t\t\t\t\tRp "+getIntent().getIntExtra("HARGAITEM",0));
        coffeetotal.setText("Total\t\t\t\t\t\t\t\t\tRp "+getIntent().getIntExtra("HARGATOTAL",0));

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startMain);
            }
        });
    }
}