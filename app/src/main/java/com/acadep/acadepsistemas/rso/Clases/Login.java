package com.acadep.acadepsistemas.rso.Clases;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.acadep.acadepsistemas.rso.Manifest;
import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Configuration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.admin.v1beta1.Progress;
import com.muddzdev.styleabletoast.StyleableToast;

public class Login extends AppCompatActivity {

    TextView txtPsd, txtUser, txtForgotPsw;
    Button btnLogin,btnRegistrar,btnMostrar;
    ProgressBar prgsBar;

    public String rolesUser;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    FirebaseAuth.AuthStateListener listener;

    FirebaseFirestore BDFireStore = FirebaseFirestore.getInstance();

    public String uidUserGlobal;

    ProgressBar prog;

    static int versionCode, lastestVersionCode;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();


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

        PackageInfo pInfo = null;
        try {
            pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.i("Code version ", ""+ pInfo.versionCode);

        versionCode = pInfo.versionCode;

        prog = findViewById(R.id.prgsbar);
        prog.setVisibility(View.INVISIBLE);


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

                    showLoading();
                    BDFireStore
                            .collection("configuration")
                            .document("global")
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Configuration configuration = documentSnapshot.toObject(Configuration.class);
                            lastestVersionCode = configuration.getVersionCode();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            if(versionCode >= lastestVersionCode) {
                                hideLoading();
                                Toast.makeText(getApplicationContext(), "Sesion iniciada", Toast.LENGTH_SHORT).show();
                                map();
                            }else{
                                hideLoading();
//                                StyleableToast.makeText(Login.this, "Descargue la nueva versión para poder continuar" , Toast.LENGTH_LONG, R.style.warningToastMiddle).show();
//                                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                                builder.setTitle("¡Nueva versión disponible!");
//                                builder.setMessage("Descargue la nueva versión para poder continuar");
//                                builder.setPositiveButton("Ir a la PlayStore", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.acadep.acadepsistemas.rso"));
//                                        startActivity(intent);
//                                    }
//                                });
//                                builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//                                    }
//                                });
//                                builder.create().show();

                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                builder.setTitle("¡Nueva versión disponible!");
                                builder.setMessage("Descargue la nueva versión para poder continuar");
                                builder.setPositiveButton("Ir a la PlayStore", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.acadep.acadepsistemas.rso"));
                                        startActivity(intent);
                                    }
                                });
                                builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
//                                builder.setIcon(R.drawable.ic_warning_black_24dp);
                                builder.create().show();

                            }

                        }
                    });
                }


                    //rolesUser = getIntent().getExtras().get("rol").toString();

                    //Log.d("ROLUSER", "ESTADO: " + rolesUser);
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
        String User = txtUser.getText().toString().replace(" ", "");
        String password = txtPsd.getText().toString();

        if(versionCode>=lastestVersionCode){
            if (!User.isEmpty() && !password.isEmpty()) {
                prgsBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(User, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
            } else {
                Toast.makeText(getApplicationContext(), "Ingrese el usuario y contraseña", Toast.LENGTH_SHORT).show();
            }
        }else{
//            StyleableToast.makeText(Login.this, "Descargue la nueva versión para poder continuar" , Toast.LENGTH_LONG, R.style.warningToastMiddle).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
            builder.setTitle("¡Nueva versión disponible!");
            builder.setMessage("Descargue la nueva versión para poder continuar");
            builder.setPositiveButton("Ir a la PlayStore", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.acadep.acadepsistemas.rso"));
                    startActivity(intent);
                }
            });
            builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
//                                builder.setIcon(R.drawable.ic_warning_black_24dp);
            builder.create().show();
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

    public void showLoading(){
            Toast.makeText(this, "Comprobando Actualizaciones", Toast.LENGTH_LONG).show();
        prog.setVisibility(View.VISIBLE);
    }

    public void hideLoading(){
        prog.setVisibility(View.GONE);
    }

    private void map(){
        Intent i= new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }




}