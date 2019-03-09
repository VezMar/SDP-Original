package com.acadep.acadepsistemas.rso.Clases.Prueba;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.acadep.acadepsistemas.rso.Clases.Prueba.fragmentosEvidence.ArchivosFragment;
import com.acadep.acadepsistemas.rso.Clases.Prueba.fragmentosEvidence.MultimediaFragment;
import com.acadep.acadepsistemas.rso.Clases.Prueba.fragmentosEvidence.ObservacionesFragment;
import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Foto;

import java.util.ArrayList;
import java.util.List;


public class EvidenceActivity extends AppCompatActivity {

    public EvidenceActivity() {
    }

    public EvidenceActivity(String idevent, String nameEvent, String user_id, String actividad, String trabajador, String name, String start, String end, String idactivity, String description, String title_event, ArrayList<String> tools, int ava, int number, String unit, boolean during_complete, boolean before_complete, String deleted, String edObserv, String edpercentage, TextView txtAvance, TextView txtTotal, BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener) {
        this.idevent = idevent;
        this.nameEvent = nameEvent;
        this.user_id = user_id;
        this.actividad = actividad;
        this.trabajador = trabajador;
        this.name = name;
        this.start = start;
        this.end = end;
        this.idactivity = idactivity;
        this.description = description;
        this.title_event = title_event;
        this.tools = tools;
        this.ava = ava;
        this.number = number;
        this.unit = unit;
        this.during_complete = during_complete;
        this.before_complete = before_complete;
        this.deleted = deleted;
        this.edObserv = edObserv;
        this.edpercentage = edpercentage;
        this.txtAvance = txtAvance;
        this.txtTotal = txtTotal;
        this.mOnNavigationItemSelectedListener = mOnNavigationItemSelectedListener;
    }

    //---------------------------------------------------------------
    // Datos del evento

    private String idevent;
    private String nameEvent;
    private String user_id;
    private String actividad;
    private String trabajador;
    private String name;
    private String start;
    private String end;
    private String idactivity;
    private String description;
    private String title_event;
    private ArrayList<String> tools;
    private int ava;
    private int number;
    private String unit;
    private boolean during_complete;
    private boolean before_complete;
    private String deleted;

    // Datos del evento
//---------------------------------------------------------------
    // Datos de los archivos

    // Datos de los archivos
//---------------------------------------------------------------
    // Datos de la multimedia
    private static ArrayList<String> mTypeAdapter = new ArrayList<>();
    private static ArrayList<Uri> ListVideos = new ArrayList<>();
    private static ArrayList<Boolean> mItemChecked = new ArrayList<>();
    private static ArrayList<Bitmap> mImageBitmap = new ArrayList<>();
    private static List<Foto> multimedia = new ArrayList<>();
    private static int contImg = 0;
    // Datos de la multimedia
//---------------------------------------------------------------
    // Datos de la observación
        //Declaración de componentes
            private String edObserv = "";
            private String edpercentage;
            TextView txtAvance;
            TextView txtTotal;
        //Declaración de componentes

    // Datos de la observación
//---------------------------------------------------------------




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment mifragment = null;
            Boolean FragmentoSeleccionado=false;
            int id = item.getItemId();


                if (id ==R.id.navigation_observaciones){
                    mifragment = new ObservacionesFragment();
                    FragmentoSeleccionado=true;
                }else if (id == R.id.navigation_multmedia) {
                    mifragment = new MultimediaFragment();
                    FragmentoSeleccionado = true;
                }else if (id ==  R.id.navigation_archivos) {
                    mifragment = new ArchivosFragment();
                    FragmentoSeleccionado = true;
                }



            if(FragmentoSeleccionado==true){

                Bundle bundle = new Bundle();
                bundle.putString("idEvento", idevent);
                bundle.putString("nameEvent",nameEvent);
                bundle.putString("actividad",actividad);
                bundle.putString("user_id",user_id);
                bundle.putString("trabajador",trabajador);
                bundle.putString("start",start);
                bundle.putString("end",end);
                bundle.putString("title_event", title_event);
                bundle.putString("idactivity",idactivity);
                bundle.putString("description",description);
                bundle.putStringArrayList("tools",tools);
                bundle.putString("deleted",deleted);
                bundle.putInt("ava",ava);
                bundle.putInt("number",number);
                bundle.putString("unit",unit);
                bundle.putBoolean("during_complete",during_complete);
                bundle.putBoolean("before_complete",before_complete );

                mifragment.setArguments(bundle);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenedor_Evidence, mifragment)
                        .commit();

                item.setChecked(true);
                getSupportActionBar().setTitle(item.getTitle());

            }
            return FragmentoSeleccionado;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evidence);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        recibirDatos();

        Fragment mifragment = null;
//                mifragment = new EventosFragment();
        mifragment = new ObservacionesFragment();

        Bundle bundle = new Bundle();
        bundle.putString("idEvento", idevent);
        bundle.putString("nameEvent",nameEvent);
        bundle.putString("actividad",actividad);
        bundle.putString("user_id",user_id);
        bundle.putString("trabajador",trabajador);
        bundle.putString("start",start);
        bundle.putString("end",end);
        bundle.putString("title_event", title_event);
        bundle.putString("idactivity",idactivity);
        bundle.putString("description",description);
        bundle.putStringArrayList("tools",tools);
        bundle.putString("deleted",deleted);
        bundle.putInt("ava",ava);
        bundle.putInt("number",number);
        bundle.putString("unit",unit);
        bundle.putBoolean("during_complete",during_complete);
        bundle.putBoolean("before_complete",before_complete );

        mifragment.setArguments(bundle);



        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedor_Evidence, mifragment)
                .commit();

    }

    private void recibirDatos() {


        Bundle extras = getIntent().getExtras();
        idevent = extras.getString("idEvento");
        nameEvent = extras.getString("nameEvent");
        actividad = extras.getString("actividad");
        user_id = extras.getString("user_id");
        trabajador= extras.getString("trabajador");
        start= extras.getString("start");
        end= extras.getString("end");
        title_event = extras.getString("title_event");
        idactivity= extras.getString("idactivity");
        description= extras.getString("description");
        tools = extras.getStringArrayList("tools");
        deleted = extras.getString("deleted");
        ava = extras.getInt("ava");
        number = extras.getInt("number");
        unit = extras.getString("unit");
        during_complete = extras.getBoolean("during_complete");
        before_complete = extras.getBoolean("before_complete");
        //active=false;

    }

    public String getIdevent() {
        return idevent;
    }

    public void setIdevent(String idevent) {
        this.idevent = idevent;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(String trabajador) {
        this.trabajador = trabajador;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getIdactivity() {
        return idactivity;
    }

    public void setIdactivity(String idactivity) {
        this.idactivity = idactivity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public ArrayList<String> getTools() {
        return tools;
    }

    public void setTools(ArrayList<String> tools) {
        this.tools = tools;
    }

    public int getAva() {
        return ava;
    }

    public void setAva(int ava) {
        this.ava = ava;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isDuring_complete() {
        return during_complete;
    }

    public void setDuring_complete(boolean during_complete) {
        this.during_complete = during_complete;
    }

    public boolean isBefore_complete() {
        return before_complete;
    }

    public void setBefore_complete(boolean before_complete) {
        this.before_complete = before_complete;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getEdObserv() {
        return edObserv;
    }

    public void setEdObserv(String edObserv) {
        this.edObserv = edObserv;
    }

    public String getEdpercentage() {
        return edpercentage;
    }

    public void setEdpercentage(String edpercentage) {
        this.edpercentage = edpercentage;
    }

    public TextView getTxtAvance() {
        return txtAvance;
    }

    public void setTxtAvance(TextView txtAvance) {
        this.txtAvance = txtAvance;
    }

    public TextView getTxtTotal() {
        return txtTotal;
    }

    public void setTxtTotal(TextView txtTotal) {
        this.txtTotal = txtTotal;
    }

    public String getTitle_event() {
        return title_event;
    }

    public void setTitle_event(String title_event) {
        this.title_event = title_event;
    }

    public BottomNavigationView.OnNavigationItemSelectedListener getmOnNavigationItemSelectedListener() {
        return mOnNavigationItemSelectedListener;
    }

    public void setmOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener) {
        this.mOnNavigationItemSelectedListener = mOnNavigationItemSelectedListener;
    }
}
