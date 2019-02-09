package com.acadep.acadepsistemas.rso.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.EventLog;
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
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class EventoAdapter extends FirestoreRecyclerAdapter<Evento, EventoAdapter.EventoHolder>{

    private OnItemClickListener listener;
    static Time today = new Time(Time.getCurrentTimezone());

//    static Date start, end;
    static Date firstDate, secondDate;
    static String start, end;
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

        Calendar calendar = new GregorianCalendar();
        DateTime dateTime = new DateTime();

//        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
////        Date firstDate = null;
//        try {
//            firstDate = sdf.parse(calendar.get(Calendar.YEAR) + "-" + dateTime.getMonthOfYear() + "-" + calendar.get(Calendar.DAY_OF_MONTH));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
////        Date secondDate = null;
//        try {
//            secondDate = sdf.parse(evento.getEnd().substring(0,10));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
//        Log.i("Prueba horas: ", ""+diffInMillies);
//        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
//        Log.i("Prueba horas: ", ""+diff);

        String string = evento.getEnd();
        SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(string);
            holder.txtview_end.setText(""+date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date); // Sat Jan 02 00:00:00 GMT 2010


        LocalDateTime timeNow = LocalDateTime.now();
        int mni = evento.getEnd().length();
        LocalDateTime timeEnd = LocalDateTime.parse(evento.getEnd().substring(0, (mni-1)));

        Period p = new Period(timeNow, timeEnd);
        long hours = p.getHours();
        long minutes = p.getMinutes();

        String formato = String.format("%%0%dd", 2);

//        return Long.toString(hours)+":"+String.format(formato, minutes);

        Log.i("Prueba horas : ", Long.toString(hours)+":"+String.format(formato, minutes));
        Log.i("Prueba horas : ", ""+timeNow);
        Log.i("Prueba horas : ", ""+timeEnd);
        start = evento.getStart().substring(0,10);
        holder.txtview_start.setText(""+start);
        end = evento.getEnd().substring(0,10);
        holder.txtview_end.setText(""+end);




        holder.txtview_actividad.setText(evento.getType_activity());
        holder.txtview_name.setText(evento.getTitle());

    }

    @NonNull
    @Override
    public EventoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_recycler, viewGroup, false);
        return new EventoHolder(v);
    }



    public class EventoHolder extends RecyclerView.ViewHolder{

        TextView txtview_actividad, txtview_trabajador,txtview_uid;
        TextView txtview_name, txtview_start, txtview_end, txtview_active;
        TextView txtDescripcion, txtHrs;

        public EventoHolder(@NonNull View itemView) {
            super(itemView);

            txtview_actividad = (TextView) itemView.findViewById(R.id.txtview_actividad);
            //txtview_trabajador = (TextView) itemView.findViewById(R.id.txtview_trabajador);
            txtview_name = (TextView) itemView.findViewById(R.id.txtview_name);
            txtview_start = (TextView) itemView.findViewById(R.id.txtview_start);
            txtview_end = (TextView) itemView.findViewById(R.id.txtview_end);

            txtHrs = (TextView) itemView.findViewById(R.id.txtHrs);
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
