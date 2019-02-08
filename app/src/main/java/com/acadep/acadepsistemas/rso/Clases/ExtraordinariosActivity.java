package com.acadep.acadepsistemas.rso.Clases;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.acadepsistemas.seguimiento.Manifest;
import com.acadep.acadepsistemas.rso.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.File;


import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class ExtraordinariosActivity extends AppCompatActivity {


    private static final String CARPETA_PRINCIPAL = "DCIM/";
    private static final String CARPETA_IMAGEN = "Camera";
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;

    static File fileimagen;
    static File fileimagen2;
    static File fileimagen3;
    static File fileimagen4;
    static File fileimagen5;


    static Bitmap capturedCoolerBitmap;
    //Variables xxxxxx

    static String idevent;
    static String name;

    static int descision;

    //Variables xxxxxx

    static TextView mensaje1, mensaje2;

    static double Lat;
    static double Lng;


    // GPS LOCATION

    TextView txname, txtidevent;

    FloatingTextButton btnEnviar;
    private FloatingTextButton btnFoto;
    private FloatingTextButton btnFoto2;
    private FloatingTextButton btnFoto3;
    private FloatingTextButton btnFoto4;
    private FloatingTextButton btnFoto5;

    //Variables Fotos

    private static final int REQUEST_PERM_WRITE_STORAGE = 102;
    private static final int CAPTURE_PHOTO = 104;


    private Uri filePath;
    private static ImageView imageView;
    private static ImageView imageView2;
    private static ImageView imageView3;
    private static ImageView imageView4;
    private static ImageView imageView5;

    private static ImageView noImage;

    FloatingTextButton btnBorrar;
    FloatingTextButton btnBorrar2;
    FloatingTextButton btnBorrar3;
    FloatingTextButton btnBorrar4;
    FloatingTextButton btnBorrar5;

    //Subir archivo

    FloatingTextButton btnArchivo;
    Uri pdfUri;

    ProgressDialog progressDialog;
    EditText edObserv;

    //FIREBASE

    FirebaseAuth mAuth;
    FirebaseStorage storage;

    DatabaseReference mDatabase;
    DatabaseReference ref;

    FirebaseDatabase dbRef;

    StorageReference storageReference;

    //FIRESTORE
    private FirebaseFirestore mFireStore;
    private Query mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extraordinarios);

        //init();


        //recibirDatos();
        //Inicializacion de varibales


        edObserv = (EditText) findViewById(R.id.edObserv);
        btnEnviar = (FloatingTextButton) findViewById(R.id.btnEnviar);

        btnArchivo = (FloatingTextButton) findViewById(R.id.btnArchivo);

        btnFoto = (FloatingTextButton) findViewById(R.id.btnFoto);



        imageView = (ImageView) findViewById(R.id.imgView);
        imageView2 = (ImageView) findViewById(R.id.imgView2);
        imageView3 = (ImageView) findViewById(R.id.imgView3);
        imageView4 = (ImageView) findViewById(R.id.imgView4);
        imageView5 = (ImageView) findViewById(R.id.imgView5);

        noImage = imageView5;

        // GPSSSSSSSSSSSSSSSSSSSSS

        mensaje1 = (TextView) findViewById(R.id.txtLat);
        mensaje2 = (TextView) findViewById(R.id.txtLng);

        mensaje1.setVisibility(View.INVISIBLE);
        mensaje2.setVisibility(View.INVISIBLE);

        // GPSSSSSSSSSSSSSSSSSSSSS

        //Firebase Inicializacion
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dbRef = FirebaseDatabase.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Eventos").child(idevent).child("observation");
        //Instancias
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        //Fin Instancias
        storageReference = storage.getReference();
        //Fin Firebase Inicializacion

        // FireStore
        mFireStore = FirebaseFirestore.getInstance();

        // FireStore


        //Botones

    }



}

