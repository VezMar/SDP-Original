package com.acadep.acadepsistemas.rso.Clases;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.acadep.acadepsistemas.rso.Adapter.MaterialsAdapter;
import com.acadep.acadepsistemas.rso.Adapter.RecyclerViewAdapter;
import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Configuration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MaterialsCheckList extends AppCompatActivity {


    //Firebase FireStore
    FirebaseFirestore BDFireStore = FirebaseFirestore.getInstance();
    //Firebase FireStore

    //RecyclerView
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayaoutManager;
    //RecyclerView

    //Materiales
    private List<String> Materials;
    private List<Integer> CantidadMaterials;
    //Materiales

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materials_check_list);

        ChequeoConfiguration();

    }

    private void initRecyclerView() {
//        int mNoOfColumns = SupervisionActivity.Utility.calculateNoOfColumns(getApplicationContext(), 150);
        mRecyclerView =  findViewById(R.id.materials_recycler);
//        mLayaoutManager= new GridLayoutManager(this,mNoOfColumns);
//        mLayaoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
        mLayaoutManager = new LinearLayoutManager(getApplicationContext());

        mAdapter = new MaterialsAdapter(this, Materials, CantidadMaterials);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(mLayaoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void ChequeoConfiguration() {

        BDFireStore
                .collection("configuration")
                .document("global")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Configuration configuration = documentSnapshot.toObject(Configuration.class);

                Materials = configuration.getMaterials();
            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                initRecyclerView();
            }
        });

    }
}
