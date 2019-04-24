package com.acadep.acadepsistemas.rso.Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Evento;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EventoAdapter extends FirestoreRecyclerAdapter<Evento, EventoAdapter.EventoHolder>{

    private OnItemClickListener listener;
    static Time today = new Time(Time.getCurrentTimezone());

//    static Date start, end;
    static Date firstDate, secondDate;
    static String start, end , hrStart, hrEnd;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public EventoAdapter(@NonNull FirestoreRecyclerOptions<Evento> options) {
        super(options);

    }


    @Override
    protected void onBindViewHolder(@NonNull EventoHolder holder, int position, @NonNull Evento evento) {




        if (evento.getType()!=null) {
            if (evento.getType().equals("additional")) {
                holder.txtTarea.setText("adicional:");
//                holder.card_Event.setBackgroundColor(Color.rgb(0, 196, 255));
            }
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m");
        SimpleDateFormat dateFormatNow = new SimpleDateFormat("hh: mm: ss a dd-MMM-aaaa");

        start = evento.getStart().substring(0,10);
        hrStart = evento.getStart().substring(11, evento.getStart().toString().length());

        end = evento.getEnd().substring(0,10);
        hrEnd = evento.getEnd().substring(11, evento.getEnd().toString().length());


        try {
            firstDate = dateFormat.parse( start+ " " + hrStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            secondDate = dateFormat.parse( end+ " " + hrEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }


//        try {
//            firstDate = dateFormatNow.parse(String.valueOf(new Date()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        Date Fecha = new Date();
        firstDate = new Date();

        int diferencia=(int) ((secondDate.getTime()-firstDate.getTime())/1000);

        int dias=0;
        int horas=0;
        int minutos=0;
        if(diferencia>86400) {
            dias=(int)Math.floor(diferencia/86400);
            diferencia=diferencia-(dias*86400);
        }
        if(diferencia>3600) {
            horas=(int)Math.floor(diferencia/3600);
            diferencia=diferencia-(horas*3600);
        }
        if(diferencia>60) {
            minutos=(int)Math.floor(diferencia/60);
            diferencia=diferencia-(minutos*60);
        }

//        Log.i("Hora-restante", "Hay "+dias+" dias, "+horas+" horas, "+minutos+" minutos y "+diferencia+" segundos de diferencia");

//        holder.txtRest.setText(dias+"d- "+horas+"h- "+ minutos+"m");
        if(dias>=0 && horas>=0 && minutos>0){
            holder.txtTRestante.setText("Tiempo Restante: " +dias+"d - "+horas+"h - "+ minutos+"m");
        }else{
            holder.txtTRestante.setText("Sin Tiempo Restante, tarea con retraso");
        }

        holder.txtview_start.setText(""+start);
        holder.txtview_end.setText(""+end);



//        holder.txtview_subProyecto.setText(evento.getSubproject_name());
//        holder.txtview_activity.setText(evento.getActivity_name());
//        holder.txtview_actividad.setText(evento.getType_activity());
        holder.txtview_name.setText(evento.getTitle());

    }

    @NonNull
    @Override
    public EventoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_recycler_eventos, viewGroup, false);
        return new EventoHolder(v);
    }



    public class EventoHolder extends RecyclerView.ViewHolder{

        TextView txtview_actividad, txtview_trabajador,txtview_uid;
        TextView txtview_name, txtview_start, txtview_end, txtview_active, txtview_subProyecto, txtview_activity;
        TextView txtDescripcion, txtRest;
        TextView txtTarea;
        TextView txtTRestante;
        CardView card_Event;

        public EventoHolder(@NonNull View itemView) {
            super(itemView);

//            txtview_actividad = (TextView) itemView.findViewById(R.id.txtview_actividad);
            //txtview_trabajador = (TextView) itemView.findViewById(R.id.txtview_trabajador);
            txtview_name = (TextView) itemView.findViewById(R.id.txtview_name);
            txtview_start = (TextView) itemView.findViewById(R.id.txtview_start);
            txtview_end = (TextView) itemView.findViewById(R.id.txtview_end);
            txtTarea = (TextView) itemView.findViewById(R.id.txtTarea);

            card_Event = itemView.findViewById(R.id.card_Event);

//            txtview_subProyecto  = (TextView) itemView.findViewById(R.id.txtview_subProyecto);
//            txtview_activity  = (TextView) itemView.findViewById(R.id.txtview_activity);

//            txtRest = (TextView) itemView.findViewById(R.id.txtRest);
            txtTRestante = (TextView) itemView.findViewById(R.id.txtTRestante);
            //txtDescripcion = (TextView) itemView.findViewById(R.id.txtDescripcion);
            //txtview_active = (TextView) itemView.findViewById(R.id.txtview_active);
            // txtview_uid = (TextView) itemView.findViewById(R.id.txtview_uid);
            //btnRealizar = (Button) itemView.findViewById(R.id.btnRealizar);

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
