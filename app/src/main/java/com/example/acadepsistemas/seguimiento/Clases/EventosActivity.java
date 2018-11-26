package com.example.acadepsistemas.seguimiento.Clases;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.widget.Toast;

import com.example.acadepsistemas.seguimiento.Adapter.Adapter;
import com.example.acadepsistemas.seguimiento.Fragmentos.EventosFragment;
import com.example.acadepsistemas.seguimiento.R;
import com.example.acadepsistemas.seguimiento.model.Evento;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventosActivity extends AppCompatActivity {

    RecyclerView rv;

    ArrayList<Evento> list;
    DatabaseReference mDatabase;
    com.example.acadepsistemas.seguimiento.Adapter.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos2);

        setContentView(R.layout.activity_eventos2);

        rv = (RecyclerView) findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<Evento>();


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Eventos");


       // mDatabase = FirebaseDatabase.getInstance().getReference().child("Profiles");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Evento>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Evento p = dataSnapshot1.getValue(Evento.class);
                    list.add(p);
                }
               // adapter = new Adapter(EventosActivity.this,list);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EventosActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });


        /*
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Evento e = dataSnapshot1.getValue(Evento.class);
                    list.add(e);
                }
                adapter = new Adapter(EventosActivity.this, list);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Ah ocurrido un error" , Toast.LENGTH_SHORT).show();
            }
        });*/















        /*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        adapter = new com.example.acadepsistemas.seguimiento.Adapter.Adapter(eventos);

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
        });*/
    }
}
