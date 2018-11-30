package com.example.acadepsistemas.seguimiento.Clases;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acadepsistemas.seguimiento.Fragmentos.EventosFragment;
//import com.example.acadepsistemas.seguimiento.Manifest;
import com.example.acadepsistemas.seguimiento.R;
import com.example.acadepsistemas.seguimiento.model.Data;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;


import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;

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
        btnFoto2 = (FloatingTextButton) findViewById(R.id.btnFoto2);
        btnFoto3 = (FloatingTextButton) findViewById(R.id.btnFoto3);
        btnFoto4 = (FloatingTextButton) findViewById(R.id.btnFoto4);
        btnFoto5 = (FloatingTextButton) findViewById(R.id.btnFoto5);

        btnBorrar = (FloatingTextButton) findViewById(R.id.btnBorrar);
        btnBorrar2 = (FloatingTextButton) findViewById(R.id.btnBorrar2);
        btnBorrar3 = (FloatingTextButton) findViewById(R.id.btnBorrar3);
        btnBorrar4 = (FloatingTextButton) findViewById(R.id.btnBorrar4);
        btnBorrar5 = (FloatingTextButton) findViewById(R.id.btnBorrar5);


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

