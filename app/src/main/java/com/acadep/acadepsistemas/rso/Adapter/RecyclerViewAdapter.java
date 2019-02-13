package com.acadep.acadepsistemas.rso.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaExtractor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.acadep.acadepsistemas.rso.Clases.SupervisionActivity;
import com.acadep.acadepsistemas.rso.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter{

    private Context mContext;
    private ArrayList<String> mTypeAdapter;
    private ArrayList<Uri> mImages = new ArrayList<>();
    private OnItemClickListener listener;
    //private AdapterView.OnItemLongClickListener

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mTypeAdapter, ArrayList<Uri> mImages, OnItemClickListener listener) {
        this.mContext = mContext;
        this.mTypeAdapter = mTypeAdapter;
        this.mImages = mImages;
        this.listener = listener;
    }


//    public RecyclerViewAdapter(Context mContext, ArrayList<File> mImages, OnItemClickListener listener) {
//        this.mContext = mContext;
//        this.mImages = mImages;
//        this.listener = listener;
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (mTypeAdapter.get(i).equals("Photo")){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem, viewGroup, false);
            return new ImageTypeViewHolder(view);
        }

        if (mTypeAdapter.get(i).equals("Video")){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_videoview, viewGroup, false);
            return new VideoTypeViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {



//        Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(mImages.get(i)));
//        double heightd = bitmap.getHeight()*.1720430107526882;
//        float heightf =  (float)heightd;
//        Bitmap Bitnew = redimensionarImagenMaximo(bitmap, 512 ,  heightf);
//
//        viewHolder.image.setImageBitmap(Bitnew);

        if (mTypeAdapter.get(i).equals("Photo")){
            Picasso.get()
                    .load(mImages.get(i))
                    .resize(500, 700)
                    .centerCrop()
                    .into(((ImageTypeViewHolder) viewHolder).image);

            ((ImageTypeViewHolder) viewHolder).bind(mImages.get(i), listener);
        }

        if (mTypeAdapter.get(i).equals("Video")){
//            ((VideoTypeViewHolder) viewHolder).videoView.setVideoPath(String.valueOf(mImages.get(i)));
            ((VideoTypeViewHolder) viewHolder).videoView.setVideoURI((mImages.get(i)));
//            ((VideoTypeViewHolder) viewHolder).videoView.setVideoURI(Uri.parse("https://www.youtube.com/watch?v=dbB-mICjkQM"));
            MediaController mediaController = new MediaController(mContext);
            mediaController.setAnchorView(((VideoTypeViewHolder) viewHolder).videoView);
//            mediaController.setMediaPlayer(((VideoTypeViewHolder) viewHolder).videoView);
            ((VideoTypeViewHolder) viewHolder).videoView.setMediaController(mediaController);

//            ((VideoTypeViewHolder) viewHolder).videoView.seekTo(100);
//            ((VideoTypeViewHolder) viewHolder).videoView.requestFocus();
//            ((VideoTypeViewHolder) viewHolder).videoView.start();
        }





    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    // Clase y funciones para la imagen
    public class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
//        RelativeLayout parent_layout;
        public ImageTypeViewHolder(@NonNull View itemView) {
            super(itemView);

            this.image = itemView.findViewById(R.id.image_recycler);
//            parent_layout = itemView.findViewById(R.id.parent_layout);
        }

            public void bind(final Uri mImage, final OnItemClickListener listener){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(mImage, getAdapterPosition());
                }
            });
            }
    }

    public interface OnItemClickListener{
        void OnItemClick(Uri mImage, int position);
    }

    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth){
        //Redimensionamos
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }

    // Clase y funciones para la imagen

    // ------------------------------------------------------------------

    // Clase y funciones para la video
    public class VideoTypeViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;


        public VideoTypeViewHolder(@NonNull View itemView) {
            super(itemView);

            this.videoView = itemView.findViewById(R.id.video_recycler);
        }
    }



    // Clase y funciones para la video
















}
