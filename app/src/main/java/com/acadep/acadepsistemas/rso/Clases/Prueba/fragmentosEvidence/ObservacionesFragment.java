package com.acadep.acadepsistemas.rso.Clases.Prueba.fragmentosEvidence;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.acadep.acadepsistemas.rso.Clases.Prueba.EvidenceActivity;
import com.acadep.acadepsistemas.rso.R;
import com.karan.churi.PermissionManager.PermissionManager;

import java.util.ArrayList;


public class ObservacionesFragment extends Fragment {

    //---------------------------------------------------------------
    // Datos del evento
    static EvidenceActivity evidenceActivity = new EvidenceActivity();
    
    static String idevent;
    static String nameEvent;
    static String user_id;
    static String actividad;
    static String trabajador;
    static String name;
    static String start;
    static String end;
    static String idactivity;
    static String description;
    static String title;
    static ArrayList<String> tools;
    static int ava;
     int number = evidenceActivity.getNumber();
     String unit = evidenceActivity.getUnit();
    static boolean during_complete;
    static boolean before_complete;
    static String deleted;

    static boolean Tbefore;
    static boolean Tduring;
    static boolean Tafter;

    static  String estado = "before";
    // Datos del evento
//---------------------------------------------------------------

    //Declaración de componentes
        EditText edObserv;
        EditText edpercentage;
        TextView txtAvance;
        TextView txtTotal;
        TextView txtEstado;
        TextView txtAyuda;
    //Declaración de componentes

    PermissionManager permissionManager;


    public ObservacionesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            number = getArguments().getInt("number");
            unit = getArguments().getString("unit");

            during_complete = getArguments().getBoolean("during_complete");
            before_complete = getArguments().getBoolean("before_complete");

            Tbefore = getArguments().getBoolean("Tbefore");
            Tduring = getArguments().getBoolean("Tduring");
            Tafter = getArguments().getBoolean("Tafter");

            estado = getArguments().getString("estado");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_observaciones, container, false);

        permissionManager = new PermissionManager() {};
        permissionManager.checkAndRequestPermissions(getActivity());

        edObserv = (EditText) view.findViewById(R.id.edObserv);
        edpercentage = (EditText) view.findViewById(R.id.edit_porcentage);
        txtAvance = (TextView) view.findViewById(R.id.txtAvance);
        txtTotal = (TextView) view.findViewById(R.id.txtTotal);

        txtEstado = (TextView) view.findViewById(R.id.txtEstado);
        txtAyuda = view.findViewById(R.id.txtAyuda);

        edObserv.setText("" + evidenceActivity.getEdObserv());
        edpercentage.setText("" + evidenceActivity.getAvanced());

        txtTotal.setText("/" + number + " "+ unit);

        ChequeoDeVariables();

        edpercentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = ""+edpercentage.getText();
                if(!text.equals("")) {
                    evidenceActivity.setAvanced(Integer.parseInt(text));
//                    evidenceActivity.setEdpercentage(text);
                }
            }
        });

        edObserv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                evidenceActivity.setEdObserv(""+ edObserv.getText());
            }
        });

        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.checkResult(requestCode, permissions,grantResults);

        ArrayList<String> granted = permissionManager.getStatus().get(0).granted;
        ArrayList<String> denied = permissionManager.getStatus().get(0).denied;

        for (String item:granted){
            Toast.makeText(getContext(), "Permisos otrogados", Toast.LENGTH_SHORT).show();
        }

        for (String item:denied){
            Toast.makeText(getContext(), "Faltan permisos por otorgar", Toast.LENGTH_SHORT).show();
        }

    }

    private void ChequeoDeVariables() {
        if(Tbefore==true && before_complete == false){
            txtAvance.setVisibility(View.INVISIBLE);
            edpercentage.setVisibility(View.INVISIBLE);
            txtTotal.setVisibility(View.INVISIBLE);
            txtAyuda.setVisibility(View.INVISIBLE);

            ava = 0;
            txtEstado.setText("Inicio de la tarea");
            estado = "before";
            EvidenceActivity.setEstado("before");
        }else {
            if (Tduring == true && during_complete==false){
                txtAvance.setVisibility(View.VISIBLE);
                edpercentage.setVisibility(View.VISIBLE);
                txtTotal.setVisibility(View.VISIBLE);
                txtAyuda.setVisibility(View.VISIBLE);

                txtEstado.setText("Durante la tarea");
                estado = "during";
                EvidenceActivity.setEstado("during");
            }else {
                if (Tafter == true && during_complete == true) {
                    txtAvance.setVisibility(View.INVISIBLE);
                    edpercentage.setVisibility(View.INVISIBLE);
                    txtTotal.setVisibility(View.INVISIBLE);
                    txtAyuda.setVisibility(View.INVISIBLE);

                    txtEstado.setText("Después de la tarea");
                    estado = "after";
                    EvidenceActivity.setEstado("after");
                }
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
