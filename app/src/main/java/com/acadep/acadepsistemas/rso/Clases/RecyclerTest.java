package com.acadep.acadepsistemas.rso.Clases;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.acadep.acadepsistemas.rso.Adapter.RecyclerViewAdapter;
import com.acadep.acadepsistemas.rso.R;

import java.util.ArrayList;


public class RecyclerTest  extends AppCompatActivity {

    private ArrayList<String> mImageBitmap = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_test);

        initImagesBitmap();

    }

    private void initImagesBitmap(){
        mImageBitmap.add("https://definicion.mx/wp-content/uploads/gral/Planicie.jpg");
        mImageBitmap.add("https://s1.significados.com/foto/planicie_bg.jpg");
        mImageBitmap.add("http://spmedia.s3.amazonaws.com/wp-content/uploads/2013/02/CCLAPLANICIE.jpg");
        mImageBitmap.add("https://photo980x880.mnstatic.com/ebbe869f8b2d0336ec387685f1da6370/planicie-de-nardab_84091.jpg");

        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.test_recycler);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mImageBitmap);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }
}
