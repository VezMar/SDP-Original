package com.acadep.acadepsistemas.rso.Fragmentos;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.acadep.acadepsistemas.rso.Adapter.EventoAdapter;
import com.acadep.acadepsistemas.rso.Clases.SupervisionActivity;
import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Evento;
import com.acadep.acadepsistemas.rso.model.Recursos;
import com.acadep.acadepsistemas.rso.model.Total;
import com.acadep.acadepsistemas.rso.model.Usuario;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventosFragment extends Fragment  {

    static int contEventos=0;


    public String uidUserGlobal;
    public String user_id;
    static boolean activeStatus;
    static String nameEvent;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference mDatabase;

    com.acadep.acadepsistemas.rso.Adapter.EventoAdapter EventAdapter;

    FirebaseFirestore BDFireStore= FirebaseFirestore.getInstance();
    CollectionReference eventsReference;
    //CollectionReference eventsReference = BDFireStore.collection("events");

    TextView EventosPendientes;
    RecyclerView rv;


    TextView txtCorreo;


    static String Correo;

    static ArrayList<String> arrayString = new ArrayList<String>();

    Query mReference;


    ArrayList<Evento> list;
    com.acadep.acadepsistemas.rso.Adapter.Adapter adapter;
    public EventosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        arrayString.add("1");
//        arrayString.add("2");
//        arrayString.add("3");
//        arrayString.add("4");
//        arrayString.add("5");



        final View view = inflater.inflate(R.layout.fragment_eventos, container, false);
//        Spinner spnProyectos = view.findViewById(R.id.SpnProyectos);
//
//        ArrayAdapter adapter = new ArrayAdapter(getContext(),  android.R.layout.simple_spinner_item, arrayString);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spnProyectos.setAdapter(adapter);
//        spnProyectos.setOnItemSelectedListener(this);


//        BDFireStore.collection("users").document(mAuth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Usuario usuario = documentSnapshot.toObject(Usuario.class);
//                Correo = usuario.getEmail();
//            }
//        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                txtCorreo.setText(""+Correo);
//
//            }
//        });


        rv = (RecyclerView) view.findViewById(R.id.recycler);
        EventosPendientes = (TextView) view.findViewById(R.id.txtEventosPendientes);

        FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
        //fragmentTransaction1.add(R.id.fragment_container, new SupervisionFragment());

        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<Evento>();

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


//        = BDFireStore.collection("events")
//                .whereEqualTo("user_id", mAuth.getUid())
//                    .whereEqualTo("active",true);
        mReference = BDFireStore.collection("events")
                .whereEqualTo("user_id", mAuth.getUid())
                    .whereEqualTo("active",true)
                        .orderBy("subproject_name", Query.Direction.ASCENDING);

//        com.google.firebase.firestore.Query query = mReference.orderBy("subproject_name", Query.Direction.ASCENDING);

//        query.orderBy("subproject_name", Query.Direction.DESCENDING);

//                        .orderBy("subproject_name", Query.Direction.DESCENDING);
        //com.google.firebase.firestore.Query query = BDFireStore.collection("events").whereEqualTo("uid",  mAuth.getUid());



        mReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (DocumentSnapshot document : task.getResult()) {
                        contEventos++;
                        EventosPendientes.setText(contEventos+" Eventos pendientes");
                    }
                } else {
                    Log.d("<E> en EventosFragment:", " Error getting documents: ", task.getException());
                }
            }
        });

        FirestoreRecyclerOptions<Evento> options = new FirestoreRecyclerOptions.Builder<Evento>()
                .setQuery(mReference, Evento.class).build();

        EventAdapter = new EventoAdapter(options);

        //contEventos++;


        EventAdapter.setOnItemClickListener(new EventoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Evento evento = documentSnapshot.toObject(Evento.class);

                Total total = new Total();
                total = evento.getTotal();
                String actividad = evento.getType_activity();
                String user_id = evento.getUser_id();
                String title = evento.getTitle();
                String start = evento.getStart();
                String end = evento.getEnd();
                String description = evento.getDescription();
                String idEvento = evento.getId();
                String nameEvent = evento.getTitle();

                int number = total.getNumber();
                String unit = total.getUnit();

                int advanced = evento.getAdvanced();

                List<Recursos> tools = evento.getTools();
                String deleted = evento.getDeleted();
                activeStatus = evento.isActive();


                Intent intent = new Intent(getActivity(), SupervisionActivity.class);
                intent.putExtra("idEvento", idEvento);
                intent.putExtra("actividad", actividad);
                intent.putExtra("nameEvent", nameEvent);
                intent.putExtra("user_id", user_id);
                intent.putExtra("start", start);
                intent.putExtra("end", end);
                intent.putExtra("title", title);
                intent.putExtra("description", description);
                intent.putExtra("deleted", deleted);
                intent.putExtra("advanced", advanced);

                intent.putExtra("number", number);
                intent.putExtra("unit", unit);


                    startActivity(intent);
                    //Toast.makeText(getContext(), "Supervision", Toast.LENGTH_SHORT).show();
//                    StyleableToast.makeText(getContext(), ""+actividad, Toast.LENGTH_SHORT, R.style.sucessToast).show();



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

//    public void onItemSelected(AdapterView<?> parent, View view,
//                               int pos, long id) {
//
//        Toast.makeText(getContext(), ""+ arrayString.get(pos), Toast.LENGTH_SHORT).show();
//        // An item was selected. You can retrieve the selected item using
//        // parent.getItemAtPosition(pos)
//    }
//
//    public void onNothingSelected(AdapterView<?> parent) {
//        // Another interface callback
//    }


}
