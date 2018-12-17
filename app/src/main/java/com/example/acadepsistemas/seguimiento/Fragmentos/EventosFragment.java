package com.example.acadepsistemas.seguimiento.Fragmentos;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.example.acadepsistemas.seguimiento.Adapter.EventoAdapter;
import com.example.acadepsistemas.seguimiento.Clases.SupervisionActivity;
import com.example.acadepsistemas.seguimiento.R;
import com.example.acadepsistemas.seguimiento.model.Evento;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventosFragment extends Fragment {

    public String uidUserGlobal;
    public String user_id;
    static boolean activeStatus;
    static String nameEvent;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    com.example.acadepsistemas.seguimiento.Adapter.EventoAdapter EventAdapter;

    FirebaseFirestore BDFireStore= FirebaseFirestore.getInstance();
    CollectionReference eventsReference;
    //CollectionReference eventsReference = BDFireStore.collection("events");

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

        setUpRecyclerView();



        mDatabase = FirebaseDatabase.getInstance().getReference().child("Eventos");



        return view;






    }

    private void setUpRecyclerView() {
        //com.google.firebase.firestore.Query query = eventsReference.orderBy("end", com.google.firebase.firestore.Query.Direction.DESCENDING);

        com.google.firebase.firestore.Query query = BDFireStore.collection("events").whereEqualTo("user_id", mAuth.getUid()).whereEqualTo("active",true);
        //com.google.firebase.firestore.Query query = BDFireStore.collection("events").whereEqualTo("uid",  mAuth.getUid());


        FirestoreRecyclerOptions<Evento> options = new FirestoreRecyclerOptions.Builder<Evento>()
                .setQuery(query, Evento.class).build();

        EventAdapter = new EventoAdapter(options);


        EventAdapter.setOnItemClickListener(new EventoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Evento evento = documentSnapshot.toObject(Evento.class);

                String actividad = evento.getActividad();
                String user_id = evento.getUser_id();
                String trabajador = evento.getTrabajador();
                String start= evento.getStart();
                String end= evento.getEnd();
                String idactivity = evento.getIdactivity();
                String description = evento.getDescription();
                String idEvento = evento.getIdevent();
                String nameEvent = evento.getName();
                List<String> tools = evento.getTools();
                boolean deleted = evento.isDeleted();
                activeStatus = evento.isActive();



                Intent intent= new Intent (getActivity(), SupervisionActivity.class);
                intent.putExtra("idEvento",idEvento);
                intent.putExtra("actividad",actividad);
                intent.putExtra("nameEvent",nameEvent);
                intent.putExtra("user_id",user_id);
                intent.putExtra("trabajador",trabajador);
                intent.putExtra("start",start);
                intent.putExtra("end",end);
                intent.putExtra("idactivity",idactivity);
                intent.putExtra("description",description);
                intent.putStringArrayListExtra("tools", (ArrayList<String>) tools);
                intent.putExtra("deleted", deleted);

               if(evento.getActividad().equals("Supervision")){
                    //mifragment = new SupervisionFragment();

                    startActivity(intent);
                    Toast.makeText(getContext(), "Supervision", Toast.LENGTH_SHORT).show();

                }

                if(evento.getActividad().equals("Revision")){
                    // mifragment = new RevisionFragment();

                    startActivity(intent);
                    Toast.makeText(getContext(), "Revision", Toast.LENGTH_SHORT).show();

                }

                if(evento.getActividad().equals("Auditoria")){
                    // mifragment = new AuditoriaFragment();


                    startActivity(intent);
                    Toast.makeText(getContext(), "Auditoria", Toast.LENGTH_SHORT).show();


                }


            }
        });



                rv.setHasFixedSize(true);
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                rv.setAdapter(EventAdapter);



    }

    @Override
    public void onStart() {
        super.onStart();
        EventAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventAdapter.stopListening();
    }

    private void chequeoDevariables() {
    }

}
