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

import com.acadep.acadepsistemas.rso.Adapter.ActivityAdapter;
import com.acadep.acadepsistemas.rso.Clases.ExtraActivity;
import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Activity;
import com.acadep.acadepsistemas.rso.model.Evento;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ActivitysFragment extends Fragment {

    FirebaseFirestore BDFireStore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;

    RecyclerView recyclerView;
    ActivityAdapter activityAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activitys_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        setUpRecyclerView();


        return view;
    }

    private void setUpRecyclerView() {
        com.google.firebase.firestore.Query query = BDFireStore.collection("events").whereEqualTo("user_id", mAuth.getUid()).whereEqualTo("active",true);

        FirestoreRecyclerOptions<Activity> options = new FirestoreRecyclerOptions.Builder<Activity>()
                .setQuery(query, Activity.class)
                .build();

        activityAdapter = new ActivityAdapter(options);

        activityAdapter.setOnItemClickListener(new ActivityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Evento evento = documentSnapshot.toObject(Evento.class);

                String activity_id = evento.getActivity_id();

                Intent intent = new Intent(getActivity(), ExtraActivity.class);
                intent.putExtra("activity_id", activity_id);

                startActivity(intent);

            }
        });

        recyclerView.setAdapter(activityAdapter);
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
