package com.example.acadepsistemas.seguimiento.Clases;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acadepsistemas.seguimiento.Fragmentos.EventosFragment;
import com.example.acadepsistemas.seguimiento.Fragmentos.SupervisionFragment;
import com.example.acadepsistemas.seguimiento.R;
import com.example.acadepsistemas.seguimiento.model.Evento;
import com.example.acadepsistemas.seguimiento.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment currentFragment;

    FirebaseAuth mAuth;
    public String rolesUser;
    DatabaseReference mDatabase;

    TextView txtBienvenida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();



     /*mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseUser user = mAuth.getCurrentUser();
                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){

                    Usuario usuario = datasnapshot.getValue(Usuario.class);

                     String activo = usuario.getActivo();
                     String apellido = usuario.getApellido();
                     String correo = usuario.getCorreo();
                     String nombre = usuario.getNombre();
                     String rol = usuario.getRol();
                     String uid = usuario.getUid();

                    if (user.getUid() == uid){
                        rolesUser = rol;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        //rolDeUsuario();


       // mDatabase.orderByChild(getString(R.string.campo_uid)).equalTo("admin");
       /* if(rolesUser == "admin") {
            Toast.makeText(getApplicationContext(), "Bienvenido usuario", Toast.LENGTH_SHORT).show();
        }

        if(rolesUser =="trabajador") {
            Toast.makeText(getApplicationContext(), "Aplicacion solo para trabajadores", Toast.LENGTH_SHORT).show();
            //cerrarSesion();
        }*/












        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment mifragment = null;
        Boolean FragmentoSeleccionado=false;

        if (id == R.id.nav_acty) {
            /*mifragment = new SupervisionFragment();
            FragmentoSeleccionado=true;*/
            Toast.makeText(getApplicationContext(),"Aún en proceso",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_event) {
            mifragment = new EventosFragment();
            FragmentoSeleccionado=true;

           /*Intent intent= new Intent (Main2Activity.this, EventosActivity.class);
            startActivity(intent);]*/
           //finish();

        } else if (id == R.id.nav_ext) {
            Toast.makeText(getApplicationContext(),"Aún en proceso",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_signOut) {
            cerrarSesion();
        }


        if(FragmentoSeleccionado==true){
            ocultarItems();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Contenedor, mifragment)
                    .commit();

            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void map(){

    }

    private void cerrarSesion(){

        mAuth.signOut();
        Intent i= new Intent(this, Login.class);
        startActivity(i);
        finish();
    }



    private void ocultarItems(){

        txtBienvenida = (TextView) findViewById(R.id.txtBienvenida);
        txtBienvenida.setVisibility(View.INVISIBLE);
    }
}
