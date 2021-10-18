package org.aplas.mycaffe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    Button homeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        TextView coffeenamedetail = (TextView) findViewById(R.id.coffeenamedetail);
        TextView coffeetotalitem = (TextView) findViewById(R.id.coffeetotalitem);
        TextView coffeetotal = (TextView) findViewById(R.id.coffeetotal);
        TextView hargaItem = (TextView) findViewById(R.id.hargaItem);
        homeBtn = (Button) findViewById(R.id.homeBtn);

        String title = getIntent().getStringExtra("NAMA_MENU");
        int q = getIntent().getIntExtra("QUANTITY",0);
        int h = getIntent().getIntExtra("HARGATOTAL",0);
        int hi = getIntent().getIntExtra("HARGAITEM",0);

        coffeenamedetail.setText("Pesanan\t\t\t\t"+title);
        coffeetotalitem.setText("Total item\t\t\t"+q);
        hargaItem.setText("Harga\t\t\t\t\t\t\tRp "+hi);
        coffeetotal.setText("Total\t\t\t\t\t\t\t\tRp "+h);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startMain);
            }
        });
    }
}