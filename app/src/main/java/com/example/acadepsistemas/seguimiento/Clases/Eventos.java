package com.example.acadepsistemas.seguimiento.Clases;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;
import android.widget.LinearLayout;

import com.example.acadepsistemas.seguimiento.R;
import com.example.acadepsistemas.seguimiento.model.Evento;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.xml.sax.DTDHandler;

import java.util.ArrayList;
import java.util.List;

public class Eventos extends AppCompatActivity {

    RecyclerView rv;

    List<Evento> eventos;

    com.example.acadepsistemas.seguimiento.Adapter.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);


        setContentView(R.layout.activity_eventos);

        rv = (RecyclerView) findViewById(R.id.recycler);

        rv.setLayoutManager(new LinearLayoutManager(this));

        eventos = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

       // adapter = new com.example.acadepsistemas.seguimiento.Adapter.Adapter(eventos);

        rv.setAdapter(adapter);
        database.getReference().getRoot().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventos.removeAll(eventos);
                for(DataSnapshot snapshot :
                        dataSnapshot.getChildren()){
                    Evento evento = snapshot.getValue(Evento.class);
                    eventos.add(evento);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
