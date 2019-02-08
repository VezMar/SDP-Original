package com.acadep.acadepsistemas.rso.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Activity;
import com.acadep.acadepsistemas.rso.model.Evento;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;


public class ActivityAdapter extends FirestoreRecyclerAdapter<Activity, ActivityAdapter.ActivityHolder>{
    private OnItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ActivityAdapter(@NonNull FirestoreRecyclerOptions<Activity> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ActivityHolder holder, int position, @NonNull Activity activity) {
        holder.txtTitle.setText(activity.getTitle());
        holder.txtID.setText(activity.getId());
    }

    @NonNull
    @Override
    public ActivityHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_recycler_2, viewGroup, false);
        return new ActivityHolder(v);
    }

    class ActivityHolder extends RecyclerView.ViewHolder{
        TextView txtTitle;
        TextView txtID;


        public ActivityHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitulo);
            txtID = itemView.findViewById(R.id.txtId_Acitivy);

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
