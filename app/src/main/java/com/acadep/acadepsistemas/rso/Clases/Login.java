package com.acadep.acadepsistemas.rso.Clases;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.acadep.acadepsistemas.rso.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    TextView txtPsd, txtUser, txtForgotPsw;
    Button btnLogin,btnRegistrar,btnMostrar;
    ProgressBar prgsBar;

    public String rolesUser;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    FirebaseAuth.AuthStateListener listener;

    public String uidUserGlobal;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        prgsBar = (ProgressBar) findViewById(R.id.prgsbar);
        prgsBar.setVisibility(View.INVISIBLE);
        txtPsd = (TextView) findViewById(R.id.txtPsd);
        txtUser = (TextView) findViewById(R.id.txtUser);

        txtForgotPsw = (TextView) findViewById(R.id.txtForgotPsw);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        //btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        //btnMostrar = (Button) findViewById(R.id.btnMostrar);
        //btnMostrar.setVisibility(View.INVISIBLE);

        mDatabase = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_Usuario));

       /* mDatabase.addValueEventListener(new ValueEventListener() {
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
/*
        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              / Intent intent= new Intent (Login.this, EventosActivity.class);
                startActivity(intent);
                String ad = "admin";
                Query q = mDatabase.orderByChild(getString(R.string.campo_rol)).equalTo(ad);

               q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int cont=0;
                        for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                            cont++;

                        }
                        Toast.makeText(Login.this, "Hubo " + cont, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });*/


        mAuth = FirebaseAuth.getInstance();
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
               // uidUserGlobal = user.getUid();
                if(user == null){
                    //NO ESTA LOGEADO
                    Toast.makeText(getApplicationContext(),"Sesion cerrada",Toast.LENGTH_SHORT).show();
                }else{
                    //ESTA LOGUEADO
                    Toast.makeText(getApplicationContext(),"Sesion iniciada",Toast.LENGTH_SHORT).show();

                    map();

                    //rolesUser = getIntent().getExtras().get("rol").toString();

                    //Log.d("ROLUSER", "ESTADO: " + rolesUser);
                }

            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // mDatabase.orderByChild(getString(R.string.c))
                ingresar();


            }
        });




        txtForgotPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), RecuperacionContrasena.class);
                startActivity(i);
            }
        });

       /* btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FirebaseUser user = mAuth.getCurrentUser();
                String uid1;*/
                //Query q = mDatabase.orderByChild(getString(R.string.campo_rol)).equalTo("admin");



              /* q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int cont=0;
                        for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                            cont++;

                        }
                        Toast.makeText(Login.this, "Hubo " + cont, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });*/

       /*btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), Registrar.class);
                startActivity(i);
                finish();
            }
        });*/




    }

    private void ingresar() {
        String User = txtUser.getText().toString();
        String password = txtPsd.getText().toString();

        if(!User.isEmpty() && !password.isEmpty()){
            prgsBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(User,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "CORRECTO", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "INCORRECTO", Toast.LENGTH_SHORT).show();
                    }
                    prgsBar.setVisibility(View.INVISIBLE);
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "Ingrese el usuario y contraseña", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(listener != null){
            mAuth.removeAuthStateListener(listener);
        }
    }

    private void map(){
        Intent i= new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
    private void cerrarSesion(){
        mAuth.signOut();
        Intent i= new Intent(this, Login.class);
        startActivity(i);
        finish();
    }



}