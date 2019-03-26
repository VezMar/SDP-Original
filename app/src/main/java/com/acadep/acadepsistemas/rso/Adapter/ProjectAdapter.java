package com.acadep.acadepsistemas.rso.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Project;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class ProjectAdapter extends FirestoreRecyclerAdapter<Project, ProjectAdapter.ProjectViewHolder> {

    private OnItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ProjectAdapter(@NonNull FirestoreRecyclerOptions<Project> options) {
        super(options);

    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_project, viewGroup, false);
        return new ProjectViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProjectViewHolder holder, int position, @NonNull Project project) {

//        holder.txtProjectDescription.setText(project.getDescription());
        holder.txtProjectName.setText(project.getTitle());
    }


    public class ProjectViewHolder extends RecyclerView.ViewHolder{

        TextView txtProjectName, txtProjectDescription;
        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);

//        this.txtProjectDescription = itemView.findViewById(R.id.txtProjectDescription);
        this.txtProjectName = itemView.findViewById(R.id.txtProjectName);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener!=null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
