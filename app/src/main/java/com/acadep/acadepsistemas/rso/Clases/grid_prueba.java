package com.acadep.acadepsistemas.rso.Clases;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.acadep.acadepsistemas.rso.Adapter.ImageAdapter;
import com.acadep.acadepsistemas.rso.R;

public class grid_prueba extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_prueba);

        GridView gridView = (GridView) findViewById(R.id.gridPrueba);

        gridView.setAdapter(new ImageAdapter(this));
    }
}
