package com.acadep.acadepsistemas.rso.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Evento;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class EventoAdapter extends FirestoreRecyclerAdapter<Evento, EventoAdapter.EventoHolder>{
    private OnItemClickListener listener;
    static Time today = new Time(Time.getCurrentTimezone());

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

       String date = evento.getEnd().getDate();

        String horaFinal = evento.getEnd().getTime();
        //String
        String diaFinal = date.substring(8,10);
        String diaActual = ""+today.monthDay;
//        int HorasFinal = Integer.parseInt(diaFinal)*24;
//        int HorasActual = Integer.parseInt(diaActual)*24;
        int diaRestante = Integer.parseInt(diaFinal) - Integer.parseInt(diaActual);


        holder.txtview_actividad.setText(evento.getType_activity());
         //holder.txtview_uid.setText(evento.get(position).getUid());
        //holder.txtview_trabajador.setText(evento.get(position).getTrabajador());

        //holder.txtview_start.setText(evento.getStart());
        //holder.txtview_end.setText(evento.getEnd());
        holder.txtview_start.setText(evento.getStart().getDate());
        holder.txtview_end.setText(evento.getEnd().getDate());
        holder.txtview_name.setText(evento.getName());
        //holder.txtDescripcion.setText(evento.getDescription());
        //holder.txtHrs.setText(""+diaRestante);
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
