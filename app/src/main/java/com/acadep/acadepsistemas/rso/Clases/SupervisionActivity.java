package com.acadep.acadepsistemas.rso.Clases;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.format.Time;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.acadep.acadepsistemas.rso.Fragmentos.EventosFragment;
//import com.example.acadepsistemas.seguimiento.Manifest;
import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Data;
import com.acadep.acadepsistemas.rso.model.Data2;
import com.acadep.acadepsistemas.rso.model.Data3;
import com.acadep.acadepsistemas.rso.model.Evento;
import com.acadep.acadepsistemas.rso.model.Foto;
import com.acadep.acadepsistemas.rso.model.Ubication;
import com.acadep.acadepsistemas.rso.model.datetime;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;


import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.muddzdev.styleabletoast.StyleableToast;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class SupervisionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String CARPETA_PRINCIPAL = "DCIM/";
    private static final String CARPETA_IMAGEN = "Camera";
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;

    static File fileimagen = null;
    static File fileimagen2 = null;
    static File fileimagen3 = null;
    static File fileimagen4 = null;
    static File fileimagen5 = null;
    static File fileimagen6 = null;


    static Bitmap capturedCoolerBitmap;


    // GPS LOCATION
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;


    private String mLastUpdateTime;

    TextView txtLocationResult;

    private Boolean mRequestingLocationUpdates;


    static TextView mensaje1, mensaje2;

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    //TextView txtUpdatedOn;


    static double Lat;
    static double Lng;
    // GPS LOCATION

    private static final int REQUEST_PERM_WRITE_STORAGE = 102;
    private static final int CAPTURE_PHOTO = 104;

    //Varibales X
    static int cont1=0;
    static int cont2=0;
    static int cont3=0;

    FloatingTextButton btnEnviar;
    private FloatingTextButton btnFoto;
    private FloatingTextButton btnFoto2;
    private FloatingTextButton btnFoto3;
    private FloatingTextButton btnFoto4;
    private FloatingTextButton btnFoto5;



    ListView listView;


    static int conteo = 0;
    static int descision;

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
    static  List<String> tools;

    static boolean deleted;
    static boolean active;

    EditText edObserv;
    static TextView txtEstado;

    boolean statusChange;


    public String rolesUser;
    static String estado = "before";


    //Variables Fotos
    //private Uri filePath;
    private static ImageView imageView;
    private static ImageView imageView2;
    private static ImageView imageView3;
    private static ImageView imageView4;
    private static ImageView imageView5;
    private static ImageView imageView6;

    private static ImageView noImage;

    FloatingTextButton btnBorrar;
    FloatingTextButton btnBorrar2;
    FloatingTextButton btnBorrar3;
    FloatingTextButton btnBorrar4;
    FloatingTextButton btnBorrar5;

    FloatingTextButton btnBorrarArchivo;




    static List<String> Foto =  new ArrayList<>();
    static List<String> Foto2 =  new ArrayList<>();
    static List<String> Foto3 =  new ArrayList<>();
    static List<String> Foto4 =  new ArrayList<>();
    static List<String> Foto5 =  new ArrayList<>();



//Subir archivo

    FloatingTextButton btnArchivo;
    static Uri ArchivoUri;
    static Uri ArchivoUri2;
    static Uri ArchivoUri3;
    static Uri ArchivoUri4;
    static Uri ArchivoUri5;
    static Uri ArchivoUri6;
    static Uri ArchivoUri7;
    static Uri ArchivoUri8;
    static Uri ArchivoUri9;
    static Uri ArchivoUri10;

    static int contUris=0;
    static int restUris=9;

    ProgressDialog progressDialog;

    static ImageView[] valores = new ImageView[]{imageView, imageView2, imageView3, imageView4, imageView5};
    //FIREBASE

    FirebaseAuth mAuth;
    FirebaseStorage storage;

    DatabaseReference mDatabase;
    DatabaseReference ref;
    DatabaseReference query;


    DatabaseReference refBefore;
    DatabaseReference refDuring;
    DatabaseReference refAfter;

    FirebaseFirestore BDFireStore= FirebaseFirestore.getInstance();
    CollectionReference fireStoreReference;


    FirebaseDatabase dbRef;

    StorageReference storageReference;

    //FIRESTORE
    private FirebaseFirestore mFireStore;
    private Query mQuery;


    private BottomNavigationView bottomNavigationView;


    private static List<Foto> evidence = new ArrayList<>();
    private static com.acadep.acadepsistemas.rso.model.Foto PerFoto1 = new Foto();
    private static com.acadep.acadepsistemas.rso.model.Foto PerFoto2 = new Foto();
    private static com.acadep.acadepsistemas.rso.model.Foto PerFoto3 = new Foto();
    private static com.acadep.acadepsistemas.rso.model.Foto PerFoto4 = new Foto();
    private static com.acadep.acadepsistemas.rso.model.Foto PerFoto5 = new Foto();
    private static com.acadep.acadepsistemas.rso.model.Foto PerFoto6 = new Foto();

    private static datetime datatime = new datetime();
    private static Ubication ubication = new Ubication();

    private static Time today = new Time(Time.getCurrentTimezone());
    private static String Fecha;
    private static String Hora;

    private static String src1;
    static String src2;
    static String src3;
    static String src4;
    static String src5;
    static String src6;

    int status=1;

    CheckBox checkBox1 ,checkBox2,checkBox3,checkBox4, checkBox5,checkBox6;
    LinearLayout Lfotos1, Lfotos2;
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervision);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        today.setToNow();

        //init();


        cont1=0;
        cont2=0;
        cont3=0;





        recibirDatos();

        final Evento nEvent = new Evento();

        nEvent.setActive(false);
        nEvent.setType_activity(actividad);
        nEvent.setName(nameEvent);
        //nEvent.setEnd(end);
        nEvent.setIdactivity(idactivity);
        nEvent.setId(idevent);
        //nEvent.setStart(start);
        nEvent.setUser_id(user_id);
        nEvent.setTrabajador(trabajador);
        nEvent.setDeleted(deleted);
       // nEvent.setTools(tools);

        //Inicializacion de varibales
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        txtEstado = (TextView) findViewById(R.id.txtEstado);

        txtEstado.setText("Antes del Evento");
        estado = "before";
/*
        if(nEvent.getType_activity().equals("auditoria")){
            estado = "before";
            bottomNavigationView.setVisibility(View.INVISIBLE);
        }*/

        edObserv = (EditText) findViewById(R.id.edObserv);
        btnEnviar = (FloatingTextButton) findViewById(R.id.btnEnviar);

        btnArchivo = (FloatingTextButton) findViewById(R.id.btnArchivo);

        btnFoto = (FloatingTextButton) findViewById(R.id.btnFoto);
//        btnFoto2 = (FloatingTextButton) findViewById(R.id.btnFoto2);
//        btnFoto3 = (FloatingTextButton) findViewById(R.id.btnFoto3);
//        btnFoto4 = (FloatingTextButton) findViewById(R.id.btnFoto4);
//        btnFoto5 = (FloatingTextButton) findViewById(R.id.btnFoto5);
//
        btnBorrar = (FloatingTextButton) findViewById(R.id.btnBorrar);
//        btnBorrar2 = (FloatingTextButton) findViewById(R.id.btnBorrar2);
//        btnBorrar3 = (FloatingTextButton) findViewById(R.id.btnBorrar3);
//        btnBorrar4 = (FloatingTextButton) findViewById(R.id.btnBorrar4);
//        btnBorrar5 = (FloatingTextButton) findViewById(R.id.btnBorrar5);


        checkBox1 = (CheckBox) findViewById(R.id.Check1);
        checkBox2 = (CheckBox) findViewById(R.id.Check2);
        checkBox3 = (CheckBox) findViewById(R.id.Check3);
        checkBox4 = (CheckBox) findViewById(R.id.Check4);
        checkBox5 = (CheckBox) findViewById(R.id.Check5);
        checkBox6 = (CheckBox) findViewById(R.id.Check6);



        btnBorrarArchivo = (FloatingTextButton) findViewById(R.id.btnBorrarArchivo);


        imageView = (ImageView) findViewById(R.id.imgView);
        imageView2 = (ImageView) findViewById(R.id.imgView2);
        imageView3 = (ImageView) findViewById(R.id.imgView3);
        imageView4 = (ImageView) findViewById(R.id.imgView4);
        imageView5 = (ImageView) findViewById(R.id.imgView5);
        imageView6 = (ImageView) findViewById(R.id.imgView6);




        checkBox1.setVisibility(View.INVISIBLE);
        checkBox2.setVisibility(View.INVISIBLE);
        checkBox3.setVisibility(View.INVISIBLE);
        checkBox4.setVisibility(View.INVISIBLE);
        checkBox5.setVisibility(View.INVISIBLE);
        checkBox6.setVisibility(View.INVISIBLE);


        imageView.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
        imageView3.setVisibility(View.INVISIBLE);
        imageView4.setVisibility(View.INVISIBLE);
        imageView5.setVisibility(View.INVISIBLE);
        imageView6.setVisibility(View.INVISIBLE);

        Lfotos1 = (LinearLayout) findViewById(R.id.Lfotos1);
        Lfotos2 = (LinearLayout) findViewById(R.id.Lfotos2);




        noImage = imageView5;

        // GPSSSSSSSSSSSSSSSSSSSSS

        mensaje1  = (TextView) findViewById(R.id.txtLat);
        mensaje2  = (TextView) findViewById(R.id.txtLng);

        mensaje1.setVisibility(View.INVISIBLE);
        mensaje2.setVisibility(View.INVISIBLE);

        // GPSSSSSSSSSSSSSSSSSSSSS

        //Firebase Inicializacion
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dbRef = FirebaseDatabase.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Eventos").child(idevent).child("observation");

        refBefore = FirebaseDatabase.getInstance().getReference().child("Eventos").child(idevent).child("observation").child("before");
        refDuring = FirebaseDatabase.getInstance().getReference().child("Eventos").child(idevent).child("observation").child("during");
        refAfter = FirebaseDatabase.getInstance().getReference().child("Eventos").child(idevent).child("observation").child("after");



        //Instancias
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        //Fin Instancias
        storageReference = storage.getReference();
        //Fin Firebase Inicializacion

        // FireStore
        mFireStore = FirebaseFirestore.getInstance();

        // FireStore

       // query = FirebaseDatabase.getInstance().getReference().child("Eventos").child(idevent).child("observation").child();


        //Botones

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }


        chequeoDevariables();
        /**/




        btnArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (estado.equals("before") && cont1<1 || estado.equals("during") && cont2<1 || estado.equals("after") && cont3<1) {
                    if (ContextCompat.checkSelfPermission(SupervisionActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        mostrarDialogoOpciones();
                    } else {
                        ActivityCompat.requestPermissions(SupervisionActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                    }
                }else{
                    StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
                }
            }
        });

        btnBorrarArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MostrarOpciones();


            }
        });


        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkBox1.isChecked() || checkBox2.isChecked() || checkBox3.isChecked() || checkBox4.isChecked() || checkBox5.isChecked() || checkBox6.isChecked()) {


                    if (checkBox1.isChecked()) {

                        imageView.setImageResource(R.drawable.empty_image);
                        checkBox1.setChecked(false);
                        fileimagen = null;


                        imageView.setVisibility(View.INVISIBLE);
                        checkBox1.setVisibility(View.INVISIBLE);
                    }

                    if (checkBox2.isChecked()) {
                        imageView2.setImageResource(R.drawable.empty_image);
                        checkBox2.setChecked(false);
                        fileimagen2 = null;

                        imageView2.setVisibility(View.INVISIBLE);
                        checkBox2.setVisibility(View.INVISIBLE);
                    }

                    if (checkBox3.isChecked()) {
                        imageView3.setImageResource(R.drawable.empty_image);
                        checkBox3.setChecked(false);
                        fileimagen3 = null;

                        imageView3.setVisibility(View.INVISIBLE);
                        checkBox3.setVisibility(View.INVISIBLE);
                    }

                    if (checkBox4.isChecked()) {
                        imageView4.setImageResource(R.drawable.empty_image);
                        checkBox4.setChecked(false);
                        fileimagen4 = null;

                        imageView4.setVisibility(View.INVISIBLE);
                        checkBox4.setVisibility(View.INVISIBLE);
                    }

                    if (checkBox5.isChecked()) {
                        imageView5.setImageResource(R.drawable.empty_image);
                        checkBox5.setChecked(false);
                        fileimagen5 = null;

                        imageView5.setVisibility(View.INVISIBLE);
                        checkBox5.setVisibility(View.INVISIBLE);
                    }

                    if (checkBox6.isChecked()) {
                        imageView6.setImageResource(R.drawable.empty_image);
                        checkBox6.setChecked(false);
                        fileimagen6 = null;

                        imageView6.setVisibility(View.INVISIBLE);
                        checkBox6.setVisibility(View.INVISIBLE);
                    }
                }else{
                    StyleableToast.makeText(getApplicationContext(), "No ha seleccionado ninguna foto", Toast.LENGTH_SHORT, R.style.warningToast).show();
                }


            }
        });



        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(String.valueOf(Manifest.permission.CAMERA)) != PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(SupervisionActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                }

                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(SupervisionActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);
                } else {
                    locationStart();

                    if(fileimagen !=null && fileimagen2 !=null && fileimagen3 !=null && fileimagen4 !=null && fileimagen5 !=null && fileimagen6 !=null){
                        StyleableToast.makeText(getApplicationContext(), "Ya no puede añadir más fotos", Toast.LENGTH_SHORT, R.style.warningToast).show();
                    }else{
                        takePhoto();
                        locationStart();
                    }
                    locationStart();
                    Fecha =  today.monthDay + "/" + today.month + "/" + today.year;
                    Hora = today.hour +":" + today.minute;

                    datatime.setDate(Fecha);
                    datatime.setTime(Hora);

                    ubication.setLat(""+Lat);
                    ubication.setLng(""+Lng);

                    if(descision==0 && fileimagen!=null) {
                        PerFoto1.setDatatime(datatime);
                        PerFoto1.setUbicacion(ubication);
                        PerFoto1.setType("Imagen");

                    } else if (descision==1){
                        PerFoto2.setDatatime(datatime);
                        PerFoto2.setUbicacion(ubication);
                        PerFoto2.setType("Imagen");

                        imageView2.setVisibility(View.VISIBLE);
                        checkBox2.setVisibility(View.VISIBLE);
                    } else if (descision==2){
                        PerFoto3.setDatatime(datatime);
                        PerFoto3.setUbicacion(ubication);
                        PerFoto3.setType("Imagen");


                    } else if (descision==3){
                        PerFoto4.setDatatime(datatime);
                        PerFoto4.setUbicacion(ubication);
                        PerFoto4.setType("Imagen");


                    } else if (descision==4){
                        PerFoto5.setDatatime(datatime);
                        PerFoto5.setUbicacion(ubication);
                        PerFoto5.setType("Imagen");


                    }else if (descision==5){
                        PerFoto6.setDatatime(datatime);
                        PerFoto6.setUbicacion(ubication);
                        PerFoto6.setType("Imagen");

                    }
                }
            }
        });
























//        btnBorrar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (estado.equals("before") && cont1<1 || estado.equals("during") && cont2<1 || estado.equals("after") && cont3<1) {
//
//                    imageView.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
//                    fileimagen = null;
//                    Toast.makeText(getApplicationContext(), "Borrada con exito", Toast.LENGTH_SHORT).show();
//                }else{
//                    StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
//                }
//
//
//            }
//        });
//
//        btnBorrar2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (estado.equals("before") && cont1<1 || estado.equals("during") && cont2<1 || estado.equals("after") && cont3<1) {
//
//                    imageView2.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
//                    fileimagen2 = null;
//                    Toast.makeText(getApplicationContext(), "Borrada con exito", Toast.LENGTH_SHORT).show();
//                }else{
//                    StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
//                }
//
//            }
//        });
//
//        btnBorrar3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (estado.equals("before") && cont1<1 || estado.equals("during") && cont2<1 || estado.equals("after") && cont3<1) {
//                    imageView3.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
//                    fileimagen3 = null;
//                    StyleableToast.makeText(getApplicationContext(), "Borrada con exito", Toast.LENGTH_SHORT, R.style.doneToast).show();
//                }else{
//                    StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
//                }
//            }
//        });
//
//        btnBorrar4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (estado.equals("before") && cont1<1 || estado.equals("during") && cont2<1 || estado.equals("after") && cont3<1) {
//                    imageView4.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
//                    //imageView4.setImageURI(Uri.parse("@drawable/empty_image"));
//                    //imageView4.findViewById(R.id.imgView4);
//
//                    fileimagen4 = null;
//                    StyleableToast.makeText(getApplicationContext(), "Borrada con exito", Toast.LENGTH_SHORT, R.style.doneToast).show();
//                }else{
//                    StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
//                }
//
//
//            }
//        });
//
//        btnBorrar5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (estado.equals("before") && cont1<1 || estado.equals("during") && cont2<1 || estado.equals("after") && cont3<1) {
//                    imageView5.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
//                    fileimagen5 = null;
//                    StyleableToast.makeText(getApplicationContext(), "Borrada con exito", Toast.LENGTH_SHORT, R.style.doneToast).show();
//                }else{
//                    StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
//                }
//            }
//        });
//
//        btnFoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (checkSelfPermission(String.valueOf(Manifest.permission.CAMERA)) != PackageManager.PERMISSION_DENIED) {
//                        ActivityCompat.requestPermissions(SupervisionActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
//                    }
//                }
//
//                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//                    ActivityCompat.requestPermissions(SupervisionActivity.this,
//                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);
//                } else {
//                    locationStart();
//                    descision = 0;
//                    takePhoto();
//
//                    datetime =  today.monthDay + "/" + today.month + "/" + today.year + "T" + today.hour + ":" + today.minute;
//
//                    Foto.add(datetime);
//                    Foto.add(""+Lng);
//                    Foto.add(""+Lat);
//                }
//            }
//        });
//
//        btnFoto2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (checkSelfPermission(String.valueOf(Manifest.permission.CAMERA)) != PackageManager.PERMISSION_DENIED) {
//                        ActivityCompat.requestPermissions(SupervisionActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
//                    }
//                }
//
//                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//                    ActivityCompat.requestPermissions(SupervisionActivity.this,
//                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);
//                } else {
//                    descision = 1;
//                    takePhoto();
//
//                    locationStart();
//                    datetime =  today.monthDay + "/" + today.month + "/" + today.year + "T" + today.hour + ":" + today.minute;
//                    Foto2.add(""+datetime);
//                    Foto2.add(""+Lng);
//                    Foto2.add(""+Lat);
//                }
//            }
//        });
//
//        btnFoto3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (checkSelfPermission(String.valueOf(Manifest.permission.CAMERA)) != PackageManager.PERMISSION_DENIED) {
//                        ActivityCompat.requestPermissions(SupervisionActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
//                    }
//                }
//
//                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//                    ActivityCompat.requestPermissions(SupervisionActivity.this,
//                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);
//                } else {
//                    descision = 2;
//                    takePhoto();
//
//                    locationStart();
//                    datetime =  today.monthDay + "/" + today.month + "/" + today.year + "T" + today.hour + ":" + today.minute;
//                    Foto3.add(datetime);
//                    Foto3.add(""+Lng);
//                    Foto3.add(""+Lat);
//
//                }
//            }
//        });
//
//        btnFoto4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (checkSelfPermission(String.valueOf(Manifest.permission.CAMERA)) != PackageManager.PERMISSION_DENIED) {
//                        ActivityCompat.requestPermissions(SupervisionActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
//                    }
//                }
//
//                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//                    ActivityCompat.requestPermissions(SupervisionActivity.this,
//                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);
//                } else {
//                    descision = 3;
//                    takePhoto();
//
//                    locationStart();
//                    datetime =  today.monthDay + "/" + today.month + "/" + today.year + "T" + today.hour;
//                    Foto4.add(datetime);
//                    Foto4.add(""+Lng);
//                    Foto4.add(""+Lat);
//
//                }
//            }
//        });
//
//        btnFoto5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (checkSelfPermission(String.valueOf(Manifest.permission.CAMERA)) != PackageManager.PERMISSION_DENIED) {
//                        ActivityCompat.requestPermissions(SupervisionActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
//                    }
//                }
//
//                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//                    ActivityCompat.requestPermissions(SupervisionActivity.this,
//                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);
//                } else {
//                    descision = 4;
//                    takePhoto();
//
//                    locationStart();
//                    datetime =  today.monthDay + "/" + today.month + "/" + today.year + "T" + today.hour;
//                    Foto5.add(datetime);
//                    Foto5.add(""+Lng);
//                    Foto5.add(""+Lat);
//                }
//            }
//        });




        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((estado).equals("before") && cont1>0){
                    StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
                }else if ((estado).equals("during") && cont2>0){
                    StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
                }else if ((estado).equals("after") && cont3>0){
                    StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
                }else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Confirmación");
                        builder.setMessage("¿Está seguro?");
                        StyleableToast.makeText(getApplicationContext(), "Una vez realizada, esta acción no se puede revertir", Toast.LENGTH_SHORT, R.style.warningToast).show();
                        // builder.setCancelable(false);
                        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String Observation = edObserv.getText().toString();

                                if(!Observation.equals("")) {
                                    if ((estado).equals("before")) {

                                        if (cont1 > 0) {
                                            StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
                                            // Toast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT).show();
                                        } else {
                                            cont1++;
                                            boolean before = true;

                                            uploadImage1();
                                            uploadImage2();
                                            uploadImage3();
                                            uploadImage4();
                                            uploadImage5();

                                            uploadAllFiles();

                                            //PerFoto1.setSrc(src1);
                                            //PerFoto2.setSrc(src2);
                                            //PerFoto3.setSrc(src3);


                                            if (fileimagen != null) {
                                                evidence.add(PerFoto1);
                                            }
                                            if (fileimagen2 != null) {
                                                evidence.add(PerFoto2);
                                            }
                                            if (fileimagen3 != null) {
                                                evidence.add(PerFoto3);
                                            }
                                            if (fileimagen4 != null) {
                                                evidence.add(PerFoto4);
                                            }
                                            if (fileimagen5 != null) {
                                                evidence.add(PerFoto5);
                                            }
                                            if (fileimagen6 != null) {
                                                evidence.add(PerFoto6);
                                            }


                                            Data data = new Data(Observation, before, Lat, Lng, evidence);
                                            BDFireStore.collection("events").document(idevent).collection("observation").document("before").set(data);

                                            BDFireStore.collection("events").document(idevent).update("status", 2);
                                            // String k = mDatabase.child("Eventos").child(idevent).child("observation").child("before").child("status").;
                                            //mDatabase.child("Eventos").child(idevent).child("observation").child("before").setValue(data);

                                            //BDFireStore.collection("events").document("asdawd").set(data);
                                            //BDFireStore.collection("events").document(idevent).update("Observation", data);


                                            StyleableToast.makeText(getApplicationContext(), "Datos ingresados", Toast.LENGTH_LONG, R.style.sucessToast).show();
                                            //Toast.makeText(getApplicationContext(), "Datos ingresados", Toast.LENGTH_SHORT).show();


                                            edObserv.setText("");


                                            BorrarImagenes();
                                        }

                                        if (cont1 > 0 && cont2 > 0 && cont3 > 0) {


                                            //mDatabase.child("Eventos").child(idevent).child("active").setValue(false);
                                            BDFireStore.collection("events").document(idevent).set(nEvent);

                                            StyleableToast.makeText(getApplicationContext(), "Evento terminado", Toast.LENGTH_LONG, R.style.doneToast).show();
                                            //Toast.makeText(getApplicationContext(),"Evento terminado",Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    if ((estado).equals("during")) {

                                        if (cont2 > 0) {
                                            StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_LONG, R.style.warningToast).show();
                                            //Toast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT).show();
                                        } else {
                                            cont2++;
                                            boolean during = true;

                                            //mDatabase.child("Eventos").child(idevent).child("observation").child("during").setValue(data2);


                                            StyleableToast.makeText(getApplicationContext(), "Datos ingresados", Toast.LENGTH_LONG, R.style.sucessToast).show();
                                            //Toast.makeText(getApplicationContext(), "Datos ingresados", Toast.LENGTH_SHORT).show();

                                            edObserv.setText("");
                                            uploadImage1();
                                            uploadImage2();
                                            uploadImage3();
                                            uploadImage4();
                                            uploadImage5();

                                            uploadAllFiles();

                                            if (fileimagen != null) {
                                                evidence.add(PerFoto1);
                                            }
                                            if (fileimagen2 != null) {
                                                evidence.add(PerFoto2);
                                            }
                                            if (fileimagen3 != null) {
                                                evidence.add(PerFoto3);
                                            }
                                            if (fileimagen4 != null) {
                                                evidence.add(PerFoto4);
                                            }
                                            if (fileimagen5 != null) {
                                                evidence.add(PerFoto5);
                                            }
                                            if (fileimagen6 != null) {
                                                evidence.add(PerFoto6);
                                            }


                                            Data2 data2 = new Data2(Observation, during, Lat, Lng, evidence);
                                            BDFireStore.collection("events").document(idevent).collection("observation").document("during").set(data2);
                                            BorrarImagenes();
                                        }

                                        if (cont1 > 0 && cont2 > 0 && cont3 > 0) {

                                            //mDatabase.child("Eventos").child(idevent).child("active").setValue(false);
                                            BDFireStore.collection("events").document(idevent).set(nEvent);
                                            StyleableToast.makeText(getApplicationContext(), "Evento terminado", Toast.LENGTH_LONG, R.style.doneToast).show();
                                            //Toast.makeText(getApplicationContext(),"Evento terminado",Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    if ((estado).equals("after")) {

                                        if (cont3 > 0) {
                                            StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_LONG, R.style.warningToast).show();
                                            //Toast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT).show();
                                        } else {
                                            cont3++;
                                            boolean after = true;

                                            //mDatabase.child("Eventos").child(idevent).child("observation").child("after").setValue(data3);

                                            StyleableToast.makeText(getApplicationContext(), "Datos ingresados", Toast.LENGTH_LONG, R.style.sucessToast).show();
                                            // Toast.makeText(getApplicationContext(), "Datos ingresados", Toast.LENGTH_SHORT).show();

                                            edObserv.setText("");
                                            uploadImage1();
                                            uploadImage2();
                                            uploadImage3();
                                            uploadImage4();
                                            uploadImage5();

                                            uploadAllFiles();


                                            if (fileimagen != null) {
                                                evidence.add(PerFoto1);
                                            }
                                            if (fileimagen2 != null) {
                                                evidence.add(PerFoto2);
                                            }
                                            if (fileimagen3 != null) {
                                                evidence.add(PerFoto3);
                                            }
                                            if (fileimagen4 != null) {
                                                evidence.add(PerFoto4);
                                            }
                                            if (fileimagen5 != null) {
                                                evidence.add(PerFoto5);
                                            }
                                            if (fileimagen6 != null) {
                                                evidence.add(PerFoto6);
                                            }


                                            Data3 data3 = new Data3(Observation, after, Lat, Lng, evidence);
                                            BDFireStore.collection("events").document(idevent).collection("observation").document("after").set(data3);

                                            BorrarImagenes();
                                        }

                                        if (cont1 > 0 && cont2 > 0 && cont3 > 0) {

                                            //mDatabase.child("Eventos").child(idevent).child("active").setValue(false);
                                            BDFireStore.collection("events").document(idevent).update("active", false);
                                            BDFireStore.collection("events").document(idevent).update("status", 3);
                                            //Intent intent= new Intent (getApplicationContext(), Main2Activity.class);
                                            //startActivity(intent);
                                            StyleableToast.makeText(getApplicationContext(), "Evento terminado", Toast.LENGTH_SHORT, R.style.doneToast).show();
                                            //StyleableToast.makeText(getApplicationContext(), "Evento terminado", Toast.LENGTH_LONG, R.style.doneToast).show();
                                            //Toast.makeText(getApplicationContext(),"Evento terminado",Toast.LENGTH_SHORT).show();

                                            EventoTerminado();

                                        }

                                    }
                                }else{
                                    StyleableToast.makeText(getApplicationContext(), "El texto es necesario para poder enviar", Toast.LENGTH_SHORT, R.style.warningToastMiddle).show();
                                }
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.create().show();
                    }






            }

        });



        // -------------------Image view --------------------- Zoom ---------------------
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SupervisionActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                PhotoView photoView = mView.findViewById(R.id.imageView);
                photoView.setImageURI(Uri.fromFile(fileimagen));
                mBuilder.setView(mView);
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void EventoTerminado() {
        NotificationCompat.Builder mBuilder;
        NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICACION_CHANNEL_ID = "com.acadep.acadepsistemas.rso.test";

        int icono = R.mipmap.ic_launcher;
        Intent intent = new Intent(SupervisionActivity.this, Main2Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(SupervisionActivity.this, 0,intent, 0);

        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICACION_CHANNEL_ID);

        notiBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.rso)
                .setContentTitle("Evento terminado")
                .setContentText(nameEvent + " ha finalizado")
                .setContentInfo("info")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);


        notificationManager.notify(new Random().nextInt(), notiBuilder.build());
    }

    private void MostrarOpciones() {

        if((estado).equals("before") && cont1>0){
            StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
        }else if ((estado).equals("during") && cont2>0){
            StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
        }else if ((estado).equals("after") && cont3>0){
            StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
        }else {
            final CharSequence[] opciones = {"Borrar archivo anterior", "Cancelar"};
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);


            builder.setTitle("Borrar archivos");
            builder.setItems(opciones, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (opciones[i].equals("Borrar archivo anterior")) {
                        if (contUris == 0) {
                            StyleableToast.makeText(getApplicationContext(), "No hay ningún archivo para borrar", Toast.LENGTH_SHORT, R.style.warningToast).show();
                        } else if (ArchivoUri != null && contUris == 1) {
                            ArchivoUri = null;
                            contUris--;
                            StyleableToast.makeText(getApplicationContext(), "¡Archivo Borrado con exito!", Toast.LENGTH_SHORT, R.style.doneToast).show();
                        } else if (ArchivoUri2 != null && contUris == 2) {
                            ArchivoUri2 = null;
                            contUris--;
                            StyleableToast.makeText(getApplicationContext(), "¡Archivo Borrado con exito!", Toast.LENGTH_SHORT, R.style.doneToast).show();
                        } else if (ArchivoUri3 != null && contUris == 3) {
                            ArchivoUri3 = null;
                            contUris--;
                            StyleableToast.makeText(getApplicationContext(), "¡Archivo Borrado con exito!", Toast.LENGTH_SHORT, R.style.doneToast).show();
                        } else if (ArchivoUri4 != null && contUris == 4) {
                            ArchivoUri4 = null;
                            contUris--;
                            StyleableToast.makeText(getApplicationContext(), "¡Archivo Borrado con exito!", Toast.LENGTH_SHORT, R.style.doneToast).show();
                        } else if (ArchivoUri5 != null && contUris == 5) {
                            ArchivoUri5 = null;
                            contUris--;
                            StyleableToast.makeText(getApplicationContext(), "¡Archivo Borrado con exito!", Toast.LENGTH_SHORT, R.style.doneToast).show();
                        } else if (ArchivoUri6 != null && contUris == 6) {
                            ArchivoUri6 = null;
                            contUris--;
                            StyleableToast.makeText(getApplicationContext(), "¡Archivo Borrado con exito!", Toast.LENGTH_SHORT, R.style.doneToast).show();
                        } else if (ArchivoUri7 != null && contUris == 7) {
                            ArchivoUri7 = null;
                            contUris--;
                            StyleableToast.makeText(getApplicationContext(), "¡Archivo Borrado con exito!", Toast.LENGTH_SHORT, R.style.doneToast).show();
                        } else if (ArchivoUri8 != null && contUris == 8) {
                            ArchivoUri8 = null;
                            contUris--;
                            StyleableToast.makeText(getApplicationContext(), "¡Archivo Borrado con exito!", Toast.LENGTH_SHORT, R.style.doneToast).show();
                        } else if (ArchivoUri9 != null && contUris == 9) {
                            ArchivoUri9 = null;
                            contUris--;
                            StyleableToast.makeText(getApplicationContext(), "¡Archivo Borrado con exito!", Toast.LENGTH_SHORT, R.style.doneToast).show();
                        } else if (ArchivoUri10 != null && contUris == 10) {
                            ArchivoUri10 = null;
                            contUris--;
                            StyleableToast.makeText(getApplicationContext(), "¡Archivo Borrado con exito!", Toast.LENGTH_SHORT, R.style.doneToast).show();
                        }
                    } else {
                        dialogInterface.dismiss();
                    }
                }

            });
            builder.show();
        }
    }

    private void uploadAllFiles() {
        if (ArchivoUri != null && contUris==1) {
            uploadfile(ArchivoUri);
            contUris=0;
            ArchivoUri=null;
        }else if(ArchivoUri2 != null && contUris==2) {
            uploadfile(ArchivoUri);
            uploadfile2(ArchivoUri2);
            contUris=0;

            ArchivoUri=null;
            ArchivoUri2=null;
        }else if(ArchivoUri3 != null && contUris==3) {
            uploadfile(ArchivoUri);
            uploadfile2(ArchivoUri2);
            uploadfile3(ArchivoUri3);
            contUris=0;

            ArchivoUri=null;
            ArchivoUri2=null;
            ArchivoUri3=null;
        }else if(ArchivoUri4 != null && contUris==4) {
            uploadfile(ArchivoUri);
            uploadfile2(ArchivoUri2);
            uploadfile3(ArchivoUri3);
            uploadfile4(ArchivoUri4);
            contUris=0;

            ArchivoUri=null;
            ArchivoUri2=null;
            ArchivoUri3=null;
            ArchivoUri4=null;
        }else if(ArchivoUri5 != null && contUris==5) {
            uploadfile(ArchivoUri);
            uploadfile2(ArchivoUri2);
            uploadfile3(ArchivoUri3);
            uploadfile4(ArchivoUri4);
            uploadfile5(ArchivoUri5);
            contUris=0;

            ArchivoUri=null;
            ArchivoUri2=null;
            ArchivoUri3=null;
            ArchivoUri4=null;
            ArchivoUri5=null;
        }else if(ArchivoUri6 != null && contUris==6) {
            uploadfile(ArchivoUri);
            uploadfile2(ArchivoUri2);
            uploadfile3(ArchivoUri3);
            uploadfile4(ArchivoUri4);
            uploadfile5(ArchivoUri5);
            uploadfile6(ArchivoUri6);
            contUris=0;

            ArchivoUri=null;
            ArchivoUri2=null;
            ArchivoUri3=null;
            ArchivoUri4=null;
            ArchivoUri5=null;
            ArchivoUri6=null;
        }else if(ArchivoUri7 != null && contUris==7) {
            uploadfile(ArchivoUri);
            uploadfile2(ArchivoUri2);
            uploadfile3(ArchivoUri3);
            uploadfile4(ArchivoUri4);
            uploadfile5(ArchivoUri5);
            uploadfile6(ArchivoUri6);
            uploadfile7(ArchivoUri7);
            contUris=0;

            ArchivoUri=null;
            ArchivoUri2=null;
            ArchivoUri3=null;
            ArchivoUri4=null;
            ArchivoUri5=null;
            ArchivoUri6=null;
            ArchivoUri7=null;
        }else if(ArchivoUri8 != null && contUris==8) {
            uploadfile(ArchivoUri);
            uploadfile2(ArchivoUri2);
            uploadfile3(ArchivoUri3);
            uploadfile4(ArchivoUri4);
            uploadfile5(ArchivoUri5);
            uploadfile6(ArchivoUri6);
            uploadfile7(ArchivoUri7);
            uploadfile8(ArchivoUri8);
            contUris=0;

            ArchivoUri=null;
            ArchivoUri2=null;
            ArchivoUri3=null;
            ArchivoUri4=null;
            ArchivoUri5=null;
            ArchivoUri6=null;
            ArchivoUri7=null;
            ArchivoUri8=null;
        }else if(ArchivoUri9 != null && contUris==9) {
            uploadfile(ArchivoUri);
            uploadfile2(ArchivoUri2);
            uploadfile3(ArchivoUri3);
            uploadfile4(ArchivoUri4);
            uploadfile5(ArchivoUri5);
            uploadfile6(ArchivoUri6);
            uploadfile7(ArchivoUri7);
            uploadfile8(ArchivoUri8);
            uploadfile9(ArchivoUri9);
            contUris=0;

            ArchivoUri=null;
            ArchivoUri2=null;
            ArchivoUri3=null;
            ArchivoUri4=null;
            ArchivoUri5=null;
            ArchivoUri6=null;
            ArchivoUri7=null;
            ArchivoUri8=null;
            ArchivoUri9=null;
        }else if(ArchivoUri10 != null && contUris==10) {
            uploadfile(ArchivoUri);
            uploadfile2(ArchivoUri2);
            uploadfile3(ArchivoUri3);
            uploadfile4(ArchivoUri4);
            uploadfile5(ArchivoUri5);
            uploadfile6(ArchivoUri6);
            uploadfile7(ArchivoUri7);
            uploadfile8(ArchivoUri8);
            uploadfile9(ArchivoUri9);
            uploadfile10(ArchivoUri10);
            contUris=0;

            ArchivoUri=null;
            ArchivoUri2=null;
            ArchivoUri3=null;
            ArchivoUri4=null;
            ArchivoUri5=null;
            ArchivoUri6=null;
            ArchivoUri7=null;
            ArchivoUri8=null;
            ArchivoUri9=null;
            ArchivoUri10=null;
        }
    }



    private void mostrarDialogoOpciones() {

        if((estado).equals("before") && cont1>0){
            StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
        }else if ((estado).equals("during") && cont2>0){
            StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
        }else if ((estado).equals("after") && cont3>0){
            StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
        }else {
            final CharSequence[] opciones = {"Elegir PDF", "Elegir Docx", "Elegir Video", "Elegir Audio", "Cancelar"};
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            if (contUris == 0) {
                StyleableToast.makeText(getApplicationContext(), "Solo puede añadir 10 archivos diferentes", Toast.LENGTH_SHORT, R.style.warningToast).show();
                //Toast.makeText(getApplicationContext(),"Solo puede añadir 10 archivos diferentes \nUna vez agregado un archivo este no puede ser eliminado",Toast.LENGTH_LONG).show();
            }

            builder.setTitle("Elige un Archivo");
            builder.setItems(opciones, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (opciones[i].equals("Elegir PDF")) {
                        selectPDF();
                    } else if (opciones[i].equals("Elegir Docx")) {
                        selectDocx();
                    } else if (opciones[i].equals("Elegir Video")) {
                        selectVideo();
                    } else if (opciones[i].equals("Elegir Audio")) {
                        selectAudio();
                    } else {
                        dialogInterface.dismiss();
                    }
                }
            });
            builder.show();
        }

       // Toast.makeText(getApplicationContext(),"Mostrar dialogo opciones", Toast.LENGTH_SHORT).show();

       /* final CharSequence [] opciones={"Elegir PDF", "Elegir Docx", "Elegir Video", "Elegir Audio","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

        builder.setTitle("Elige una Opcion");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(opciones[i].equals("Elegir PDF")){
                    selectPDF();
                    }else if(opciones[i].equals("Elegir Docx")){
                        selectDocx();
                     }else if(opciones[i].equals("Elegir Video")){
                         selectVideo();
                        }else if(opciones[i].equals("Elegir Audio")){
                            selectAudio();
                            }else{
                                dialog.dismiss();
                            }
            }
        });
        builder.show();*/

    }

    private void chequeoDevariables() {

        /*CollectionReference refEvents = BDFireStore.collection("events");
        CollectionReference refObservation = BDFireStore.collection("Observation");

        DocumentReference refIDEvent = BDFireStore.document(idevent);
        DocumentReference refBefore = BDFireStore.document("before");
        DocumentReference refduring = BDFireStore.document("during");
        DocumentReference refafter = BDFireStore.document("after");*/






        BDFireStore.collection("events")
                .document(idevent)
                .collection("observation")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){

                                                        Data check = documentSnapshot.toObject(Data.class);
                                                        Data2 check2 = documentSnapshot.toObject(Data2.class);
                                                        Data3 check3 = documentSnapshot.toObject(Data3.class);

                                                        if(check.isBefore()==true) {
                                                            cont1++;
                                                            bottomNavigationView.getSelectedItemId();
                                                            bottomNavigationView.setEnabled(false);
                                                            bottomNavigationView.setSelectedItemId(R.id.itemDurante);
                                                            estado = "during";
                                                            txtEstado.setText("Durante el Evento");
                                                        }

                                                        if(check2.isDuring()==true) {
                                                            cont2++;
                                                            bottomNavigationView.getSelectedItemId();
                                                            bottomNavigationView.setEnabled(false);
                                                            bottomNavigationView.setSelectedItemId(R.id.itemDespues);
                                                            estado = "after";
                                                            txtEstado.setText("Después del Evento");
                                                        }

                                                        if(check3.isAfter()==true) {
                                                            cont3++;
                                                            StyleableToast.makeText(getApplicationContext(), "Evento terminado", Toast.LENGTH_SHORT, R.style.doneToast).show();

                                                        }

                                                    }

                                                }
                                            });


        /* Query ok = ref.orderByChild("before").equalTo(true);
        //(mDatabase.child("Eventos").child(idevent).child("observation").child("before").child("observ").equalTo(""));
        ok.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    cont1++;
                    //Toast.makeText(getApplicationContext(),"he: "+cont1,Toast.LENGTH_SHORT).show();
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query ok1 = ref.orderByChild("during").equalTo(true);
        //(mDatabase.child("Eventos").child(idevent).child("observation").child("before").child("observ").equalTo(""));
        ok1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    cont2++;
                    //Toast.makeText(getApplicationContext(),"he: "+cont2,Toast.LENGTH_SHORT).show();
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query ok3 = ref.orderByChild("after").equalTo(true);
        //(mDatabase.child("Eventos").child(idevent).child("observation").child("before").child("observ").equalTo(""));
        ok3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    cont3++;
                    // Toast.makeText(getApplicationContext(),"he: "+cont1,Toast.LENGTH_SHORT).show();
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/



    }

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
        mensaje1.setText("Localización agregada");
        mensaje2.setText("");
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectPDF();
        }else{
            StyleableToast.makeText(getApplicationContext(), "Porfavor otorgue los permisos...", Toast.LENGTH_LONG, R.style.warningToast).show();
            //Toast.makeText(getApplicationContext(),"Porfavor otorgue los permisos...",Toast.LENGTH_SHORT).show();
        }

        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }
    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    mensaje2.setText("Mi direccion es: \n"
                            + DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /* Aqui empieza la Clase Localizacion */
    public static class Localizacion implements LocationListener {
        SupervisionActivity mainActivity3;
        public SupervisionActivity getMainActivity() {
            return mainActivity3;
        }
        public void setMainActivity(SupervisionActivity mainActivity) {
            this.mainActivity3 = mainActivity;
        }
        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion
            loc.getLatitude();
            loc.getLongitude();
            String Text = "Mi ubicacion actual es: " + "\n Lat = "
                    + loc.getLatitude() + "\n Long = " + loc.getLongitude();

            //----------------------------------------------------------------------------------------------------------------------------------------------------------
            Lat=loc.getLatitude();
            Lng=loc.getLongitude();

            //mensaje1.setText(Text);
            mensaje1.setText(Text);
            this.mainActivity3.setLocation(loc);
        }
        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            mensaje1.setText("GPS Desactivado");
        }
        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            mensaje1.setText("GPS Activado");
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }


    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = true;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    private void updateLocationUI() {
                if (mCurrentLocation != null) {
                    txtLocationResult.setText(
                            "Lat: " + mCurrentLocation.getLatitude() + ", " +
                                    "Lng: " + mCurrentLocation.getLongitude()
                    );

                    // giving a blink animation on TextView
                    txtLocationResult.setAlpha(0);
                    txtLocationResult.animate().alpha(1).setDuration(300);

                    // location last updated time
                    //txtUpdatedOn.setText("Last updated on: " + mLastUpdateTime);
                }

               // toggleButtons();
            }

    private void BorrarImagenes() {
        imageView.setImageResource(R.drawable.empty_image);
        checkBox1.setChecked(false);
        fileimagen = null;

        imageView2.setImageResource(R.drawable.empty_image);
        checkBox2.setChecked(false);
        fileimagen2 = null;

        imageView3.setImageResource(R.drawable.empty_image);
        checkBox3.setChecked(false);
        fileimagen3 = null;

        imageView4.setImageResource(R.drawable.empty_image);
        checkBox4.setChecked(false);
        fileimagen4 = null;

        imageView5.setImageResource(R.drawable.empty_image);
        checkBox5.setChecked(false);
        fileimagen5 = null;

        imageView6.setImageResource(R.drawable.empty_image);
        checkBox6.setChecked(false);
        fileimagen6 = null;

        checkBox1.setVisibility(View.INVISIBLE);
        checkBox2.setVisibility(View.INVISIBLE);
        checkBox3.setVisibility(View.INVISIBLE);
        checkBox4.setVisibility(View.INVISIBLE);
        checkBox5.setVisibility(View.INVISIBLE);
        checkBox6.setVisibility(View.INVISIBLE);


        imageView.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
        imageView3.setVisibility(View.INVISIBLE);
        imageView4.setVisibility(View.INVISIBLE);
        imageView5.setVisibility(View.INVISIBLE);
        imageView6.setVisibility(View.INVISIBLE);
    }

    public void takePhoto() {
        if((estado).equals("before") && cont1>0){
            StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
        }else if ((estado).equals("during") && cont2>0){
            StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
        }else if ((estado).equals("after") && cont3>0){
            StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
        }else {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAPTURE_PHOTO);
        }
    }

    private void uploadImage1() {
        if (fileimagen != null) {
            final ProgressDialog progressDialog = new ProgressDialog(SupervisionActivity.this);
            progressDialog.setTitle("Subiendo....");

            final StorageReference ref = storageReference.child("images").child(idevent).child(estado).child("Img" + UUID.randomUUID().toString());
            // StorageReference ref = storageReference.child("images/"+UUID.randomUUID().toString());

            final UploadTask uploadTask = ref.putFile(Uri.fromFile(fileimagen));

//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    String message = e.toString();
//                    Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
//                }
//            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(getApplicationContext(), "Exito al Subirse", Toast.LENGTH_SHORT).show();
//
//                    src1 = taskSnapshot.getMetadata().getPath();
//
//                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                        @Override
//                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                            if (!task.isSuccessful()) {
//                                throw task.getException();
//                            }
//
//                            src1 = ref.getDownloadUrl().toString();
//                            //PerFoto1.setSrc(src1);
//                            // Continue with the task to get the download URL
//                            return ref.getDownloadUrl();
//                        }
//                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Uri> task) {
//                            if (task.isSuccessful()) {
//                                Uri downloadUri = task.getResult();
//
//                                urls();
//                            } else {
//                                // Handle failures
//                                // ...
//                            }
//                        }
//                    });
//                }
//            });
//
//
//
//            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    PerFoto1.setSrc(uri.toString());
//                }
//            });



            ref.putFile(Uri.fromFile(fileimagen))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();


                            // Toast.makeText(getApplicationContext(), "Exito al Subirse", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            StyleableToast.makeText(getApplicationContext(), "Fallo al Subir", Toast.LENGTH_LONG, R.style.dangerToast).show();
                           // Toast.makeText(getApplicationContext(), "Fallo al Subir", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                String downloadUri = ""+task.getResult().toString();
                                src1 = downloadUri;
                            }else{
                                StyleableToast.makeText(getApplicationContext(), "Ocurrio un error", Toast.LENGTH_LONG, R.style.dangerToast).show();
                            }
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Subido " + (int) progress + "%");
                        }
                    });

            //src1 = ""+ref.getDownloadUrl();





        }
    }

    private void urls() {
        PerFoto1.setSrc(src1);
    }

    private void uploadImage2() {
        if (fileimagen2 != null) {
            final ProgressDialog progressDialog = new ProgressDialog(SupervisionActivity.this);
            progressDialog.setTitle("Subiendo....");

            StorageReference ref = storageReference.child("images").child(idevent).child(estado).child("Img" + UUID.randomUUID().toString());            // StorageReference ref = storageReference.child("images/"+UUID.randomUUID().toString());



            ref.putFile(Uri.fromFile(fileimagen2))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            StyleableToast.makeText(getApplicationContext(), "Fallo al Subir", Toast.LENGTH_LONG, R.style.dangerToast).show();
                            //Toast.makeText(getApplicationContext(), "Fallo al Subir", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                UploadTask.TaskSnapshot downloadUri = task.getResult();

                            }
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Subido " + (int) progress + "%");
                        }
                    });
            src2 = ""+ref.getDownloadUrl();
        }
    }

    private void uploadImage3() {
        if (fileimagen3 != null) {
            final ProgressDialog progressDialog = new ProgressDialog(SupervisionActivity.this);
            progressDialog.setTitle("Subiendo....");

            final StorageReference ref = storageReference.child("images").child(idevent).child(estado).child("Img" + UUID.randomUUID().toString());
            // StorageReference ref = storageReference.child("images/"+UUID.randomUUID().toString());

            ref.putFile(Uri.fromFile(fileimagen3))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            // Toast.makeText(getApplicationContext(), "Exito al Subirse", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            StyleableToast.makeText(getApplicationContext(), "Fallo al Subir", Toast.LENGTH_LONG, R.style.dangerToast).show();
                            //Toast.makeText(getApplicationContext(), "Fallo al Subir", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                UploadTask.TaskSnapshot downloadUri = task.getResult();

                            }
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Subido " + (int) progress + "%");
                        }
                    });
            src3 = ""+ref.getDownloadUrl();



        }
    }

    private void uploadImage4() {
        if (fileimagen4 != null) {
            final ProgressDialog progressDialog = new ProgressDialog(SupervisionActivity.this);
            progressDialog.setTitle("Subiendo....");

            StorageReference ref = storageReference.child("images").child(idevent).child(estado).child("Img" + UUID.randomUUID().toString());
            // StorageReference ref = storageReference.child("images/"+UUID.randomUUID().toString());

            ref.putFile(Uri.fromFile(fileimagen4))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            // Toast.makeText(getApplicationContext(), "Exito al Subirse", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            StyleableToast.makeText(getApplicationContext(), "Fallo al Subir", Toast.LENGTH_LONG, R.style.dangerToast).show();
                            //Toast.makeText(getApplicationContext(), "Fallo al Subir", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Subido " + (int) progress + "%");
                        }
                    });
        }
    }

    private void uploadImage5() {
        if (fileimagen5 != null) {
            final ProgressDialog progressDialog = new ProgressDialog(SupervisionActivity.this);
            progressDialog.setTitle("Subiendo....");

            StorageReference ref = storageReference.child("images").child(idevent).child(estado).child("Img" + UUID.randomUUID().toString());
            // StorageReference ref = storageReference.child("images/"+UUID.randomUUID().toString());

            ref.putFile(Uri.fromFile(fileimagen5))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            // Toast.makeText(getApplicationContext(), "Exito al Subirse", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            StyleableToast.makeText(getApplicationContext(), "Fallo al Subir", Toast.LENGTH_LONG, R.style.dangerToast).show();
                            //Toast.makeText(getApplicationContext(), "Fallo al Subir", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Subido " + (int) progress + "%");
                        }
                    });
        }
    }




    private void uploadfile(Uri ArchivoUri) {
        progressDialog= new ProgressDialog(SupervisionActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo archivo...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName= "Archivo" + UUID.randomUUID().toString();
        StorageReference srtreference = storage.getReference();
        srtreference.child("Archivos").child(idevent).child(estado).child(fileName).putFile(ArchivoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getUploadSessionUri().toString();
                        DatabaseReference refDB = dbRef.getReference();

                        refDB.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    StyleableToast.makeText(getApplicationContext(), "Archivo Subido...", Toast.LENGTH_SHORT, R.style.doneToast).show();
                                    //Toast.makeText(getApplicationContext(),"Archivo Subido...",Toast.LENGTH_SHORT).show();

                                }else{
                                    StyleableToast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                                    //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                StyleableToast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currenProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currenProgress);

            }
        });
        if(contUris>1) {
            progressDialog.hide();
        }

    }



    private void uploadfile2(Uri ArchivoUri) {
        progressDialog= new ProgressDialog(SupervisionActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo archivos...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName= "Archivo" + UUID.randomUUID().toString();
        StorageReference srtreference = storage.getReference();
        srtreference.child("Archivos").child(idevent).child(estado).child(fileName).putFile(ArchivoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getUploadSessionUri().toString();
                        DatabaseReference refDB = dbRef.getReference();

                        refDB.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    StyleableToast.makeText(getApplicationContext(), "Archivo Subido...", Toast.LENGTH_SHORT, R.style.doneToast).show();
                                    //Toast.makeText(getApplicationContext(),"Archivo Subido...",Toast.LENGTH_SHORT).show();
                                }else{
                                    StyleableToast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                                    //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                StyleableToast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currenProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currenProgress);

            }
        });
        if(contUris>2) {
            progressDialog.hide();
        }

    }

    private void uploadfile3(Uri ArchivoUri) {
        progressDialog= new ProgressDialog(SupervisionActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo archivos...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName= "Archivo" + UUID.randomUUID().toString();
        StorageReference srtreference = storage.getReference();
        srtreference.child("Archivos").child(idevent).child(estado).child(fileName).putFile(ArchivoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getUploadSessionUri().toString();
                        DatabaseReference refDB = dbRef.getReference();

                        refDB.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    StyleableToast.makeText(getApplicationContext(), "Archivo Subido...", Toast.LENGTH_SHORT, R.style.doneToast).show();
                                    //Toast.makeText(getApplicationContext(),"Archivo Subido...",Toast.LENGTH_SHORT).show();
                                }else{
                                    StyleableToast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                                    //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                StyleableToast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currenProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currenProgress);

            }
        });
        if(contUris>3) {
            progressDialog.hide();
        }

    }

    private void uploadfile4(Uri ArchivoUri) {
        progressDialog= new ProgressDialog(SupervisionActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo archivos...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName= "Archivo" + UUID.randomUUID().toString();
        StorageReference srtreference = storage.getReference();
        srtreference.child("Archivos").child(idevent).child(estado).child(fileName).putFile(ArchivoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getUploadSessionUri().toString();
                        DatabaseReference refDB = dbRef.getReference();

                        refDB.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    StyleableToast.makeText(getApplicationContext(), "Archivo Subido...", Toast.LENGTH_SHORT, R.style.doneToast).show();
                                    //Toast.makeText(getApplicationContext(),"Archivo Subido...",Toast.LENGTH_SHORT).show();
                                }else{
                                    StyleableToast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                                    //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                StyleableToast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currenProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currenProgress);

            }
        });
        if(contUris>4) {
            progressDialog.hide();
        }

    }

    private void uploadfile5(Uri ArchivoUri) {
        progressDialog= new ProgressDialog(SupervisionActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo archivos...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName= "Archivo" + UUID.randomUUID().toString();
        StorageReference srtreference = storage.getReference();
        srtreference.child("Archivos").child(idevent).child(estado).child(fileName).putFile(ArchivoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getUploadSessionUri().toString();
                        DatabaseReference refDB = dbRef.getReference();

                        refDB.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    StyleableToast.makeText(getApplicationContext(), "Archivo Subido...", Toast.LENGTH_SHORT, R.style.doneToast).show();
                                    //Toast.makeText(getApplicationContext(),"Archivo Subido...",Toast.LENGTH_SHORT).show();
                                }else{
                                    StyleableToast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                                    //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                StyleableToast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currenProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currenProgress);

            }
        });
        if(contUris>5) {
            progressDialog.hide();
        }

    }

    private void uploadfile6(Uri ArchivoUri) {
        progressDialog= new ProgressDialog(SupervisionActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo archivos...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName= "Archivo" + UUID.randomUUID().toString();
        StorageReference srtreference = storage.getReference();
        srtreference.child("Archivos").child(idevent).child(estado).child(fileName).putFile(ArchivoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getUploadSessionUri().toString();
                        DatabaseReference refDB = dbRef.getReference();

                        refDB.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    StyleableToast.makeText(getApplicationContext(), "Archivo Subido...", Toast.LENGTH_SHORT, R.style.doneToast).show();
                                    //Toast.makeText(getApplicationContext(),"Archivo Subido...",Toast.LENGTH_SHORT).show();
                                }else{
                                    StyleableToast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                                    //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                StyleableToast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currenProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currenProgress);

            }
        });
        if(contUris>6) {
            progressDialog.hide();
        }


    }

    private void uploadfile7(Uri ArchivoUri) {
        progressDialog= new ProgressDialog(SupervisionActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo archivos...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName= "Archivo" + UUID.randomUUID().toString();
        StorageReference srtreference = storage.getReference();
        srtreference.child("Archivos").child(idevent).child(estado).child(fileName).putFile(ArchivoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getUploadSessionUri().toString();
                        DatabaseReference refDB = dbRef.getReference();

                        refDB.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    StyleableToast.makeText(getApplicationContext(), "Archivo Subido...", Toast.LENGTH_SHORT, R.style.doneToast).show();
                                    //Toast.makeText(getApplicationContext(),"Archivo Subido...",Toast.LENGTH_SHORT).show();
                                }else{
                                    StyleableToast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                                    //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                StyleableToast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currenProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currenProgress);

            }
        });
        if(contUris>7) {
            progressDialog.hide();
        }


    }

    private void uploadfile8(Uri ArchivoUri) {
        progressDialog= new ProgressDialog(SupervisionActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo archivos...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName= "Archivo" + UUID.randomUUID().toString();
        StorageReference srtreference = storage.getReference();
        srtreference.child("Archivos").child(idevent).child(estado).child(fileName).putFile(ArchivoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getUploadSessionUri().toString();
                        DatabaseReference refDB = dbRef.getReference();

                        refDB.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    StyleableToast.makeText(getApplicationContext(), "Archivo Subido...", Toast.LENGTH_SHORT, R.style.doneToast).show();
                                    //Toast.makeText(getApplicationContext(),"Archivo Subido...",Toast.LENGTH_SHORT).show();
                                }else{
                                    StyleableToast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                                    //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                StyleableToast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currenProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currenProgress);

            }
        });
        if(contUris>8) {
            progressDialog.hide();
        }


    }

    private void uploadfile9(Uri ArchivoUri) {
        progressDialog= new ProgressDialog(SupervisionActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo archivos...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName= "Archivo" + UUID.randomUUID().toString();
        StorageReference srtreference = storage.getReference();
        srtreference.child("Archivos").child(idevent).child(estado).child(fileName).putFile(ArchivoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getUploadSessionUri().toString();
                        DatabaseReference refDB = dbRef.getReference();

                        refDB.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    StyleableToast.makeText(getApplicationContext(), "Archivo Subido...", Toast.LENGTH_SHORT, R.style.doneToast).show();
                                    //Toast.makeText(getApplicationContext(),"Archivo Subido...",Toast.LENGTH_SHORT).show();
                                }else{
                                    StyleableToast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                                    //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                StyleableToast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currenProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currenProgress);

            }
        });
        if(contUris>9) {
            progressDialog.hide();
        }


    }

    private void uploadfile10(Uri ArchivoUri) {
        progressDialog= new ProgressDialog(SupervisionActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo archivos...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName= "Archivo" + UUID.randomUUID().toString();
        StorageReference srtreference = storage.getReference();
        srtreference.child("Archivos").child(idevent).child(estado).child(fileName).putFile(ArchivoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getUploadSessionUri().toString();
                        DatabaseReference refDB = dbRef.getReference();

                        refDB.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    StyleableToast.makeText(getApplicationContext(), "Archivo Subido...", Toast.LENGTH_SHORT, R.style.doneToast).show();
                                    //Toast.makeText(getApplicationContext(),"Archivo Subido...",Toast.LENGTH_SHORT).show();
                                }else{
                                    StyleableToast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                                    //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                StyleableToast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currenProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currenProgress);

            }
        });



    }




    private void selectPDF() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
    }

    private void selectVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 87);
    }

    private void selectAudio() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 88);
    }

    private void selectDocx() {
        Intent intent = new Intent();
        intent.setType("docx/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 89);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode==86 && resultCode==RESULT_OK && data!=null) {
            if(contUris>9){
                StyleableToast.makeText(getApplicationContext(), "Limite de archivos alcanzado", Toast.LENGTH_LONG, R.style.warningToast).show();
            }else {
                SelecUri(data);
                contUris++;
            }
            //Toast.makeText(getApplicationContext(),"Tu archivo se ha guardado exitosamente",Toast.LENGTH_SHORT).show();
        }else if (requestCode==87 && resultCode==RESULT_OK && data!=null) {
            if(contUris>9){
                StyleableToast.makeText(getApplicationContext(), "Limite de archivos alcanzado", Toast.LENGTH_LONG, R.style.warningToast).show();
            }else {
                SelecUri(data);
                contUris++;
            }
            //Toast.makeText(getApplicationContext(),"Tu archivo se ha guardado exitosamente",Toast.LENGTH_SHORT).show();
        } if (requestCode==88 && resultCode==RESULT_OK && data!=null) {
            if(contUris>9){
                StyleableToast.makeText(getApplicationContext(), "Limite de archivos alcanzado", Toast.LENGTH_LONG, R.style.warningToast).show();
            }else {
                SelecUri(data);
                contUris++;
            }
            //Toast.makeText(getApplicationContext(),"Tu archivo se ha guardado exitosamente",Toast.LENGTH_SHORT).show();
        }
        if (requestCode==89 && resultCode==RESULT_OK && data!=null) {
            if(contUris>9){
                StyleableToast.makeText(getApplicationContext(), "Limite de archivos alcanzado", Toast.LENGTH_LONG, R.style.warningToast).show();
            }else {
                SelecUri(data);
                contUris++;
            }
           // Toast.makeText(getApplicationContext(),"Tu archivo se ha guardado exitosamente",Toast.LENGTH_SHORT).show();
        }

        if (requestCode==104 && resultCode == RESULT_OK) {
            switch (requestCode) {

                case CAPTURE_PHOTO:

                    capturedCoolerBitmap = (Bitmap) data.getExtras().get("data");


                    int CamWidth = 1200;
                    int CamHegith = 800;

                    //Bitmap resizeImage = Bitmap.createScaledBitmap(capturedCoolerBitmap,CamWidth,CamHegith,false);
                    Bitmap Bitnew = redimensionarImagenMaximo(capturedCoolerBitmap, 1200, 800);


                    for(int i=0; i<6; i++) {
                        if (fileimagen==null){
                            descision=0;
                            imageView.setImageBitmap(Bitnew);
                            saveImageToGallery(Bitnew);

                            break;
                        } else if (fileimagen2==null) {
                            descision=1;
                            imageView2.setImageBitmap(Bitnew);
                            saveImageToGallery(Bitnew);
                            break;
                        } else if (fileimagen3 ==null) {
                            descision=2;
                            imageView3.setImageBitmap(Bitnew);
                            saveImageToGallery(Bitnew);
                            break;
                        } else if (fileimagen4 == null) {
                            descision=3;
                            imageView4.setImageBitmap(Bitnew);
                            saveImageToGallery(Bitnew);
                            break;
                        } else if (fileimagen5 == null) {
                            descision=4;
                            imageView5.setImageBitmap(Bitnew);
                            saveImageToGallery(Bitnew);
                            break;
                        } else if (fileimagen6 == null) {
                            descision=5;
                            imageView6.setImageBitmap(Bitnew);
                            saveImageToGallery(Bitnew);
                            break;
                        }
                    }

                    break;

                default:
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    break;
            }
        }





    }

    private void SelecUri(Intent data) {

        if(contUris==0){
            ArchivoUri=data.getData();
            StyleableToast.makeText(getApplicationContext(), "Archivo agregado con exito \nPuede subir " + (restUris-contUris) + " más", Toast.LENGTH_LONG, R.style.sucessToast).show();
           // Toast.makeText(getApplicationContext(),"Archivo agregado con exito \nPuede subir " + (restUris-contUris) + " más",Toast.LENGTH_SHORT).show();
        }else if(contUris==1){
            ArchivoUri2=data.getData();
            StyleableToast.makeText(getApplicationContext(), "Archivo agregado con exito \nPuede subir " + (restUris-contUris) + " más", Toast.LENGTH_LONG, R.style.sucessToast).show();
            //Toast.makeText(getApplicationContext(),"Archivo agregado con exito \nPuede subir " + (restUris-contUris) + " más",Toast.LENGTH_SHORT).show();
        }else if(contUris==2){
            ArchivoUri3=data.getData();
            StyleableToast.makeText(getApplicationContext(), "Archivo agregado con exito \nPuede subir " + (restUris-contUris) + " más", Toast.LENGTH_LONG, R.style.sucessToast).show();
            //Toast.makeText(getApplicationContext(),"Archivo agregado con exito /nPuede subir " + (restUris-contUris) + " más",Toast.LENGTH_SHORT).show();
        }else if(contUris==3){
            ArchivoUri4=data.getData();
            StyleableToast.makeText(getApplicationContext(), "Archivo agregado con exito \nPuede subir " + (restUris-contUris) + " más", Toast.LENGTH_LONG, R.style.sucessToast).show();
            //Toast.makeText(getApplicationContext(),"Archivo agregado con exito /nPuede subir " + (restUris-contUris) + " más",Toast.LENGTH_SHORT).show();
        }else if(contUris==4){
            ArchivoUri5=data.getData();
            StyleableToast.makeText(getApplicationContext(), "Archivo agregado con exito \nPuede subir " + (restUris-contUris) + " más", Toast.LENGTH_LONG, R.style.sucessToast).show();
            //Toast.makeText(getApplicationContext(),"Archivo agregado con exito /nPuede subir " + (restUris-contUris) + " más",Toast.LENGTH_SHORT).show();
        }else if(contUris==5){
            ArchivoUri6=data.getData();
            StyleableToast.makeText(getApplicationContext(), "Archivo agregado con exito \nPuede subir " + (restUris-contUris) + " más", Toast.LENGTH_LONG, R.style.sucessToast).show();
            //Toast.makeText(getApplicationContext(),"Archivo agregado con exito /nPuede subir " + (restUris-contUris) + " más",Toast.LENGTH_SHORT).show();
        }else if(contUris==6){
            ArchivoUri7=data.getData();
            StyleableToast.makeText(getApplicationContext(), "Archivo agregado con exito \nPuede subir " + (restUris-contUris) + " más", Toast.LENGTH_LONG, R.style.sucessToast).show();
            //Toast.makeText(getApplicationContext(),"Archivo agregado con exito /nPuede subir " + (restUris-contUris) + " más",Toast.LENGTH_SHORT).show();
        }else if(contUris==7){
            ArchivoUri8=data.getData();
            StyleableToast.makeText(getApplicationContext(), "Archivo agregado con exito \nPuede subir " + (restUris-contUris) + " más", Toast.LENGTH_LONG, R.style.sucessToast).show();
            //Toast.makeText(getApplicationContext(),"Archivo agregado con exito /nPuede subir " + (restUris-contUris) + " más",Toast.LENGTH_SHORT).show();
        }else if(contUris==8){
            ArchivoUri9=data.getData();
            StyleableToast.makeText(getApplicationContext(), "Archivo agregado con exito \nPuede subir " + (restUris-contUris) + " más", Toast.LENGTH_LONG, R.style.sucessToast).show();
            //Toast.makeText(getApplicationContext(),"Archivo agregado con exito /nPuede subir " + (restUris-contUris) + " más",Toast.LENGTH_SHORT).show();
        }else if(contUris==9){
            ArchivoUri10=data.getData();
            StyleableToast.makeText(getApplicationContext(), "Archivo agregado con exito \nYa no puede subir más", Toast.LENGTH_LONG, R.style.sucessToast).show();
            //Toast.makeText(getApplicationContext(),"Archivo agregado con exito /nPuede subir " + (restUris-contUris) + " más",Toast.LENGTH_SHORT).show();
        }else{
            StyleableToast.makeText(getApplicationContext(), "Limite de archivos alcanzado", Toast.LENGTH_LONG, R.style.warningToast).show();
            //Toast.makeText(getApplicationContext(),"Ya no puede subir ningun archivo",Toast.LENGTH_SHORT).show();
        }
    }

    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth){
        //Redimensionamos
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }

    private void saveImageToGallery(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/ImagenesSeguimiento");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String imageName = "Image-" + n + ".jpg";
        File file = new File (myDir, imageName);




        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            String resizeCoolerImagePath = file.getAbsolutePath();
            out.flush();
            out.close();

            Toast.makeText(getApplicationContext(),"Tu foto se ha guardado exitosamente",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Hubo un error",Toast.LENGTH_SHORT).show();
        }

        if(descision==0) {
            fileimagen = file;

            imageView.setVisibility(View.VISIBLE);
            checkBox1.setVisibility(View.VISIBLE);
        } else if (descision==1){
            fileimagen2 = file;

            imageView2.setVisibility(View.VISIBLE);
            checkBox2.setVisibility(View.VISIBLE);
        } else if (descision==2){
            fileimagen3 = file;

            imageView3.setVisibility(View.VISIBLE);
            checkBox3.setVisibility(View.VISIBLE);
        } else if (descision==3){
            fileimagen4 = file;

            imageView4.setVisibility(View.VISIBLE);
            checkBox4.setVisibility(View.VISIBLE);
        } else if (descision==4){
            fileimagen5 = file;

            imageView5.setVisibility(View.VISIBLE);
            checkBox5.setVisibility(View.VISIBLE);
        }else if (descision==5){
            fileimagen6 = file;

            imageView6.setVisibility(View.VISIBLE);
            checkBox6.setVisibility(View.VISIBLE);
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    boolean opcion = true;

                    if (menuItem.getItemId()==R.id.itemAntes) {
                        if (cont1 > 0) {
                            StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
                            opcion = false;
                            menuItem.setEnabled(false);
                        } else {

                            StyleableToast.makeText(getApplicationContext(), "Seccion disponible", Toast.LENGTH_SHORT, R.style.doneToast).show();
                            estado = "before";
                            txtEstado.setText("Antes del Evento");

                        }
                    }

                    if (menuItem.getItemId()==R.id.itemDurante){
                        if(cont2>0) {
                            StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
                            opcion = false;
                            menuItem.setEnabled(false);
                        }else {
                            if (cont1>0) {

                                StyleableToast.makeText(getApplicationContext(), "Seccion disponible", Toast.LENGTH_SHORT, R.style.doneToast).show();
                                estado = "during";
                                txtEstado.setText("Durante el Evento");
                            }else{
                                opcion = false;
                                StyleableToast.makeText(getApplicationContext(), "Te faltan secciones por realizar", Toast.LENGTH_SHORT, R.style.warningToast).show();
                            }

                        }
                    }

                    if (menuItem.getItemId()==R.id.itemDespues){
                        if(cont3>0) {
                            StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
                            opcion = false;
                        }else {
                            if (cont1>0 && cont2>0) {

                                StyleableToast.makeText(getApplicationContext(), "Seccion disponible", Toast.LENGTH_SHORT, R.style.doneToast).show();
                                estado = "after";
                                txtEstado.setText("Después del Evento");
                            }else{
                                opcion = false;
                                StyleableToast.makeText(getApplicationContext(), "Te faltan secciones por realizar", Toast.LENGTH_SHORT, R.style.warningToast).show();
                            }
                        }
                    }
                   /* switch (menuItem.getItemId()){
                        case R.id.itemAntes:
                            estado="before";
                            txtEstado.setText("Antes del Evento");
                            //Toast.makeText(getApplicationContext(),"Estado = " +estado, Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.itemDurante:
                            estado="during";
                            txtEstado.setText("Durante el Evento");
                           // Toast.makeText(getApplicationContext(),"Estado = " +estado, Toast.LENGTH_SHORT).show();
                        break;

                        case R.id.itemDespues:
                            estado="after";
                            txtEstado.setText("Después del Evento");
                            //Toast.makeText(getApplicationContext(),"Estado = " +estado, Toast.LENGTH_SHORT).show();
                            break;
                    }*/

                    return opcion;
                }
            };

    private void recibirDatos() {
        Bundle extras = getIntent().getExtras();
        idevent = extras.getString("idEvento");
        nameEvent = extras.getString("nameEvent");
        actividad = extras.getString("actividad");
        user_id = extras.getString("user_id");
        trabajador= extras.getString("trabajador");
        start= extras.getString("start");
        end= extras.getString("end");
        idactivity= extras.getString("idactivity");
        description= extras.getString("description");
        tools = extras.getStringArrayList("tools");
        deleted = extras.getBoolean("deleted");
        //active=false;



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.supervision, menu);
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

        if (id == R.id.nav_perfil) {
            Toast.makeText(getApplicationContext(),"Aún en proceso",Toast.LENGTH_SHORT).show();
        }else if (id == R.id.nav_acty) {
            Toast.makeText(getApplicationContext(),"Aún en proceso",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_event) {
            mifragment = new EventosFragment();
            FragmentoSeleccionado=true;

           /*Intent intent= new Intent (Main2Activity.this, EventosActivity.class);
            startActivity(intent);]*/
            //finish();

        } else if (id == R.id.nav_ext) {
            Intent intent= new Intent (this, ExtraActivity.class);
            intent.putExtra("idEvento",idevent);
            intent.putExtra("nameEvent",nameEvent);
            //intent.putExtra("Lat",Lat);
            //intent.putExtra("Lng",Lng);
            startActivity(intent);

        }else if(id == R.id.nav_conf) {
            Toast.makeText(getApplicationContext(),"Aún en proceso",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_signOut) {
            cerrarSesion();
        }


        if(FragmentoSeleccionado==true){
            ocultarItems();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_supervision, mifragment)
                    .commit();

            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void cerrarSesion(){


        mAuth.signOut();
        Intent i= new Intent(this, Login.class);
        startActivity(i);
        finish();
    }

    private void ocultarItems(){

        txtEstado.setVisibility(View.INVISIBLE);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        edObserv.setVisibility(View.INVISIBLE);
        btnEnviar.setVisibility(View.INVISIBLE);

        btnFoto.setVisibility(View.INVISIBLE);
        btnFoto2.setVisibility(View.INVISIBLE);
        btnFoto3.setVisibility(View.INVISIBLE);
        btnFoto4.setVisibility(View.INVISIBLE);
        btnFoto5.setVisibility(View.INVISIBLE);

        btnBorrar.setVisibility(View.INVISIBLE);
        btnBorrar2.setVisibility(View.INVISIBLE);
        btnBorrar3.setVisibility(View.INVISIBLE);
        btnBorrar4.setVisibility(View.INVISIBLE);
        btnBorrar5.setVisibility(View.INVISIBLE);

        btnBorrarArchivo.setVisibility(View.INVISIBLE);

        imageView.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
        imageView3.setVisibility(View.INVISIBLE);
        imageView4.setVisibility(View.INVISIBLE);
        imageView5.setVisibility(View.INVISIBLE);

        btnArchivo.setVisibility(View.INVISIBLE);


    }
}

