package org.aplas.sanasinicoffee;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        public void ButtonOnClick(View V){
            textview.setText("Button telah di Klik");
            textview.setVisibility(View.VISIBLE);
        }
    }
}