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

import com.acadep.acadepsistemas.rso.Fragmentos.ActivitysFragment;
import com.acadep.acadepsistemas.rso.Fragmentos.EventosFragment;
//import com.example.acadepsistemas.seguimiento.Manifest;
import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Configuration;
import com.acadep.acadepsistemas.rso.model.Data;
import com.acadep.acadepsistemas.rso.model.Evento;
import com.acadep.acadepsistemas.rso.model.Extra;
import com.acadep.acadepsistemas.rso.model.Files;
import com.acadep.acadepsistemas.rso.model.Foto;
import com.acadep.acadepsistemas.rso.model.Ref_event;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;


import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.muddzdev.styleabletoast.StyleableToast;

import org.joda.time.DateTime;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class SupervisionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String CARPETA_PRINCIPAL = "DCIM/";
    private static final String CARPETA_IMAGEN = "Camera";
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;

    static File [] FileImagenArray = new File[6];

    static File fileimagen = null;
    static File fileimagen2 = null;
    static File fileimagen3 = null;
    static File fileimagen4 = null;
    static File fileimagen5 = null;
    static File fileimagen6 = null;

    static File nula = new File("/ada/");


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

    TextView txtFecha, txtHora;

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

    static int contImg=0;

    FloatingTextButton btnEnviar;
    private FloatingTextButton btnFoto;
    private FloatingTextButton btnFoto2;
    private FloatingTextButton btnFoto3;
    private FloatingTextButton btnFoto4;
    private FloatingTextButton btnFoto5;



    ListView listView;


    static int conteo = 0;
    static int descision= 0;

    static String u;

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
    static String title;
    static  List<String> tools;

    static Ref_event ref_event = new Ref_event();

    static String Observation;

    static int percentage;

    static boolean deleted;
    static boolean active;

    EditText edObserv;
    EditText edpercentage;
    TextView txtAvance;

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
    private static ImageView imageView7;
    private static ImageView imageView8;
    private static ImageView imageView9;
    private static ImageView imageView10;
    private static ImageView imageView11;
    private static ImageView imageView12;
    private static ImageView imageView13;
    private static ImageView imageView14;
    private static ImageView imageView15;
    private static ImageView imageView16;
    private static ImageView imageView17;
    private static ImageView imageView18;
    private static ImageView imageView19;
    private static ImageView imageView20;
    private static ImageView imageView21;
    private static ImageView imageView22;
    private static ImageView imageView23;
    private static ImageView imageView24;
    private static ImageView imageView25;
    private static ImageView imageView26;
    private static ImageView imageView27;
    private static ImageView imageView28;
    private static ImageView imageView29;
    private static ImageView imageView30;

    private static ImageView noImage;

    FloatingTextButton btnBorrar;
    FloatingTextButton btnBorrar2;
    FloatingTextButton btnBorrar3;
    FloatingTextButton btnBorrar4;
    FloatingTextButton btnBorrar5;

    FloatingTextButton btnBorrarArchivo;

    static int max_photos;
    static int min_photos;


    static List<String> Foto =  new ArrayList<>();
    static List<String> Foto2 =  new ArrayList<>();
    static List<String> Foto3 =  new ArrayList<>();
    static List<String> Foto4 =  new ArrayList<>();
    static List<String> Foto5 =  new ArrayList<>();



//Subir archivo

    FloatingTextButton btnArchivo;

    static Uri [] ArchivosUrisArray;
    static Uri UriNula = Uri.parse("1");

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

    //static ImageView[] valores = new ImageView[]{imageView, imageView2, imageView3, imageView4, imageView5};
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


    private static List<Foto> multimedia = new ArrayList<>();
    private static List<Files> files = new ArrayList<>();

    private static com.acadep.acadepsistemas.rso.model.Foto PerFotoArray[] = new Foto[50];
    private static Files PerFilesArray[] = new Files[10];

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
    int terminado = 1;

    static String created_at;

    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    CheckBox checkBox5;
    CheckBox checkBox6;
    CheckBox checkBox7;
    CheckBox checkBox8;
    CheckBox checkBox9;
    CheckBox checkBox10;
    CheckBox checkBox11;
    CheckBox checkBox12;
    CheckBox checkBox13;
    CheckBox checkBox14;
    CheckBox checkBox15;
    CheckBox checkBox16;
    CheckBox checkBox17;
    CheckBox checkBox18;
    CheckBox checkBox19;
    CheckBox checkBox20;
    CheckBox checkBox21;
    CheckBox checkBox22;
    CheckBox checkBox23;
    CheckBox checkBox24;
    CheckBox checkBox25;
    CheckBox checkBox26;
    CheckBox checkBox27;
    CheckBox checkBox28;
    CheckBox checkBox29;
    CheckBox checkBox30;
    LinearLayout Lfotos1, Lfotos2;


    static String header;
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervision);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        today.setToNow();

        //init();

        // Inializacion de variables

        ChequeoConfiguration();
        inicializacionVariables();


        //PerFotoArray[] = new Foto(context)

        cont1 = 0;
        cont2 = 0;
        cont3 = 0;

    // Inializacion de variables

        recibirDatos();


        final Evento nEvent = new Evento();

        nEvent.setActive(false);
        nEvent.setType_activity(actividad);
        nEvent.setTitle(nameEvent);
        //nEvent.setEnd(end);

        nEvent.setId(idevent);
        //nEvent.setStart(start);
        nEvent.setUser_id(user_id);
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
        edpercentage = (EditText) findViewById(R.id.edit_porcentage);
        txtAvance = (TextView) findViewById(R.id.txtAvance);

        btnEnviar = (FloatingTextButton) findViewById(R.id.btnEnviar);

        btnArchivo = (FloatingTextButton) findViewById(R.id.btnArchivo);

        btnFoto = (FloatingTextButton) findViewById(R.id.btnFoto);
//
        btnBorrar = (FloatingTextButton) findViewById(R.id.btnBorrar);


        checkBox1 = (CheckBox) findViewById(R.id.Check1);
        checkBox2 = (CheckBox) findViewById(R.id.Check2);
        checkBox3 = (CheckBox) findViewById(R.id.Check3);
        checkBox4 = (CheckBox) findViewById(R.id.Check4);
        checkBox5 = (CheckBox) findViewById(R.id.Check5);
        checkBox6 = (CheckBox) findViewById(R.id.Check6);
        checkBox7 = (CheckBox) findViewById(R.id.Check7);
        checkBox8 = (CheckBox) findViewById(R.id.Check8);
        checkBox9 = (CheckBox) findViewById(R.id.Check9);
        checkBox10 = (CheckBox) findViewById(R.id.Check10);
        checkBox11 = (CheckBox) findViewById(R.id.Check11);
        checkBox12 = (CheckBox) findViewById(R.id.Check12);
        checkBox13 = (CheckBox) findViewById(R.id.Check13);
        checkBox14 = (CheckBox) findViewById(R.id.Check14);
        checkBox15 = (CheckBox) findViewById(R.id.Check15);
        checkBox16 = (CheckBox) findViewById(R.id.Check16);
        checkBox17 = (CheckBox) findViewById(R.id.Check17);
        checkBox18 = (CheckBox) findViewById(R.id.Check18);
        checkBox19 = (CheckBox) findViewById(R.id.Check19);
        checkBox20 = (CheckBox) findViewById(R.id.Check20);
        checkBox21 = (CheckBox) findViewById(R.id.Check21);
        checkBox22 = (CheckBox) findViewById(R.id.Check22);
        checkBox23 = (CheckBox) findViewById(R.id.Check23);
        checkBox24 = (CheckBox) findViewById(R.id.Check24);
        checkBox25 = (CheckBox) findViewById(R.id.Check25);
        checkBox26 = (CheckBox) findViewById(R.id.Check26);
        checkBox27 = (CheckBox) findViewById(R.id.Check27);
        checkBox28 = (CheckBox) findViewById(R.id.Check28);
        checkBox29 = (CheckBox) findViewById(R.id.Check29);
        checkBox30 = (CheckBox) findViewById(R.id.Check30);


        btnBorrarArchivo = (FloatingTextButton) findViewById(R.id.btnBorrarArchivo);


        imageView = (ImageView) findViewById(R.id.imgView);
        imageView2 = (ImageView) findViewById(R.id.imgView2);
        imageView3 = (ImageView) findViewById(R.id.imgView3);
        imageView4 = (ImageView) findViewById(R.id.imgView4);
        imageView5 = (ImageView) findViewById(R.id.imgView5);
        imageView6 = (ImageView) findViewById(R.id.imgView6);
        imageView7 = (ImageView) findViewById(R.id.imgView7);
        imageView8 = (ImageView) findViewById(R.id.imgView8);
        imageView9 = (ImageView) findViewById(R.id.imgView9);
        imageView10 = (ImageView) findViewById(R.id.imgView10);
        imageView11 = (ImageView) findViewById(R.id.imgView11);
        imageView12 = (ImageView) findViewById(R.id.imgView12);
        imageView13 = (ImageView) findViewById(R.id.imgView13);
        imageView14 = (ImageView) findViewById(R.id.imgView14);
        imageView15 = (ImageView) findViewById(R.id.imgView15);
        imageView16 = (ImageView) findViewById(R.id.imgView16);
        imageView17 = (ImageView) findViewById(R.id.imgView17);
        imageView18 = (ImageView) findViewById(R.id.imgView18);
        imageView19 = (ImageView) findViewById(R.id.imgView19);
        imageView20 = (ImageView) findViewById(R.id.imgView20);
        imageView21 = (ImageView) findViewById(R.id.imgView21);
        imageView22 = (ImageView) findViewById(R.id.imgView22);
        imageView23 = (ImageView) findViewById(R.id.imgView23);
        imageView24 = (ImageView) findViewById(R.id.imgView24);
        imageView25 = (ImageView) findViewById(R.id.imgView25);
        imageView26 = (ImageView) findViewById(R.id.imgView26);
        imageView27 = (ImageView) findViewById(R.id.imgView27);
        imageView28 = (ImageView) findViewById(R.id.imgView28);
        imageView29 = (ImageView) findViewById(R.id.imgView29);
        imageView30 = (ImageView) findViewById(R.id.imgView30);



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

//        txtFecha  = (TextView) findViewById(R.id.txtFecha);
//        txtHora  = (TextView) findViewById(R.id.txtHora);

//        txtFecha.setVisibility(View.INVISIBLE);
//        txtHora.setVisibility(View.INVISIBLE);

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

        edpercentage.setText(""+percentage);
        ChequeoDeVariables();
        /**/




        btnArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (estado.equals("before") && cont1<1 || estado.equals("during") && cont2<1 || estado.equals("after") && cont3<1) {
                    if (ContextCompat.checkSelfPermission(SupervisionActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                        GuardarInformacionArchivos();
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

                BorrarImagenesCheckBoxs();


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
                        locationStart();

                        GuardarInformacionImagenes();
                        takePhoto();

                    }
                }
            }
        });



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

                    Observation = edObserv.getText().toString();
                    if(!Observation.equals("")) {
                        if (contImg>min_photos) {
                            if (contImg>max_photos){
                                StyleableToast.makeText(getApplicationContext(), "El maximo de fotos es " + max_photos + "usted ha superado esa cantidad por " + (contImg-max_photos), Toast.LENGTH_SHORT, R.style.warningToast).show();
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                builder.setTitle("Confirmación");
                                builder.setMessage("¿Está seguro?");
                                StyleableToast.makeText(getApplicationContext(), "Una vez realizada, esta acción no se puede revertir", Toast.LENGTH_SHORT, R.style.warningToast).show();
                                // builder.setCancelable(false);
                                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        ref_event.setId(idevent);
                                        ref_event.setName(title);


                                        if ((estado).equals("before")) {


                                            boolean before = true;


                                            header = "before";
                                            UUID uuid = UUID.randomUUID();
                                            u = "" + uuid;

                                            uploadAllFiles();
                                            uploadAllImages();


                                            BDFireStore.collection("events").document(idevent).update("status", 2);

                                            BDFireStore.collection("events").document(idevent).update("percentage", 1);
                                            StyleableToast.makeText(getApplicationContext(), "Datos ingresados", Toast.LENGTH_LONG, R.style.sucessToast).show();
                                            //Toast.makeText(getApplicationContext(), "Datos ingresados", Toast.LENGTH_SHORT).show();


                                            edObserv.setText("");


                                        }

                                        if ((estado).equals("during")) {


                                            if (percentage <= Integer.parseInt(String.valueOf(edpercentage.getText()))) {
                                                if (Integer.parseInt(String.valueOf(edpercentage.getText())) <= 100) {

                                                    percentage = Integer.parseInt(String.valueOf(edpercentage.getText()));

                                                    boolean during = true;

                                                    header = "before";
                                                    UUID uuid = UUID.randomUUID();
                                                    u = "" + uuid;

                                                    StyleableToast.makeText(getApplicationContext(), "Datos ingresados", Toast.LENGTH_LONG, R.style.sucessToast).show();
                                                    //Toast.makeText(getApplicationContext(), "Datos ingresados", Toast.LENGTH_SHORT).show();


                                                    uploadAllFiles();

                                                    uploadAllImages();

                                                    terminado = 2;

                                                    BDFireStore.collection("events").document(idevent).update("percentage", percentage);

                                                    edObserv.setText("");
                                                } else {
                                                    StyleableToast.makeText(getApplicationContext(), "El porcentage no puede ser mayor a 100%", Toast.LENGTH_LONG, R.style.warningToastMiddle).show();
                                                }
                                            } else {
                                                StyleableToast.makeText(getApplicationContext(), "El porcentage no puede ser menor al anterior", Toast.LENGTH_LONG, R.style.warningToastMiddle).show();
                                            }


                                        }

                                        if ((estado).equals("after")) {

                                            boolean after = true;

                                            header = "before";
                                            UUID uuid = UUID.randomUUID();
                                            u = "" + uuid;

                                            StyleableToast.makeText(getApplicationContext(), "Datos ingresados", Toast.LENGTH_LONG, R.style.sucessToast).show();
                                            // Toast.makeText(getApplicationContext(), "Datos ingresados", Toast.LENGTH_SHORT).show();

                                            edObserv.setText("");


                                            uploadAllFiles();
                                            uploadAllImages();

                                            BDFireStore.collection("events").document(idevent).update("percentage", percentage);


                                            if (percentage == 100) {

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


                                    }
                                });


                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                builder.create().show();
                                ChequeoDeVariables();
                            }
                        }else{
                            StyleableToast.makeText(getApplicationContext(), "El número mínimo de fotos es " + min_photos, Toast.LENGTH_SHORT, R.style.warningToastMiddle).show();
                        }
                    }else{
                        StyleableToast.makeText(getApplicationContext(), "El texto es necesario para poder enviar", Toast.LENGTH_SHORT, R.style.warningToastMiddle).show();
                    }

                }

            }

        });



        // -------------------Image view --------------------- Zoom ---------------------
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SupervisionActivity.this);
//                View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
//                PhotoView photoView = mView.findViewById(R.id.imageView);
//                photoView.setImageURI(Uri.fromFile(fileimagen));
//                mBuilder.setView(mView);
//                AlertDialog mDialog = mBuilder.create();
//                mDialog.show();
//            }
//        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void ChequeoConfiguration() {

        BDFireStore
                .collection("configuration")
                .document("global")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Configuration configuration = documentSnapshot.toObject(Configuration.class);

                max_photos = configuration.getMax_photos();
                min_photos = configuration.getMin_photos();
            }
        });

    }

    private void BorrarImagenesCheckBoxs() {

        if(checkBox1.isChecked() || checkBox2.isChecked() || checkBox3.isChecked() || checkBox4.isChecked() || checkBox5.isChecked() || checkBox6.isChecked()) {


            if (checkBox1.isChecked()) {

                imageView.setImageResource(R.drawable.empty_image);
                checkBox1.setChecked(false);
                FileImagenArray[0]=nula;


                imageView.setVisibility(View.INVISIBLE);
                checkBox1.setVisibility(View.INVISIBLE);
            }

            if (checkBox2.isChecked()) {
                imageView2.setImageResource(R.drawable.empty_image);
                checkBox2.setChecked(false);
                FileImagenArray[1]=nula;

                imageView2.setVisibility(View.INVISIBLE);
                checkBox2.setVisibility(View.INVISIBLE);
            }

            if (checkBox3.isChecked()) {
                imageView3.setImageResource(R.drawable.empty_image);
                checkBox3.setChecked(false);
                FileImagenArray[2]=nula;

                imageView3.setVisibility(View.INVISIBLE);
                checkBox3.setVisibility(View.INVISIBLE);
            }

            if (checkBox4.isChecked()) {
                imageView4.setImageResource(R.drawable.empty_image);
                checkBox4.setChecked(false);
                FileImagenArray[3]=nula;

                imageView4.setVisibility(View.INVISIBLE);
                checkBox4.setVisibility(View.INVISIBLE);
            }

            if (checkBox5.isChecked()) {
                imageView5.setImageResource(R.drawable.empty_image);
                checkBox5.setChecked(false);
                FileImagenArray[4]=nula;

                imageView5.setVisibility(View.INVISIBLE);
                checkBox5.setVisibility(View.INVISIBLE);
            }

            if (checkBox6.isChecked()) {
                imageView6.setImageResource(R.drawable.empty_image);
                checkBox6.setChecked(false);
                FileImagenArray[5]=nula;

                imageView6.setVisibility(View.INVISIBLE);
                checkBox6.setVisibility(View.INVISIBLE);
            }
        }else{
            StyleableToast.makeText(getApplicationContext(), "No ha seleccionado ninguna foto", Toast.LENGTH_SHORT, R.style.warningToast).show();
        }
    }

    private void GuardarInformacionArchivos() {
        created_at_funct();
        PerFilesArray[contUris].setCreated_at(created_at);

    }

    private void inicializacionVariables() {

        files = new ArrayList<>();
        contUris=0;
        for (int x=0; x<10;x++) {
            PerFilesArray[x] = new Files();
        }

        multimedia = new ArrayList<>();
        contImg=0;
        for (int x=0; x<6; x++) {
            PerFotoArray[x] = new Foto();
            FileImagenArray[x] = nula;
        }
    }

    private void uploadAllImages() {

        for(int i=0; i<FileImagenArray.length; i++){
            if(FileImagenArray[i] != nula){
                uploadImageGlobal(FileImagenArray[i], i);
                multimedia.add(PerFotoArray[i]);
            }
        }
    }



    private void created_at_funct() {
        java.util.Date FecAct = new Date();
        Calendar calendar = new GregorianCalendar();
        DateTime dateTime = new DateTime();

//        Fecha =   today.year + "-" + today.month + "-" + today.monthDay;
        Fecha =   calendar.get(Calendar.YEAR) + "-" + dateTime.getMonthOfYear() + "-" + calendar.get(Calendar.DAY_OF_MONTH);



        Hora = today.hour +":" + calendar.get(Calendar.MINUTE);

        created_at = Fecha + "T" +Hora;

    }

    private void GuardarInformacionImagenes() {

        locationStart();
        created_at_funct();

        datatime.setDate(Fecha);
        datatime.setTime(Hora);

        locationStart();
        ubication.setLat(Lat);
        ubication.setLng(Lng);


        for(int i=0; i<6; i++){
            if(FileImagenArray[i] == nula){
                PerFotoArray[i].setCreated_at(created_at);
                PerFotoArray[i].setUbication(ubication);
                PerFotoArray[i].setType("Imagen");
                break;
            }
        }

//
//        if(fileimagen==null) {
//            PerFoto1.setCreated_at(created_at);
//            PerFoto1.setUbicacion(ubication);
//            PerFoto1.setType("Imagen");
//        }else
//
//        if (fileimagen2==null){
//            PerFoto2.setCreated_at(created_at);
//            PerFoto2.setUbicacion(ubication);
//            PerFoto2.setType("Imagen");
//        } else
//
//        if (fileimagen3==null){
//            PerFoto3.setCreated_at(created_at);
//            PerFoto3.setUbicacion(ubication);
//            PerFoto3.setType("Imagen");
//        }else
//
//        if (fileimagen4==null){
//            PerFoto4.setCreated_at(created_at);
//            PerFoto4.setUbicacion(ubication);
//            PerFoto4.setType("Imagen");
//        }else
//
//        if (fileimagen5==null){
//            PerFoto5.setCreated_at(created_at);
//            PerFoto5.setUbicacion(ubication);
//            PerFoto5.setType("Imagen");
//        }else
//
//        if (fileimagen6==null){
//            PerFoto6.setCreated_at(created_at);
//            PerFoto6.setUbicacion(ubication);
//            PerFoto6.setType("Imagen");
//        }
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


//                        for (int x=0; x<=9; x++){
//                            if(ArchivosUrisArray[x]!= UriNula && contUris <= 10){
//                                ArchivosUrisArray[x]
//                            }
//                        }


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
//        uploadfileGlobal(ArchivoUri);

        if (ArchivoUri != null) {
            uploadfileGlobal(ArchivoUri, 0);
            files.add(PerFilesArray[0]);
            ArchivoUri=null;


        }

        if(ArchivoUri2 != null) {
            uploadfileGlobal(ArchivoUri2, 1);
            files.add(PerFilesArray[1]);
            ArchivoUri2=null;
        }

        if(ArchivoUri3 != null) {
            uploadfileGlobal(ArchivoUri3, 2);
            files.add(PerFilesArray[2]);
            ArchivoUri3=null;

        } if(ArchivoUri4 != null) {

            uploadfileGlobal(ArchivoUri4, 3);
            files.add(PerFilesArray[3]);
            ArchivoUri4=null;

        }

        if(ArchivoUri5 != null ) {
            uploadfileGlobal(ArchivoUri5, 4);
            files.add(PerFilesArray[4]);
            ArchivoUri5=null;
        }

        if(ArchivoUri6 != null) {
            uploadfileGlobal(ArchivoUri6, 5);
            files.add(PerFilesArray[5]);
            ArchivoUri6=null;
        }

        if(ArchivoUri7 != null) {

            uploadfileGlobal(ArchivoUri7, 6);
            files.add(PerFilesArray[6]);
            ArchivoUri7=null;

        }

        if(ArchivoUri8 != null) {

            uploadfileGlobal(ArchivoUri8, 7);
            files.add(PerFilesArray[7]);
            ArchivoUri8=null;

        }

        if(ArchivoUri9 != null) {

            uploadfileGlobal(ArchivoUri9, 8);
            files.add(PerFilesArray[8]);
            ArchivoUri9=null;

        }

        if(ArchivoUri10 != null) {

            uploadfileGlobal(ArchivoUri10, 9);
            files.add(PerFilesArray[9]);
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
                        PerFilesArray[contUris].setType("PDF");
                        selectPDF();
                    } else if (opciones[i].equals("Elegir Docx")) {
                        PerFilesArray[contUris].setType("Docx");
                        selectDocx();
                    } else if (opciones[i].equals("Elegir Video")) {
                        PerFilesArray[contUris].setType("video");
                        selectVideo();
                    } else if (opciones[i].equals("Elegir Audio")) {
                        PerFilesArray[contUris].setType("Audio");
                        selectAudio();
                    } else {
                        dialogInterface.dismiss();
                    }
                }
            });
            builder.show();
        }
    }

    private void ChequeoDeVariables() {


        if(percentage==0){
            txtAvance.setVisibility(View.INVISIBLE);
            edpercentage.setVisibility(View.INVISIBLE);
        }

        if(percentage>=1 && percentage<=99){
            txtAvance.setVisibility(View.VISIBLE);
            edpercentage.setVisibility(View.VISIBLE);

            bottomNavigationView.getSelectedItemId();
            bottomNavigationView.setEnabled(false);
            bottomNavigationView.setSelectedItemId(R.id.itemDurante);
            estado = "during";
            txtEstado.setText("Durante el Evento");
        }

        if(percentage==100){
            txtAvance.setVisibility(View.INVISIBLE);
            edpercentage.setVisibility(View.INVISIBLE);

            bottomNavigationView.getSelectedItemId();
            bottomNavigationView.setEnabled(false);
            bottomNavigationView.setSelectedItemId(R.id.itemDespues);
            estado = "after";
            txtEstado.setText("Después del Evento");
        }








//        BDFireStore.collection("events")
//                .document(idevent)
//                .collection("observation")
//                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                                                @Override
//                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//                                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
//
//                                                        Data check = documentSnapshot.toObject(Data.class);
//                                                        Data2 check2 = documentSnapshot.toObject(Data2.class);
//                                                        Data3 check3 = documentSnapshot.toObject(Data3.class);
//
//                                                        if(check.isBefore()==true) {
//                                                            cont1++;
//                                                            bottomNavigationView.getSelectedItemId();
//                                                            bottomNavigationView.setEnabled(false);
//                                                            bottomNavigationView.setSelectedItemId(R.id.itemDurante);
//                                                            estado = "during";
//                                                            txtEstado.setText("Durante el Evento");
//                                                        }
//
//                                                        if(check2.isDuring()==true) {
//                                                            cont2++;
//                                                            bottomNavigationView.getSelectedItemId();
//                                                            bottomNavigationView.setEnabled(false);
//                                                            bottomNavigationView.setSelectedItemId(R.id.itemDespues);
//                                                            estado = "after";
//                                                            txtEstado.setText("Después del Evento");
//                                                        }
//
//                                                        if(check3.isAfter()==true) {
//                                                            cont3++;
//                                                            StyleableToast.makeText(getApplicationContext(), "Evento terminado", Toast.LENGTH_SHORT, R.style.doneToast).show();
//
//                                                        }
//
//                                                    }
//
//                                                }
//                                            });


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

        created_at_funct();

        //txtFecha.setText(created_at.substring(0, 9));
        //txtHora.setText(created_at.substring(10, 15));
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
            //mensaje1.setText(Text);
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

        public void setMainActivity() {
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

        multimedia = new ArrayList<>();
        contImg=0;
        for (int x=0; x<6; x++) {
            PerFotoArray[x] = new Foto();
            FileImagenArray[x] = nula;
        }

        imageView.setImageResource(R.drawable.empty_image);
        checkBox1.setChecked(false);

        imageView2.setImageResource(R.drawable.empty_image);
        checkBox2.setChecked(false);


        imageView3.setImageResource(R.drawable.empty_image);
        checkBox3.setChecked(false);

        imageView4.setImageResource(R.drawable.empty_image);
        checkBox4.setChecked(false);

        imageView5.setImageResource(R.drawable.empty_image);
        checkBox5.setChecked(false);

        imageView6.setImageResource(R.drawable.empty_image);
        checkBox6.setChecked(false);

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
        if((estado).equals("before") && percentage>=1){
            StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
        }else if ((estado).equals("during") && percentage>99){
            StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
        }else {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAPTURE_PHOTO);
        }
    }

    private void uploadImageGlobal(File fileimagenpos, final int x) {
        if (fileimagenpos != null) {

            final ProgressDialog progressDialog = new ProgressDialog(SupervisionActivity.this);
            progressDialog.setTitle("Subiendo....");

            final StorageReference ref = storageReference.child("images").child("evidence").child("Img" + UUID.randomUUID().toString());
            // StorageReference ref = storageReference.child("images/"+UUID.randomUUID().toString());

            final UploadTask uploadTask = ref.putFile(Uri.fromFile(fileimagenpos));


            ref.putFile(Uri.fromFile(fileimagenpos)).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    //PerFotoArray[x].setSrc(ref.getDownloadUrl().toString());
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        PerFotoArray[x].setSrc(downloadUri.toString());

                        Subirdatos();

                        if(x == (contImg-1) && contUris==0){

                            BorrarImagenes();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void Subirdatos() {


        created_at_funct();
        Data data = new Data(created_at, Observation, header,ref_event, Lat, Lng, multimedia, files);
        BDFireStore.collection("evidence").document(u).set(data, SetOptions.merge());

    }

    private void uploadfileGlobal(Uri ArchivoUri, final int i) {
        progressDialog= new ProgressDialog(SupervisionActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo archivo...");
        progressDialog.setProgress(0);
        progressDialog.show();

        int Tpath = ArchivoUri.getPath().length();
        String nombre = ArchivoUri.getPath().substring(9, Tpath);
        PerFilesArray[i].setName(ArchivoUri.getPath());

        final String fileName= "Archivo" + UUID.randomUUID().toString();
        final StorageReference srtreference = storage.getReference();
        srtreference.child("files").child("evidence").child(fileName).putFile(ArchivoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getUploadSessionUri().toString();
                        DatabaseReference refDB = dbRef.getReference();
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Uri downloadUri = task.getResult();
                                    PerFilesArray[i].setSrc(downloadUri.toString());

                                    Subirdatos();



                                    if(i == (contUris-1)){
                                        try {
                                            Thread.sleep(10000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        BorrarImagenes();
                                        BorrarFiles();

                                        progressDialog.setProgress(100);
                                        progressDialog.setMessage("Click para salir...");
                                        StyleableToast.makeText(getApplicationContext(), "Subida de archivos terminada!",Toast.LENGTH_SHORT, R.style.doneToast).show();

                                    }


                                } else {
                                    Toast.makeText(getApplicationContext(), "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

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
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

            }
        });
    }

    private void BorrarFiles() {
        files = new ArrayList<>();
        contUris=0;
        for (int x=0; x<10;x++) {
            PerFilesArray[x] = new Files();
        }
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
                    Bitmap Bitnew = redimensionarImagenMaximo(capturedCoolerBitmap, 1200 , 800);

                    contImg++;
                    seleccionImageView(Bitnew);

                    break;

                default:
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    break;
            }
        }





    }

    private void seleccionImageView(Bitmap bitnew) {
        for(int i=0; i<FileImagenArray.length; i++){
            if(FileImagenArray[i] == nula){
                descision=i;
                saveImageToGallery(bitnew);
                if(i==0){
                    imageView.setImageBitmap(bitnew);
                    break;
                }
                if(i==1){
                    imageView2.setImageBitmap(bitnew);
                    break;
                }
                if(i==2){
                    imageView3.setImageBitmap(bitnew);
                    break;
                }
                if(i==3){
                    imageView4.setImageBitmap(bitnew);
                    break;
                }
                if(i==4){
                    imageView5.setImageBitmap(bitnew);
                    break;
                }
                if(i==5){
                    imageView6.setImageBitmap(bitnew);
                    break;
                }

            }
        }
    }

    private void SelecUri(Intent data) {


//        for (int i=0; i<=9; i++){
//            if(contUris==i && ArchivosUrisArray[i] == UriNula){
//                ArchivosUrisArray[i]=data.getData();
//                StyleableToast.makeText(getApplicationContext(), "Archivo agregado con exito \nPuede subir " + (restUris-contUris) + " más", Toast.LENGTH_LONG, R.style.sucessToast).show();
//                break;
//            }
//        }

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
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            String resizeCoolerImagePath = file.getAbsolutePath();
            out.flush();
            out.close();

            Toast.makeText(getApplicationContext(),"Tu foto se ha guardado exitosamente",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Hubo un error",Toast.LENGTH_SHORT).show();
        }


        if(descision==0) {
            FileImagenArray[0] = file;
            imageView.setVisibility(View.VISIBLE);
            checkBox1.setVisibility(View.VISIBLE);
        } else if (descision==1){
            FileImagenArray[1] = file;
            imageView2.setVisibility(View.VISIBLE);
            checkBox2.setVisibility(View.VISIBLE);
        } else if (descision==2){
            FileImagenArray[2] = file;
            imageView3.setVisibility(View.VISIBLE);
            checkBox3.setVisibility(View.VISIBLE);
        } else if (descision==3){
            FileImagenArray[3] = file;
            imageView4.setVisibility(View.VISIBLE);
            checkBox4.setVisibility(View.VISIBLE);
        } else if (descision==4){
            FileImagenArray[4] = file;
            imageView5.setVisibility(View.VISIBLE);
            checkBox5.setVisibility(View.VISIBLE);
        }else if (descision==5){
            FileImagenArray[5] = file;
            imageView6.setVisibility(View.VISIBLE);
            checkBox6.setVisibility(View.VISIBLE);
        }


//        if(descision==0) {
//            fileimagen = file;
//            imageView.setVisibility(View.VISIBLE);
//            checkBox1.setVisibility(View.VISIBLE);
//        } else if (descision==1){
//            fileimagen2 = file;
//            imageView2.setVisibility(View.VISIBLE);
//            checkBox2.setVisibility(View.VISIBLE);
//        } else if (descision==2){
//            fileimagen3 = file;
//            imageView3.setVisibility(View.VISIBLE);
//            checkBox3.setVisibility(View.VISIBLE);
//        } else if (descision==3){
//            fileimagen4 = file;
//            imageView4.setVisibility(View.VISIBLE);
//            checkBox4.setVisibility(View.VISIBLE);
//        } else if (descision==4){
//            fileimagen5 = file;
//            imageView5.setVisibility(View.VISIBLE);
//            checkBox5.setVisibility(View.VISIBLE);
//        }else if (descision==5){
//            fileimagen6 = file;
//            imageView6.setVisibility(View.VISIBLE);
//            checkBox6.setVisibility(View.VISIBLE);
//        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    boolean opcion = true;

                    if (menuItem.getItemId()==R.id.itemAntes) {
                        if (percentage>=1) {
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
                        if(percentage==100) {
                            StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT, R.style.warningToast).show();
                            opcion = false;
                            menuItem.setEnabled(false);
                        }else {
                            if (percentage >= 1 && percentage <= 99) {
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

                            if (percentage == 100) {

                                StyleableToast.makeText(getApplicationContext(), "Seccion disponible", Toast.LENGTH_SHORT, R.style.doneToast).show();
                                estado = "after";
                                txtEstado.setText("Después del Evento");
                            }else{
                                opcion = false;
                                StyleableToast.makeText(getApplicationContext(), "Te faltan secciones por realizar", Toast.LENGTH_SHORT, R.style.warningToast).show();
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
        title = extras.getString("title");
        idactivity= extras.getString("idactivity");
        description= extras.getString("description");
        tools = extras.getStringArrayList("tools");
        deleted = extras.getBoolean("deleted");
        percentage = extras.getInt("percentage");
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
            startActivity(new Intent(this, com.acadep.acadepsistemas.rso.Clases.RecyclerTest.class));
        } else if (id == R.id.nav_event) {
            mifragment = new EventosFragment();
            FragmentoSeleccionado=true;

           /*Intent intent= new Intent (Main2Activity.this, EventosActivity.class);
            startActivity(intent);]*/
            //finish();

        } else if (id == R.id.nav_ext) {

            mifragment = new ActivitysFragment();
            FragmentoSeleccionado=true;

//            Intent intent= new Intent (this, ExtraActivity.class);
//            intent.putExtra("idEvento",idevent);
//            intent.putExtra("nameEvent",nameEvent);
//            //intent.putExtra("Lat",Lat);
//            //intent.putExtra("Lng",Lng);
//            startActivity(intent);

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

        btnBorrar.setVisibility(View.INVISIBLE);


        btnBorrarArchivo.setVisibility(View.INVISIBLE);

        imageView.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
        imageView3.setVisibility(View.INVISIBLE);
        imageView4.setVisibility(View.INVISIBLE);
        imageView5.setVisibility(View.INVISIBLE);

        btnArchivo.setVisibility(View.INVISIBLE);


    }
}

