package com.acadep.acadepsistemas.rso.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.acadep.acadepsistemas.rso.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Bitmap> mImages = new ArrayList<>();
    private OnItemClickListener listener;
    //private AdapterView.OnItemLongClickListener


    public RecyclerViewAdapter(Context mContext, ArrayList<Bitmap> mImages, OnItemClickListener listener) {
        this.mContext = mContext;
        this.mImages = mImages;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
//        Glide.with(mContext)
//                .asBitmap()
//                .load(mImages.get(i))
//                .into(viewHolder.image);

        viewHolder.image.setImageBitmap(mImages.get(i));

        viewHolder.bind(mImages.get(i), listener);

//        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "Pos " + i, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        RelativeLayout parent_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image_recycler);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }

            public void bind(final Bitmap mImage, final OnItemClickListener listener){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(mImage, getAdapterPosition());
                }
            });
            }
    }

    public interface OnItemClickListener{
        void OnItemClick(Bitmap mImage, int position);
    }


}
