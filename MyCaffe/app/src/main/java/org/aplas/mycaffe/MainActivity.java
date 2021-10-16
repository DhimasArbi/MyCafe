package org.aplas.mycaffe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcView;
    public DataAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcView = (RecyclerView) findViewById(R.id.dataView);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        loadCaffeData();
        mAdapter.setOnItemClickListener(new DataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView itemTitle = (TextView) view.findViewById(R.id.nmMenu);
                TextView itemInfo = (TextView) view.findViewById(R.id.menuInfo);
                ImageView itemPicture = (ImageView) view.findViewById(R.id.gbMenu);

                openOrderActivity(itemTitle.getText().toString());
            }
        });
    }

    public static int getId(String resourceName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            return -1;
        }
    }

    private ArrayList<DataItem> getCaffeData() {
        //Get the resources from the XML file
        String[] listTitles = getResources().getStringArray(R.array.caffe_title);
        int[] listColor = getResources().getIntArray(R.array.menu_color);
        String[] listInfo = getResources().getStringArray(R.array.caffe_info);
        String[] listIcon = getResources().getStringArray(R.array.caffe_icon);
        ArrayList<DataItem> data = new ArrayList<>();
        //Create the ArrayList with the titles and information
        for (int i=0; i<listTitles.length; i++) {
            data.add(new DataItem(listTitles[i],listInfo[i],listColor[i], getId(listIcon[i],R.drawable.class)));
        }
        return data;
    }
    private void loadCaffeData() {
        //Initialize the adapter and set it ot the RecyclerView
        mAdapter = new DataAdapter(this, getCaffeData());
        ItemTouchHelper.Callback callback = new ItemMoveCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rcView);
        rcView.setAdapter(mAdapter);
        //Notify the adapter of the change
        mAdapter.notifyDataSetChanged();
    }

    private void openOrderActivity(String title) {
        Intent media = new Intent(getApplicationContext(), OrderActivity.class);
        media.putExtra("NAMA_MENU", title);
        startActivity(media);
    }
}