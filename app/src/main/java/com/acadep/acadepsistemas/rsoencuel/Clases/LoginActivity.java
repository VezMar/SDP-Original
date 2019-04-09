package com.acadep.acadepsistemas.rsoencuel.Clases;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.acadep.acadepsistemas.rsoencuel.R;
import com.acadep.acadepsistemas.rsoencuel.model.Configuration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.muddzdev.styleabletoast.StyleableToast;

public class LoginActivity extends AppCompatActivity {


    TextView txtPsd, txtUser, txtForgotPsw;
    Button btnLogin;
    ProgressBar prgsBar;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    FirebaseAuth.AuthStateListener listener;

    FirebaseFirestore BDFireStore = FirebaseFirestore.getInstance();

    ProgressBar prog;

    static int versionCode, lastestVersionCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prgsBar = (ProgressBar) findViewById(R.id.prgsbar);
        prgsBar.setVisibility(View.INVISIBLE);
        txtPsd = (TextView) findViewById(R.id.txtPsd);
        txtUser = (TextView) findViewById(R.id.txtUser);

        txtForgotPsw = (TextView) findViewById(R.id.txtForgotPsw);

        btnLogin = (Button) findViewById(R.id.btnLogin);

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
                                StyleableToast.makeText(getApplicationContext(), "Descargue la nueva versión para poder continuar" , Toast.LENGTH_LONG, R.style.warningToastMiddle).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
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
            StyleableToast.makeText(this, "Descargue la nueva versión para poder continuar" , Toast.LENGTH_LONG, R.style.warningToastMiddle).show();
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
