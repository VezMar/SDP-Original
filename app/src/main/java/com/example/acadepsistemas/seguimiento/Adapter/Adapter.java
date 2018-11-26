package com.example.acadepsistemas.seguimiento.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.acadepsistemas.seguimiento.Fragmentos.EventosFragment;
import com.example.acadepsistemas.seguimiento.R;
import com.example.acadepsistemas.seguimiento.model.Evento;

import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<Adapter.EventoviewHolder> implements View.OnClickListener {


        EventosFragment context;
        ArrayList<Evento> evento;

        private View.OnClickListener listener;

        public Adapter(EventosFragment c, ArrayList<Evento> e) {


            context = c;
            evento = e;

        }


    @NonNull
    @Override
    public EventoviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent .getContext()).inflate(R.layout.row_recycler, parent,false);
        EventoviewHolder holder = new EventoviewHolder(v);

        v.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventoviewHolder holder, int position) {

       holder.txtview_actividad.setText(evento.get(position).getActividad());
        holder.txtview_uid.setText(evento.get(position).getUid());
        holder.txtview_trabajador.setText(evento.get(position).getTrabajador());



    }

    @Override
    public int getItemCount() {
        return evento.size();
    }


    public void setOnclickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public class EventoviewHolder extends RecyclerView.ViewHolder{

        TextView txtview_actividad, txtview_trabajador,txtview_uid;
        Button btnRealizar;

        public EventoviewHolder(@NonNull View itemView) {
            super(itemView);

            txtview_actividad = (TextView) itemView.findViewById(R.id.txtview_actividad);
            txtview_trabajador = (TextView) itemView.findViewById(R.id.txtview_trabajador);
            txtview_uid = (TextView) itemView.findViewById(R.id.txtview_uid);
            //btnRealizar = (Button) itemView.findViewById(R.id.btnRealizar);

        }


    }}
