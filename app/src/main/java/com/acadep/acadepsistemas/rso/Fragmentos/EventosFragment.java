package com.acadep.acadepsistemas.rso.Fragmentos;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.acadep.acadepsistemas.rso.Adapter.EventoAdapter;
import com.acadep.acadepsistemas.rso.Clases.SupervisionActivity;
import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Evento;
import com.acadep.acadepsistemas.rso.model.Recursos;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventosFragment extends Fragment {

    static int contEventos=0;


    public String uidUserGlobal;
    public String user_id;
    static boolean activeStatus;
    static String nameEvent;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    com.acadep.acadepsistemas.rso.Adapter.EventoAdapter EventAdapter;

    FirebaseFirestore BDFireStore= FirebaseFirestore.getInstance();
    CollectionReference eventsReference;
    //CollectionReference eventsReference = BDFireStore.collection("events");

    TextView EventosPendientes;
    RecyclerView rv;


    ArrayList<Evento> list;
    com.acadep.acadepsistemas.rso.Adapter.Adapter adapter;
    public EventosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_eventos, container, false);
        rv = (RecyclerView) view.findViewById(R.id.recycler);
        EventosPendientes = (TextView) view.findViewById(R.id.txtEventosPendientes);

        FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
        //fragmentTransaction1.add(R.id.fragment_container, new SupervisionFragment());

        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<Evento>();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        uidUserGlobal = user.getUid();
        contEventos=0;
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

        //contEventos++;


        EventAdapter.setOnItemClickListener(new EventoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Evento evento = documentSnapshot.toObject(Evento.class);

                String actividad = evento.getType_activity();
                String user_id = evento.getUser_id();

                String start = evento.getStart();
                String end = evento.getEnd();
                String description = evento.getDescription();
                String idEvento = evento.getId();
                String nameEvent = evento.getTitle();

                int percentage = evento.getPercentage();

                List<Recursos> tools = evento.getTools();
                boolean deleted = evento.isDeleted();
                activeStatus = evento.isActive();


                Intent intent = new Intent(getActivity(), SupervisionActivity.class);
                intent.putExtra("idEvento", idEvento);
                intent.putExtra("actividad", actividad);
                intent.putExtra("nameEvent", nameEvent);
                intent.putExtra("user_id", user_id);
                intent.putExtra("start", start);
                intent.putExtra("end", end);
                intent.putExtra("description", description);
                intent.putExtra("deleted", deleted);
                intent.putExtra("percentage", percentage);

                if (evento.getType_activity().equals("supervision")) {
                    //mifragment = new SupervisionFragment();

                    startActivity(intent);
                    //Toast.makeText(getContext(), "Supervision", Toast.LENGTH_SHORT).show();
                    StyleableToast.makeText(getContext(), "Supervision", Toast.LENGTH_SHORT, R.style.sucessToast).show();

                }

                if(evento.getType_activity().equals("servicio")){
                    //mifragment = new SupervisionFragment();

                    startActivity(intent);
                    //Toast.makeText(getContext(), "Servicio", Toast.LENGTH_SHORT).show();
                    StyleableToast.makeText(getContext(), "Servicio", Toast.LENGTH_SHORT, R.style.sucessToast).show();

                }

                if(evento.getType_activity().equals("revision")){
                    // mifragment = new RevisionFragment();

                    startActivity(intent);
                    //Toast.makeText(getContext(), "Revision", Toast.LENGTH_SHORT).show();
                    StyleableToast.makeText(getContext(), "Revision", Toast.LENGTH_SHORT, R.style.sucessToast).show();
                }

                if(evento.getType_activity().equals("auditoria")){
                    // mifragment = new AuditoriaFragment();


                    startActivity(intent);
                    //Toast.makeText(getContext(), "Auditoria", Toast.LENGTH_SHORT).show();
                    StyleableToast.makeText(getContext(), "Auditoria", Toast.LENGTH_SHORT, R.style.sucessToast).show();


                }


            }
        });



//                contEventos++;
//                if(contEventos !=0 ){
//                    EventosPendientes.setText("#" + " Eventos pendientes");
//                }else{
//                    EventosPendientes.setText("#" + " Eventos pendientes");
//                }




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
