package com.acadep.acadepsistemas.rso.Clases.Prueba.fragmentosEvidence;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.acadep.acadepsistemas.rso.Clases.Prueba.EvidenceActivity;
import com.acadep.acadepsistemas.rso.R;

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
    static int number = evidenceActivity.getNumber();
    static String unit = evidenceActivity.getUnit();
    static boolean during_complete;
    static boolean before_complete;
    static String deleted;

    // Datos del evento
//---------------------------------------------------------------

    //Declaración de componentes
        EditText edObserv;
        EditText edpercentage;
        TextView txtAvance;
        TextView txtTotal;
    //Declaración de componentes

    public ObservacionesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            number = getArguments().getInt("number");
            unit = getArguments().getString("unit");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_observaciones, container, false);

        edObserv = (EditText) view.findViewById(R.id.edObserv);
        edpercentage = (EditText) view.findViewById(R.id.edit_porcentage);
        txtAvance = (TextView) view.findViewById(R.id.txtAvance);
        txtTotal = (TextView) view.findViewById(R.id.txtTotal);

        edObserv.setText("" + evidenceActivity.getEdObserv());
        edpercentage.setText("" + evidenceActivity.getAva());

        txtTotal.setText("/" + number + " "+ unit);

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
                    evidenceActivity.setAva(Integer.parseInt(text));
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
