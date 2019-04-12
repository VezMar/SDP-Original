package com.acadep.acadepsistemas.rso.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acadep.acadepsistemas.rso.R;

import java.util.ArrayList;

public class TypesAdapter extends RecyclerView.Adapter{


    private Context mContext;
    private ArrayList<String> mText;
    private ArrayList<String> mStatus;
    private ArrayList<String> mType;


    private OnItemClickListener listener;

    public TypesAdapter(Context mContext, ArrayList<String> mText, ArrayList<String> mStatus, ArrayList<String> mType, OnItemClickListener listener) {
        this.mContext = mContext;
        this.mText = mText;
        this.mStatus = mStatus;
        this.mType = mType;
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
        Log.i("algo", mText.get(position));
        ((TypesViewHolder) viewHolder).txtStatus.setText(mText.get(position));
        ((TypesViewHolder) viewHolder).txtType.setText(mType.get(position));



        if (mStatus.get(position).equals("activo")){
            ((TypesViewHolder) viewHolder).linearLayout.setBackgroundColor(android.graphics.Color.parseColor("#b42f63f2"));
        }

        if (mStatus.get(position).equals("inactivo")){
            ((TypesViewHolder) viewHolder).linearLayout.setBackgroundColor(android.graphics.Color.parseColor("#b5b5ad"));
        }

        if (mStatus.get(position).equals("realizado")){
            ((TypesViewHolder) viewHolder).linearLayout.setBackgroundColor(android.graphics.Color.parseColor("#e2ffc7"));
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
        TextView txtType;
        public TypesViewHolder(@NonNull View itemView) {
            super(itemView);

            this.linearLayout = itemView.findViewById(R.id.linearLayout);
            this.txtStatus = itemView.findViewById(R.id.txtStatus);
            this.txtType = itemView.findViewById(R.id.txtType);

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
