package com.acadep.acadepsistemas.rsoencuel.Fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.acadep.acadepsistemas.rsoencuel.Adapter.TypesAdapter;
import com.acadep.acadepsistemas.rsoencuel.Clases.EvidenceActivity;
import com.acadep.acadepsistemas.rsoencuel.R;
import com.acadep.acadepsistemas.rsoencuel.model.Configuration;
import com.acadep.acadepsistemas.rsoencuel.model.Event_types;
import com.acadep.acadepsistemas.rsoencuel.model.Evento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class TypesFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private String project_id;
    private String project_title;
    private String activity_id;
    private String activity_title;
    private String event_id;
    private String event_title;
    private String actividad;
    private boolean during_complete;
    private boolean before_complete;


    private  String idevent;
    private static String nameEvent;
    private static String user_id;
    private static String trabajador;
    private static String name;
    private static String start;
    private static String end;
    private static String idactivity;
    private static String description;
    private static String title_event;
    private static ArrayList<String> tools;

    private static int ava;
    private static int avanced;


    private static int number;
    private static String unit;
    private static String deleted;


    private TextView txt_SubProyecto;
    private TextView txtAct;
    private TextView txtEve;


    private ArrayList<String> mText = new ArrayList<>();
    private ArrayList<String> mStatus = new ArrayList<>();
    private ArrayList<String> mType = new ArrayList<>();


    static RecyclerView mRecyclerViewFiles;
    static RecyclerView.Adapter mAdapterFiles;
    static RecyclerView.LayoutManager mLayaoutManagerFiles;


    FirebaseFirestore BDFireStore = FirebaseFirestore.getInstance();
    Query mQuery;
    DocumentReference docRef;

    private List<Event_types> event_types;
    private static boolean Tbefore;
    private static boolean Tduring;
    private static boolean Tafter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        Toast.makeText(getContext(), "Nuevo", Toast.LENGTH_SHORT).show();

        if (getArguments()!=null){
            activity_id = getArguments().getString("activity_id");
            project_title = getArguments().getString("project_title");
            activity_title = getArguments().getString("activity_title");
            project_id = getArguments().getString("project_id");
            event_id = getArguments().getString("event_id");
            event_title = getArguments().getString("event_title");
            actividad = getArguments().getString("actividad");
            during_complete = getArguments().getBoolean("during_complete");
            before_complete = getArguments().getBoolean("before_complete");

           idevent = getArguments().getString("idEvento");
           nameEvent = getArguments().getString("nameEvent");
           actividad = getArguments().getString("actividad");
           user_id = getArguments().getString("user_id");
           trabajador= getArguments().getString("trabajador");
           start= getArguments().getString("start");
           end= getArguments().getString("end");
           title_event = getArguments().getString("title_event");
           idactivity= getArguments().getString("idactivity");
           description= getArguments().getString("description");
           tools = getArguments().getStringArrayList("tools");
           deleted = getArguments().getString("deleted");
           ava = getArguments().getInt("ava");
           number = getArguments().getInt("number");
           unit = getArguments().getString("unit");
           during_complete = getArguments().getBoolean("during_complete");
           before_complete = getArguments().getBoolean("before_complete");

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_types, container, false);

        txt_SubProyecto = view.findViewById(R.id.txt_SubProyecto);
        txt_SubProyecto.setText(""+project_title);
        txtAct = view.findViewById(R.id.txtAct);
        txtAct.setText(""+activity_title);
        txtEve = view.findViewById(R.id.txtEve);
        txtEve.setText(""+event_title);

        initConfiguration(view);
        initEventListener();


        return view;
    }

    private void initEventListener() {
//        BDFireStore.collection("events").document(""+idevent)
        docRef = BDFireStore.collection("events").document("" + idevent);

        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                Evento evento = documentSnapshot.toObject(Evento.class);

//                Toast.makeText(getContext(), ""+mText.size(), Toast.LENGTH_SHORT).show();
                before_complete = evento.isBefore_complete();
                during_complete = evento.isDuring_complete();

                int indexBefore = mText.indexOf("Inicio");
                int indexDuring = mText.indexOf("Ejecución");
                int indexAfter = mText.indexOf("Termino");
                boolean active = evento.isActive();

//                if (!active){
//                    getActivity().finish();
//                    Intent intent = new Intent(getActivity(), MainActivity.class);
//                    startActivity(intent);
//
//
//                }

                if (mStatus.size()>0) {
                    if (Tbefore) {
                        if (before_complete) {
                            mStatus.set(indexBefore, "realizado");
                            mType.set(indexBefore, "Realizado");
                            mAdapterFiles.notifyItemChanged(indexBefore);
                        } else {
                            mStatus.set(indexBefore, "activo");
                            mType.set(indexBefore, "Activo");
                            mAdapterFiles.notifyItemChanged(indexBefore);
                        }
                    }



                    if (Tduring) {
                        if (during_complete && before_complete) {
                            mStatus.set(indexDuring, "realizado");
                            mType.set(indexDuring, "Realizado");
                            mAdapterFiles.notifyItemChanged(indexDuring);
                        } else if (before_complete && !during_complete) {
                            mStatus.set(indexDuring, "activo");
                            mType.set(indexDuring, "Activo");
                            mAdapterFiles.notifyItemChanged(indexDuring);
                        } else if (!before_complete) {
                            mStatus.set(indexDuring, "inactivo");
                            mType.set(indexDuring, "Inactivo");
                            mAdapterFiles.notifyItemChanged(indexDuring);
                        }
                    }

                    if (Tafter) {
                        if (!active){
                            mStatus.set(indexDuring, "realizado");
                            mType.set(indexDuring, "Realizado");
                        }else if (before_complete && during_complete){
                            mStatus.set(indexAfter, "activo");
                            mType.set(indexAfter, "Activo");
                            mAdapterFiles.notifyItemChanged(indexAfter);
                        }else if (!before_complete || !during_complete){
                            mStatus.set(indexAfter, "inactivo");
                            mType.set(indexAfter, "Inactivo");
                            mAdapterFiles.notifyItemChanged(indexAfter);
                        }
                    }
                }


            }
        });
    }


    private void initConfiguration(final View view) {


        BDFireStore
                .collection("configuration")
                .document("global")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Configuration configuration = documentSnapshot.toObject(Configuration.class);

                event_types = configuration.getEvent_types();

                for (int i = 0; i< event_types.size(); i++){
                    String name = event_types.get(i).getName();
                    if (name.equals(actividad)){
                        Tbefore = event_types.get(i).isBefore();
                        Tduring = event_types.get(i).isDuring();
                        Tafter = event_types.get(i).isAfter();

                        if (!Tbefore){
                            before_complete=true;
                            BDFireStore.collection("events").document(""+idevent).update("before_complete", before_complete);
                        }

                        if (!Tduring){
                            during_complete=true;
                            BDFireStore.collection("events").document(""+idevent).update("during_complete", during_complete);
                        }

                        if (Tbefore){
                            mText.add("Inicio");
                            if (before_complete){
                                mStatus.add("realizado");
                                mType.add("Realizado");
                            }else{
                                mStatus.add("activo");
                                mType.add("Activo");
                            }
                        }

                        if (Tduring){
                            mText.add("Ejecución");
                            if (during_complete && before_complete){
                                mStatus.add("realizado");
                                mType.add("Realizado");

                            }else if (before_complete && !during_complete){
                                mStatus.add("activo");
                                mType.add("Activo");
                            }else if (!before_complete){
                                mStatus.add("inactivo");
                                mType.add("Inactivo");
                            }
                        }

                        if (Tafter){
                            mText.add("Termino");
                            if (before_complete && during_complete){
                                mStatus.add("activo");
                                mType.add("Activo");
                            }else if (!before_complete || !during_complete){
                                mStatus.add("inactivo");
                                mType.add("Inactivo");
                            }
//                            if (before_complete && during_complete){
//                                mStatus.add("activo");
//                                mType.add("Activo");
//                            }else if (before_complete && !during_complete){
//                                mStatus.add("inactivo");
//                                mType.add("Inactivo");
//                            }else if (!before_complete && during_complete){
//                                mStatus.add("inactivo");
//                                mType.add("Inactivo");
//                            }else if (!before_complete && !during_complete){
//                                mStatus.add("inactivo");
//                                mType.add("Inactivo");
//                            }
                        }


                        break;
                    }
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                initRecyclerView(view);

            }
        });
    }

    private void initRecyclerView(View view) {
        mRecyclerViewFiles = view.findViewById(R.id.recycler_types);
        mLayaoutManagerFiles = new LinearLayoutManager(getContext());

        mAdapterFiles = new TypesAdapter(getContext(), mText, mStatus, mType, new TypesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(String mStatus, int adapterPosition) {

                if (mStatus.equals("activo")){

//                    getActivity().getSupportFragmentManager().beginTransaction().remove(new TypesFragment()).commit();

                    Intent intent = new Intent(getActivity(), EvidenceActivity.class);

                    intent.putExtra("idEvento", event_id);
                    intent.putExtra("actividad", actividad);
                    intent.putExtra("nameEvent", nameEvent);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("start", start);
                    intent.putExtra("end", end);
                    intent.putExtra("title", event_title);
                    intent.putExtra("description", description);
                    intent.putExtra("deleted", deleted);
                    intent.putExtra("ava", ava);
                    intent.putExtra("during_complete", during_complete);
                    intent.putExtra("before_complete", before_complete);

                    intent.putExtra("activity_id",activity_id);
                    intent.putExtra("project_title",project_title);
                    intent.putExtra("project_id",project_id);
                    intent.putExtra("activity_title",activity_title);

                    intent.putExtra("number", number);
                    intent.putExtra("unit", unit);

                    startActivity(intent);
//                    getActivity().finish();


                }else if (mStatus.equals("realizado")){
                    Toast.makeText(getContext(), "Ya has realizado esta etapa", Toast.LENGTH_SHORT).show();
                }else if (mStatus.equals("inactivo")){
                    Toast.makeText(getContext(), "Etapa aún no disponible", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        mRecyclerViewFiles.setHasFixedSize(true);
//        mRecyclerViewFiles.setItemAnimator(new DefaultItemAnimator());

                mRecyclerViewFiles.setLayoutManager(mLayaoutManagerFiles);
        mRecyclerViewFiles.setAdapter(mAdapterFiles);
    }
}
