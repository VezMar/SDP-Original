package com.acadep.acadepsistemas.rso.Clases;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import com.acadep.acadepsistemas.rso.Fragmentos.EventosFragment;
import com.acadep.acadepsistemas.rso.Notificaciones.MiFirebaseMessagingService;
import com.acadep.acadepsistemas.rso.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import com.onesignal.OneSignal;

import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment currentFragment;

    FirebaseAuth mAuth;
    FirebaseFirestore BDFireStore = FirebaseFirestore.getInstance();

    public String rolesUser;
    DatabaseReference mDatabase;

    TextView txtBienvenida;


    int cont=0;

    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("MainActivity", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast

                        //Log.d("MainActivity", token);
                        Map<String, Object> Token = new HashMap<>();
                        Token.put("token", token);
                        BDFireStore.collection("users").document(mAuth.getUid()).set(Token, SetOptions.merge());

                        //Toast.makeText(Main2Activity.this, ""+token, Toast.LENGTH_SHORT).show();
                    }
                });

        registerReceiver(broadcastReceiver, new IntentFilter(MiFirebaseMessagingService.TOKEN_BROADCAST));
//        Log.i("Token-Key", "Este es el token " + SharedPrefManager.getInstance(this).getToken());
//        Log.i("Token-Key", "Este es el token " + );

        Fragment mifragment = null;
        mifragment = new EventosFragment();
        if(cont<1){
            ocultarItems();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Contenedor, mifragment)
                    .commit();
            cont++;
        }

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
        boolean status = true;

        Fragment mifragment = null;
        Boolean FragmentoSeleccionado=false;

        if (id == R.id.nav_perfil) {
            Toast.makeText(getApplicationContext(),"Aún en proceso",Toast.LENGTH_SHORT).show();
            status = false;
        }else if (id == R.id.nav_acty) {
            Toast.makeText(getApplicationContext(),"Aún en proceso",Toast.LENGTH_SHORT).show();
            status = false;
//                        startActivity(new Intent(this, com.acadep.acadepsistemas.rso.Clases.MaterialsCheckList.class));
//                        startActivity(new Intent(this, com.acadep.acadepsistemas.rso.Clases.RecyclerTest.class));
        } else if (id == R.id.nav_event) {
            mifragment = new EventosFragment();
            FragmentoSeleccionado=true;


           /*Intent intent= new Intent (Main2Activity.this, EventosActivity.class);
            startActivity(intent);]*/
           //finish();
            status = true;
        }else if (id == R.id.nav_ext) {
            Toast.makeText(getApplicationContext(),"Aún en proceso",Toast.LENGTH_SHORT).show();
            item.setChecked(false);
            status = false;
//            mifragment = new ActivitysFragment();
//            FragmentoSeleccionado=true;

        }else if (id == R.id.nav_conf) {
            Toast.makeText(getApplicationContext(),"Aún en proceso",Toast.LENGTH_SHORT).show();
            item.setChecked(false);
            status = false;
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
        return status;
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
