package com.acadep.acadepsistemas.rso.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acadep.acadepsistemas.rso.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.type.Color;

import java.util.ArrayList;

public class TypesAdapter extends RecyclerView.Adapter{


    private Context mContext;
    private ArrayList<String> mText;
    private ArrayList<String> mStatus;

    private OnItemClickListener listener;

    public TypesAdapter(Context mContext, ArrayList<String> mText, ArrayList<String> mStatus, OnItemClickListener listener) {
        this.mContext = mContext;
        this.mText = mText;
        this.mStatus = mStatus;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_type, viewGroup, false);
        return new TypesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((TypesViewHolder) viewHolder).txtStatus.setText(mText.get(position));

        if (mStatus.get(position).equals("activo")){
            ((TypesViewHolder) viewHolder).linearLayout.setBackgroundColor(android.graphics.Color.parseColor("#2f63f2"));
        }

        if (mStatus.get(position).equals("inactivo")){
            ((TypesViewHolder) viewHolder).linearLayout.setBackgroundColor(android.graphics.Color.parseColor("#7c7c7c"));
        }

        if (mStatus.get(position).equals("realizado")){
            ((TypesViewHolder) viewHolder).linearLayout.setBackgroundColor(android.graphics.Color.parseColor("#00ff00"));
        }

        ((TypesViewHolder) viewHolder).bind(mStatus.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return mText.size();
    }


    public class TypesViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearLayout;
        TextView txtStatus;
        public TypesViewHolder(@NonNull View itemView) {
            super(itemView);

            this.linearLayout = itemView.findViewById(R.id.linearLayout);
            this.txtStatus = itemView.findViewById(R.id.txtStatus);

        }

        public void bind(final String mStatus, final OnItemClickListener listener){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(mStatus, getAdapterPosition());
//                    listener.OnItemClick(mStatus, getAdapterPosition());


                }
            });
        }


    }

    public interface OnItemClickListener{
        void OnItemClick(String mStatus, int adapterPosition);


    }


    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
