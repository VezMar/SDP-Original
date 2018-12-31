package com.example.acadepsistemas.seguimiento.Fragmentos;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.acadepsistemas.seguimiento.Adapter.Adapter;
import com.example.acadepsistemas.seguimiento.Clases.SupervisionActivity;
import com.example.acadepsistemas.seguimiento.R;
import com.example.acadepsistemas.seguimiento.model.Evento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventosFragment extends Fragment {

    public String uidUserGlobal;
    public String uid;
    static boolean activeStatus;
    static String nameEvent;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    static int c=0;
    RecyclerView rv;

    ArrayList<Evento> list;
    com.example.acadepsistemas.seguimiento.Adapter.Adapter adapter;
    public EventosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        c=0;
        final View view = inflater.inflate(R.layout.fragment_eventos, container, false);
        rv = (RecyclerView) view.findViewById(R.id.recycler);


        FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
        //fragmentTransaction1.add(R.id.fragment_container, new SupervisionFragment());

        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<Evento>();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        uidUserGlobal = user.getUid();

        chequeoDevariables();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Eventos");


        // mDatabase = FirebaseDatabase.getInstance().getReference().child("Profiles");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Evento>();


                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {


                    Evento p = dataSnapshot1.getValue(Evento.class);
                    uid = p.getUid();
                    activeStatus = p.getActive();


                    if((uidUserGlobal).equals(uid) && activeStatus==true) {
                   // if((uidUserGlobal).equals(uid)) {

                        list.add(p);
                   }
                    c++;

                }
                adapter = new Adapter(EventosFragment.this,list);

                adapter.setOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Fragment mifragment = null;
                        Boolean FragmentoSeleccionado=false;


                        /*Intent intent= new Intent (EventosFragment.this, SupervisionFragment);
                        startActivity(intent);*/
                        /**/
                        String actividad= list.get(rv.getChildAdapterPosition(v)).getActividad();

                        if((list.get(rv.getChildAdapterPosition(v)).getActividad()).equals("Supervision")){
                            //mifragment = new SupervisionFragment();
                            String uid = list.get(rv.getChildAdapterPosition(v)).getUid();
                            String trabajador = list.get(rv.getChildAdapterPosition(v)).getTrabajador();
                            String start= list.get(rv.getChildAdapterPosition(v)).getStart();
                            String end= list.get(rv.getChildAdapterPosition(v)).getEnd();
                            String idactivity= list.get(rv.getChildAdapterPosition(v)).getIdactivity();
                            String description= list.get(rv.getChildAdapterPosition(v)).getDescription();
                            String idEvento = list.get(rv.getChildAdapterPosition(v)).getIdevent();
                            String nameEvent = list.get(rv.getChildAdapterPosition(v)).getName();


                            Intent intent= new Intent (getActivity(), SupervisionActivity.class);
                            intent.putExtra("idEvento",idEvento);
                            intent.putExtra("actividad",actividad);
                            intent.putExtra("nameEvent",nameEvent);
                            intent.putExtra("uid",uid);
                            intent.putExtra("trabajador",trabajador);
                            intent.putExtra("start",start);
                            intent.putExtra("end",end);
                            intent.putExtra("idactivity",idactivity);
                            intent.putExtra("description",description);


                            startActivity(intent);
                           Toast.makeText(getContext(), "Supervision", Toast.LENGTH_SHORT).show();

                            /*getFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.Contenedor, mifragment)
                                    .commit();*/
                        }

                        if((list.get(rv.getChildAdapterPosition(v)).getActividad()).equals("Revision")){
                           // mifragment = new RevisionFragment();
                            String idEvento = list.get(rv.getChildAdapterPosition(v)).getIdevent();
                            Intent intent= new Intent (getActivity(), SupervisionActivity.class);
                            intent.putExtra("idEvento",idEvento);
                            intent.putExtra("actividad",actividad);

                            startActivity(intent);
                            Toast.makeText(getContext(), "Revision", Toast.LENGTH_SHORT).show();
                            /*getFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.Content_Eventos, mifragment)
                                    .commit();*/
                        }

                        if((list.get(rv.getChildAdapterPosition(v)).getActividad()).equals("Auditoria")){
                           // mifragment = new AuditoriaFragment();
                            String idEvento = list.get(rv.getChildAdapterPosition(v)).getIdevent();
                            Intent intent= new Intent (getActivity(), SupervisionActivity.class);
                            intent.putExtra("idEvento",idEvento);
                            intent.putExtra("actividad",actividad);

                            startActivity(intent);
                            Toast.makeText(getContext(), "Auditoria", Toast.LENGTH_SHORT).show();
                            /*getFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.Content_Eventos, mifragment)
                                    .commit();*/
                        }


                    }

                });

                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });



        return view;






    }

    private void chequeoDevariables() {
    }

}
