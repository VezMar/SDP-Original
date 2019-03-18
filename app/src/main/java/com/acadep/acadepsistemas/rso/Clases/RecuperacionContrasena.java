package com.acadep.acadepsistemas.rso.Clases;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.acadep.acadepsistemas.rso.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.muddzdev.styleabletoast.StyleableToast;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class RecuperacionContrasena extends AppCompatActivity {

    private Toolbar mToolbar;
    private FloatingTextButton ResetPasswordSendEmailButton;
    private EditText ResetEmailInput;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacion_contrasena);

        mAuth = FirebaseAuth.getInstance();

        //mToolbar = (Toolbar) findViewById(R.id.)

        ResetEmailInput = (EditText) findViewById(R.id.EditForgotEmail);
        ResetPasswordSendEmailButton = (FloatingTextButton) findViewById(R.id.btnSenEmail);


        ResetPasswordSendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = ResetEmailInput.getText().toString().replace(" ", "");;

                if(TextUtils.isEmpty(userEmail)){
                    StyleableToast.makeText(getApplicationContext(),"Porfavor ingrese un correo electronico", Toast.LENGTH_SHORT, R.style.warningToast).show();
                }else{
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                StyleableToast.makeText(getApplicationContext(),"Porfavor revise su correo electrónico", Toast.LENGTH_SHORT, R.style.doneToast).show();
                                startActivity(new Intent(RecuperacionContrasena.this, Login.class));
                            }else{
                                String message = task.getException().getMessage();
                                StyleableToast.makeText(getApplicationContext(),"Un Error ha ocurrido" + message, Toast.LENGTH_SHORT, R.style.dangerToast).show();
                            }
                        }


                    });
                }
            }
        });
    }
}
