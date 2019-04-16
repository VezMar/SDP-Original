package com.acadep.acadepsistemas.rso.Clases;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.acadep.acadepsistemas.rso.Clases.fragmentosEvidence.ArchivosFragment;
import com.acadep.acadepsistemas.rso.Clases.fragmentosEvidence.MultimediaFragment;
import com.acadep.acadepsistemas.rso.Clases.fragmentosEvidence.ObservacionesFragment;
import com.acadep.acadepsistemas.rso.R;

public class ExtraEvidenceActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment mifragment = null;
            Boolean FragmentoSeleccionado=false;
            int id = item.getItemId();

            if (id == R.id.navigation_observaciones){
                mifragment = new ObservacionesFragment();
                FragmentoSeleccionado=true;
            }else if (id == R.id.navigation_multmedia) {
                mifragment = new MultimediaFragment();
                FragmentoSeleccionado = true;
            }else if (id ==  R.id.navigation_archivos) {
                mifragment = new ArchivosFragment();
                FragmentoSeleccionado = true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_evidence);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

}
