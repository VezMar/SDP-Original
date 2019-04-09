package com.acadep.acadepsistemas.rsoencuel.Fragmentos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acadep.acadepsistemas.rsoencuel.Adapter.SubProjectAdapter;
import com.acadep.acadepsistemas.rsoencuel.R;
import com.acadep.acadepsistemas.rsoencuel.model.SubProject;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SubProjectFragment extends Fragment {


    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Query mReference_Subpro;
    FirebaseFirestore BDFireStore= FirebaseFirestore.getInstance();
    private RecyclerView rv;
    SubProjectAdapter subProjectAdapter;
//    ProjectAdapter projectAdapter;

    private String project_id;

//    ArrayList<String> subProjectList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null){
            project_id = getArguments().getString("project_id");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_subproject, container, false);

        rv = (RecyclerView) view.findViewById(R.id.recycler_subproject);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {


        String id = ""+mAuth.getUid();
        mReference_Subpro = BDFireStore.collection("subprojects")
                    .whereEqualTo(mAuth.getUid(), true)
                        .whereEqualTo("project_id", project_id);
//        mReference_activity = BDFireStore.collection("projects");

        FirestoreRecyclerOptions<SubProject> options = new FirestoreRecyclerOptions.Builder<SubProject>()
                .setQuery(mReference_Subpro, SubProject.class).build();

        subProjectAdapter = new SubProjectAdapter(options);

        subProjectAdapter.setOnItemClickListener(new SubProjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                SubProject subProject = documentSnapshot.toObject(SubProject.class);
                String id = subProject.getId();

                Fragment mifragment = null;
                mifragment = new ActivityFragment();
//

                Bundle bundle = new Bundle();
                bundle.putString("subproject_id", id);

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
        rv.setAdapter(subProjectAdapter);
    }



    @Override
    public void onStart() {
        super.onStart();
        subProjectAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        subProjectAdapter.stopListening();
    }
}
