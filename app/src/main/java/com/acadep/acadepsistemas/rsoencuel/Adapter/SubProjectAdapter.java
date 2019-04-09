package com.acadep.acadepsistemas.rsoencuel.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acadep.acadepsistemas.rsoencuel.R;
import com.acadep.acadepsistemas.rsoencuel.model.SubProject;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class SubProjectAdapter extends FirestoreRecyclerAdapter<SubProject, SubProjectAdapter.SubProjectViewHolder>{
    private OnItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SubProjectAdapter(@NonNull FirestoreRecyclerOptions<SubProject> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SubProjectViewHolder holder, int position, @NonNull SubProject model) {

        holder.txtSubProject.setText(model.getTitle());
    }

    @NonNull
    @Override
    public SubProjectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_subproject, viewGroup, false);
        return new SubProjectViewHolder(v);

    }

    public class  SubProjectViewHolder extends RecyclerView.ViewHolder{

        TextView txtSubProject;
        public SubProjectViewHolder(@NonNull View itemView) {
            super(itemView);

            this.txtSubProject = itemView.findViewById(R.id.txtSubProject);

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