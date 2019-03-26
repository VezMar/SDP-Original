package com.acadep.acadepsistemas.rso.Fragmentos;

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

import com.acadep.acadepsistemas.rso.Adapter.TypesAdapter;
import com.acadep.acadepsistemas.rso.Clases.EvidenceActivity;
import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Configuration;
import com.acadep.acadepsistemas.rso.model.Event_types;
import com.acadep.acadepsistemas.rso.model.Total;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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


    static RecyclerView mRecyclerViewFiles;
    static RecyclerView.Adapter mAdapterFiles;
    static RecyclerView.LayoutManager mLayaoutManagerFiles;


    FirebaseFirestore BDFireStore = FirebaseFirestore.getInstance();

    private List<Event_types> event_types;
    private static boolean Tbefore;
    private static boolean Tduring;
    private static boolean Tafter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



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


        return view;
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

                        if (Tbefore){
                            if (before_complete){
                                mStatus.add("realizado");
                            }else{
                                mStatus.add("activo");
                            }
                            mText.add("Inicio");
                        }

                        if (Tduring){
                            mText.add("Ejecución");
                            if (during_complete && before_complete){
                                mStatus.add("realizado");
                            }else if (before_complete && !during_complete){
                                mStatus.add("activo");
                            }else if (!before_complete){
                                mStatus.add("inactivo");
                            }
                        }

                        if (Tafter == true){
                            mText.add("Termino");
                            if (before_complete=true && during_complete==true){
                                mStatus.add("activo");
                            }else if (before_complete==true && during_complete==false){
                                mStatus.add("inactivo");
                            }else if (before_complete==false && during_complete==false){
                                mStatus.add("inactivo");
                            }
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

        mAdapterFiles = new TypesAdapter(getContext(), mText, mStatus, new TypesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(String mStatus, int adapterPosition) {
                if (mStatus.equals("activo")){
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
