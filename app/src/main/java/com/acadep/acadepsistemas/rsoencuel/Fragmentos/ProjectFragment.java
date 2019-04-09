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

import com.acadep.acadepsistemas.rsoencuel.Adapter.ProjectAdapter;
import com.acadep.acadepsistemas.rsoencuel.R;
import com.acadep.acadepsistemas.rsoencuel.model.Project;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ProjectFragment extends Fragment {

    DatabaseReference mDatabase;


    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Query mReference;
    FirebaseFirestore BDFireStore= FirebaseFirestore.getInstance();
    private RecyclerView rv;
    ProjectAdapter projectAdapter;


    public ProjectFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_project, container, false);
        rv = (RecyclerView) view.findViewById(R.id.recycler_project);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {


        String id = ""+mAuth.getUid();
        mReference = BDFireStore.collection("projects").whereEqualTo("users."+mAuth.getUid(), true);
//        mReference_activity = BDFireStore.collection("projects");

        FirestoreRecyclerOptions<Project> options = new FirestoreRecyclerOptions.Builder<Project>()
                .setQuery(mReference, Project.class).build();

        projectAdapter = new ProjectAdapter(options);



        projectAdapter.setOnItemClickListener(new ProjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Project project = documentSnapshot.toObject(Project.class);

                String id = project.getId();
                String project_title = project.getTitle();

                Fragment mifragment = null;
                mifragment = new ActivityFragment();
//

                Bundle bundle = new Bundle();
                bundle.putString("project_id", id);
                bundle.putString("project_title", project_title);

                mifragment.setArguments(bundle);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.Contenedor, mifragment)
                        .addToBackStack("main")
                        .commit();

//                String id = project.getId();
//                Intent intent = new Intent(getActivity(), SubProjectFragment.class);
//                intent.putExtra("project_id", id);
//                startActivity(intent);

//                Toast.makeText(getContext(), "algo", Toast.LENGTH_SHORT).show();
            }
        });

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(projectAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        projectAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        projectAdapter.stopListening();
    }
}
