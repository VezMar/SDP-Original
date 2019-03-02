package com.acadep.acadepsistemas.rso.Fragmentos;

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

import com.acadep.acadepsistemas.rso.Adapter.ActivityAdapter;
import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Activity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ActivityFragment extends Fragment {


    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Query mReference_activity;
    FirebaseFirestore BDFireStore= FirebaseFirestore.getInstance();
    private RecyclerView rv;
    ActivityAdapter activityAdapter;

    private String project_id;
    private String project_title;

    TextView Subproyect;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null){
            project_id = getArguments().getString("project_id");
            project_title = getArguments().getString("project_title");
        }
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activitys_fragment, container, false);
        rv = (RecyclerView) view.findViewById(R.id.recycler_2);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        initRecyclerView();

        Subproyect = view.findViewById(R.id.txt_SubProyecto);
        Subproyect.setText(""+ project_title);

        return view;
    }

    private void initRecyclerView() {
        mReference_activity = BDFireStore.collection("activities")
                .whereEqualTo("users."+mAuth.getUid(), true)
                .whereEqualTo("project_id", project_id);
//        mReference_activity = BDFireStore.collection("projects");

        FirestoreRecyclerOptions<Activity> options = new FirestoreRecyclerOptions.Builder<Activity>()
                .setQuery(mReference_activity, Activity.class).build();

        activityAdapter = new ActivityAdapter(options);

        activityAdapter.setOnItemClickListener(new ActivityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Activity activity = documentSnapshot.toObject(Activity.class);

                String id = activity.getId();
                String activity_title = activity.getTitle();

                Fragment mifragment = null;
                mifragment = new EventosFragment();
//

                Bundle bundle = new Bundle();
                bundle.putString("activity_id", id);
                bundle.putString("project_title", project_title);
                bundle.putString("activity_title", activity_title);

                mifragment.setArguments(bundle);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.Contenedor, mifragment)
                        .addToBackStack("main")
                        .commit();
            }
        });



        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(activityAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        activityAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        activityAdapter.stopListening();
    }



}
