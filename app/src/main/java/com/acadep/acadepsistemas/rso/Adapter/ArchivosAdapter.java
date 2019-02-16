package com.acadep.acadepsistemas.rso.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.acadep.acadepsistemas.rso.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArchivosAdapter extends RecyclerView.Adapter{

    private Context mContext;
    private  List<Uri> ArchivosUris;
    private List<String> Type_Archivo;
    private List<String> Name_Archivo;
    private ArrayList<Boolean> archivoChecked;

    public ArchivosAdapter(Context mContext, List<Uri> archivosUris, List<String> type_Archivo, List<String> name_Archivo, ArrayList<Boolean> archivoChecked) {
        this.mContext = mContext;
        ArchivosUris = archivosUris;
        Type_Archivo = type_Archivo;
        Name_Archivo = name_Archivo;
        this.archivoChecked = archivoChecked;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_archivos, viewGroup, false);
        ArchivosViewHolder archivosViewHolder = new ArchivosViewHolder(view);
        return archivosViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (Type_Archivo.get(i).equals("Video")) {
            ((ArchivosViewHolder)viewHolder).img_Archivos.setImageResource(R.drawable.ic_videocam_black_24dp);
        }

        if (Type_Archivo.get(i).equals("Audio")) {
            ((ArchivosViewHolder)viewHolder).img_Archivos.setImageResource(R.drawable.ic_keyboard_voice_black_24dp);
        }

        if (Type_Archivo.get(i).equals("Docx")) {
            ((ArchivosViewHolder)viewHolder).img_Archivos.setImageResource(R.drawable.ic_insert_drive_file_black_24dp);

        }
        if (Type_Archivo.get(i).equals("PDF")) {
            ((ArchivosViewHolder)viewHolder).img_Archivos.setImageResource(R.drawable.ic_picture_as_pdf_black_24dp);
        }

        ((ArchivosViewHolder)viewHolder).txType_Archivo.setText(""+Type_Archivo.get(i));
        ((ArchivosViewHolder)viewHolder).txName_Archivo.setText(""+Name_Archivo.get(i));
        ((ArchivosViewHolder)viewHolder).txtUbicacion_Archivo.setText(""+ArchivosUris.get(i));
        ((ArchivosViewHolder)viewHolder).myCheckBox_Archivos.setChecked(archivoChecked.get(i));


    }

    @Override
    public int getItemCount() {
        if (ArchivosUris==null){
            return 0;
        }else {
            return ArchivosUris.size();
        }
    }

    public void deleteCheckedFiles(){
        for (int i=ArchivosUris.size(); 0<=i; i--){
            if (archivoChecked.get(i)==true){
                Log.i("mItemChecked - ", "Borrado" + i);
            }else{
                Log.i("mItemChecked - ", "Es false" + i);
            }
        }
    }

    public class ArchivosViewHolder extends RecyclerView.ViewHolder{

        ImageView img_Archivos;
        TextView txType_Archivo, txName_Archivo, txtUbicacion_Archivo;
        CheckBox myCheckBox_Archivos;
        public ArchivosViewHolder(@NonNull View itemView) {
            super(itemView);

            this.img_Archivos = itemView.findViewById(R.id.img_Archivos);
            this.txType_Archivo = itemView.findViewById(R.id.txType_Archivo);
            this.txName_Archivo = itemView.findViewById(R.id.txName_Archivo);
            this.txtUbicacion_Archivo = itemView.findViewById(R.id.txtUbicacion_Archivo);
            this.myCheckBox_Archivos = itemView.findViewById(R.id.myCheckBox_Archivos);

            myCheckBox_Archivos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myCheckBox_Archivos.isChecked()) {
                        archivoChecked.set(getAdapterPosition(), true);
                    } else {
                        archivoChecked.set(getAdapterPosition(), false);
                    }
                    Log.i("checkBoxs archivo ", "" + archivoChecked.get(getAdapterPosition()));
                }
            });
        }
    }
}
