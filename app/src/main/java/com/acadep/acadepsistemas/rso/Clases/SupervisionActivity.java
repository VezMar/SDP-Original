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
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
//import com.github.clans.fab.FloatingActionButton
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.DisplayMetrics;
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
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.acadep.acadepsistemas.rso.Adapter.ArchivosAdapter;
import com.acadep.acadepsistemas.rso.Adapter.RecyclerViewAdapter;
//import com.example.acadepsistemas.seguimiento.Manifest;
import com.acadep.acadepsistemas.rso.Fragmentos.ProjectFragment;
import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Event_types;
import com.acadep.acadepsistemas.rso.model.Configuration;
import com.acadep.acadepsistemas.rso.model.Data;
import com.acadep.acadepsistemas.rso.model.Evento;
import com.acadep.acadepsistemas.rso.model.Files;
import com.acadep.acadepsistemas.rso.model.Foto;
import com.acadep.acadepsistemas.rso.model.Ref_event;
import com.acadep.acadepsistemas.rso.model.Total;
import com.acadep.acadepsistemas.rso.model.Ubication;
import com.acadep.acadepsistemas.rso.model.User;
import com.acadep.acadepsistemas.rso.model.Usuario;
import com.acadep.acadepsistemas.rso.model.datetime;

//import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.clans.fab.FloatingActionMenu;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;
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
    static final int REQUEST_VIDEO_CAPTURE = 1;
    int TAKE_PHOTO_CODE = 0;
    public static final int MULTIPLE_PERMISSIONS = 10;

    String[] permissions= new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};


    //Varibales X
    static int cont1 = 0;
    static int cont2 = 0;
    static int cont3 = 0;

    static int contImg = 0;

    FloatingTextButton btnEnviar;
    private FloatingTextButton btnFoto;



    ListView listView;


    static int conteo = 0;
    static int descision = 0;

    static String uuids;

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
    static List<String> tools;
    static int ava;
    static int number;
    static String unit;


    static Ref_event ref_event = new Ref_event();

    static String Observation;



    static String deleted;
    static boolean active;

    EditText edObserv;
    EditText edpercentage;
    TextView txtAvance;
    TextView txtTotal;

    static TextView txtEstado;

    boolean statusChange;


    public String rolesUser;
    static String estado = "before";


    //Variables Fotos
    //private Uri filePath;

    FloatingTextButton btnBorrar;
    FloatingTextButton btnArchivo;
    FloatingTextButton btnBorrarArchivo;

    private Switch swtBorrar;

    static int max_photos;
    static int min_photos;

    static String username;


//Subir archivo



    static Uri[] ArchivosUrisArray;
    static Uri UriNula = Uri.parse("1");

    static int contUris = 0;
    static int restUris = 9;

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

    FirebaseFirestore BDFireStore = FirebaseFirestore.getInstance();
    CollectionReference fireStoreReference;


    FirebaseDatabase dbRef;

    StorageReference storageReference;

    //FIRESTORE
    private FirebaseFirestore mFireStore;
    private com.google.firebase.firestore.Query mQuery;
    private com.google.firebase.firestore.Query mQuery2;


    private BottomNavigationView bottomNavigationView;
    private FloatingActionMenu floatingActionsMenu;
    private com.github.clans.fab.FloatingActionButton actionButton_Take_photo;
    private com.github.clans.fab.FloatingActionButton actionButton_Take_video;
    private com.github.clans.fab.FloatingActionButton actionButton_Upload_PDF;
    private com.github.clans.fab.FloatingActionButton actionButton_Upload_Docx;
    private com.github.clans.fab.FloatingActionButton actionButton_Upload_Video;
    private com.github.clans.fab.FloatingActionButton actionButton_Upload_Audio;

    private com.github.clans.fab.FloatingActionButton actionButton_Borrar_Seleccionado;

    private com.github.clans.fab.FloatingActionButton actionButton_Enviar;


    private static List<Foto> multimedia = new ArrayList<>();
    private static List<Files> files = new ArrayList<>();

    private static List<Uri> ArchivosUris = new ArrayList<>();
    private static List<String> Type_Archivo = new ArrayList<>();
    private static List<String> Name_Archivo = new ArrayList<>();
    private ArrayList<Boolean> archivoChecked= new ArrayList<>();


    Files PerFile = new Files();

    //static File [] FileImagenArray = new File[6];

    private static com.acadep.acadepsistemas.rso.model.Foto PerFotoArray[] = new Foto[1001];
    private static Foto PhotoData = new Foto();
//    private static Files PerFilesArray[] = new Files[10];

    private static datetime datatime = new datetime();
    private static Ubication ubication = new Ubication();

    private static Time today = new Time(Time.getCurrentTimezone());
    private static String Fecha;
    private static String Hora;


    String status= "#333";
    int terminado = 1;

    static String created_at;

    //    Imagenes
    private  static ArrayList<Bitmap> mImageBitmap = new ArrayList<>();
    private  static ArrayList<String> mTypeAdapter = new ArrayList<>();
    private  static ArrayList<Boolean> mItemChecked = new ArrayList<>();



    private static List<File> ListImages = new ArrayList<>();
    private static ArrayList<Uri> ListVideos = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayaoutManager;

    private RecyclerView mRecyclerViewFiles;
    private RecyclerView.Adapter mAdapterFiles;
    private RecyclerView.LayoutManager mLayaoutManagerFiles;

    private String filePath;

    //Imagenes


    static String header;
    private String pictureImagePath;
    private String mCurrentPhotoPath;


    int contT=0;
    int contT2=0;


    static List<Event_types> event_types;

    static boolean Tbefore;
    static boolean Tduring;
    static boolean Tafter;

    CheckBox check_NoAplica;
    boolean checked_NoAplica;

    TextView txtCorreo, txtAyuda;


    static boolean during_complete;
    static boolean before_complete;


    private String activity_id;
    private String project_title;
    private String project_id;
    private String activity_title;

    static String Correo;
    private int contEventos=0;
    private int contActivity=0;

    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervision);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Firebase Inicializacion
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dbRef = FirebaseDatabase.getInstance();



        //Instancias
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        //Fin Instancias
        storageReference = storage.getReference();
        //Fin Firebase Inicializacion

        // FireStore
        mFireStore = FirebaseFirestore.getInstance();

        // FireStore


        today.setToNow();


        mensaje1 = (TextView) findViewById(R.id.txtLat);
        mensaje2 = (TextView) findViewById(R.id.txtLng);
        txtAyuda = findViewById(R.id.txtAyuda);
        mensaje1.setVisibility(View.INVISIBLE);
        mensaje2.setVisibility(View.INVISIBLE);



        //init();

        // Inializacion de variables
        recibirDatos();
        ChequeoConfiguration();
        inicializacionVariables();
        initRecyclerView();
        initRecyclerViewFiles();


        ref = FirebaseDatabase.getInstance().getReference().child("Eventos").child(idevent).child("observation");

        refBefore = FirebaseDatabase.getInstance().getReference().child("Eventos").child(idevent).child("observation").child("before");
        refDuring = FirebaseDatabase.getInstance().getReference().child("Eventos").child(idevent).child("observation").child("during");
        refAfter = FirebaseDatabase.getInstance().getReference().child("Eventos").child(idevent).child("observation").child("after");

        //PerFotoArray[] = new Foto(context)

        cont1 = 0;
        cont2 = 0;
        cont3 = 0;

        // Inializacion de variables


        final Evento nEvent = new Evento();

        nEvent.setActive(false);
        nEvent.setType_activity(actividad);
        nEvent.setTitle(nameEvent);
        //nEvent.setEnd(end);

        nEvent.setId(idevent);
        //nEvent.setStart(start);
        nEvent.setUser_id(user_id);
//        nEvent.setDeleted(deleted);
        // nEvent.setTools(tools);

        //Inicializacion de varibales
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.setVisibility(View.INVISIBLE);

        floatingActionsMenu = findViewById(R.id.FloatingActionMenuPrincipal);
//        floatingActionsMenu.setIconAnimated(false);

        actionButton_Take_photo = findViewById(R.id.fab_action_1);
        actionButton_Take_video = findViewById(R.id.fab_action_2);
        actionButton_Upload_PDF = findViewById(R.id.fab_action_3);
        actionButton_Upload_Docx = findViewById(R.id.fab_action_4);
        actionButton_Upload_Video = findViewById(R.id.fab_action_5);
        actionButton_Upload_Audio = findViewById(R.id.fab_action_6);

        actionButton_Borrar_Seleccionado = findViewById(R.id.fab_action_2_1);

        actionButton_Enviar = findViewById(R.id.fab_action_3_1);

        check_NoAplica = findViewById(R.id.check_NoAplica);


        txtEstado = (TextView) findViewById(R.id.txtEstado);



/*
        if(nEvent.getType_activity().equals("auditoria")){
            estado = "before";
            bottomNavigationView.setVisibility(View.INVISIBLE);
        }*/

        edObserv = (EditText) findViewById(R.id.edObserv);
        edpercentage = (EditText) findViewById(R.id.edit_porcentage);
        txtAvance = (TextView) findViewById(R.id.txtAvance);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtTotal.setText("/" + number + " "+ unit);

//        btnEnviar = (FloatingTextButton) findViewById(R.id.btnEnviar);



//        swtBorrar = findViewById(R.id.swtBorrar);

//        swtBorrar.setVisibility(View.INVISIBLE);
//        btnBorrarArchivo.setVisibility(View.INVISIBLE);


        // GPSSSSSSSSSSSSSSSSSSSSS


//        txtFecha  = (TextView) findViewById(R.id.txtFecha);
//        txtHora  = (TextView) findViewById(R.id.txtHora);

//        txtFecha.setVisibility(View.INVISIBLE);
//        txtHora.setVisibility(View.INVISIBLE);

        // GPSSSSSSSSSSSSSSSSSSSSS



        // query = FirebaseDatabase.getInstance().getReference().child("Eventos").child(idevent).child("observation").child();


        //Botones
        if (checkPermissions()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            } else {
                locationStart();
            }
        } else{

        }
//        if(ava==1){
//            edpercentage.setText("0");
//        }else {
//            edpercentage.setText("" + ava);
//        }

        edpercentage.setText("" + ava);
        /**/




//        btnArchivo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (estado.equals("before") && cont1<1 || estado.equals("during") && cont2<1 || estado.equals("after") && cont3<1) {
//                    if (ContextCompat.checkSelfPermission(SupervisionActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//
//                        GuardarInformacionArchivos();
//                        mostrarDialogoOpciones();
//                    } else {
//                        ActivityCompat.requestPermissions(SupervisionActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
//                    }
//                }else{
//                    StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT ).show();
//                }
//            }
//        });
//






        txtAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Ayuda");
                builder.setMessage("El total del avance debe ser igual al total de la tarea para completar esta sección");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setIcon(R.drawable.ic_warning_black_24dp);
                builder.create().show();
            }
        });


        actionButton_Take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto_AltaCalidad();

                floatingActionsMenu.close(true);

            }
        });

        actionButton_Take_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarInformacionVideos();
                takeVideo();

                floatingActionsMenu.close(true);
            }
        });

        actionButton_Upload_PDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarInformacionArchivos();

                PerFile = files.get(0);
                PerFile.setType("PDF");
                PerFile.setSrc("" + contUris);
                files.set(0, PerFile);
                PerFile = new Files();

                Type_Archivo.add(0, "PDF");

                selectPDF();
                floatingActionsMenu.close(true);

            }
        });

        actionButton_Upload_Docx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarInformacionArchivos();

                PerFile = files.get(0);
                PerFile.setType("Docx");
                PerFile.setSrc(""+contUris);
                files.set(0,PerFile);
                PerFile = new Files();

                Type_Archivo.add(0, "Docx");

                selectDocx();

                floatingActionsMenu.close(true);
            }
        });

        actionButton_Upload_Video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarInformacionArchivos();

                PerFile = files.get(0);
                PerFile.setType("Video");
                PerFile.setSrc(""+contUris);
                files.set(0,PerFile);
                PerFile = new Files();

                Type_Archivo.add(0, "Video");

                selectVideo();

                floatingActionsMenu.close(true);
            }
        });

        actionButton_Upload_Audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarInformacionArchivos();

                PerFile = files.get(0);
                PerFile.setType("Audio");
                PerFile.setSrc("" + contUris);
                files.set(0, PerFile);
                PerFile = new Files();

                selectAudio();

                Type_Archivo.add(0, "Audio");


                floatingActionsMenu.close(true);
            }
        });

        actionButton_Borrar_Seleccionado.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                                    builder.setTitle("Confirmación");
                                                    builder.setMessage("¿Está seguro de que desea borrar lo seleccionado?");
//                                                    StyleableToast.makeText(getApplicationContext(), "Una vez realizada, esta acción no se puede revertir", Toast.LENGTH_SHORT ).show();
                                                    // builder.setCancelable(false);
                                                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            for (int i=(ListVideos.size()-1); 0<=i; i--){
                                                                if (mItemChecked.get(i)==true){
                                                                    Log.i("mItemChecked - ", "Borrado" + i);
                                                                    deleteImage(i);

                                                                }else{
                                                                    Log.i("mItemChecked - ", "Es false" + i);
                                                                }
                                                            }

                                                            for (int i=(ArchivosUris.size()-1); 0<=i ;i--){
                                                                if (archivoChecked.get(i)==true){
                                                                    deleteFile(i);
                                                                    Log.i("mArchivoChecked - ", "Borrado" + i);
                                                                }else{
                                                                    Log.i("mArchivoChecked - ", "Es false" + i);
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
                                                }
                                            });

        actionButton_Enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ((estado).equals("before") && cont1 > 0) {
                    StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT ).show();
                } else if ((estado).equals("during") && cont2 > 0) {
                    StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT ).show();
                } else if ((estado).equals("after") && cont3 > 0) {
                    StyleableToast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT ).show();
                } else {

                    checked_NoAplica = check_NoAplica.isChecked();
                    final String advanceed = String.valueOf(edpercentage.getText());
                    if(!advanceed.equals("")) {
                        Observation = edObserv.getText().toString();
                        if (!Observation.equals("")) {
                            if (contImg >= min_photos || check_NoAplica.isChecked()) {
                                if (check_NoAplica.isChecked()==true && contImg == 0 || check_NoAplica.isChecked()==false && contImg>0  ){
                                    if (contImg > max_photos) {
                                        Toast.makeText(getApplicationContext(), "El maximo de fotos es " + max_photos + " usted ha superado esa cantidad por " + (contImg - max_photos), Toast.LENGTH_SHORT ).show();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                        builder.setTitle("Confirmación");
                                        builder.setMessage("¿Está seguro de enviar? \n\n" + "Enviará " + contImg + " de " + max_photos + " elementos disponibles");
                                        Toast.makeText(getApplicationContext(), "Una vez realizada esta acción no se puede revertir!!", Toast.LENGTH_LONG ).show();
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
                                                    uuids = "" + uuid;


                                                    BDFireStore.collection("events").document(idevent).update("before_complete", true);
                                                    before_complete = true;

                                                    if (check_NoAplica.isChecked()) {
                                                        if (contUris == 0) {
                                                            Subirdatos();
                                                        } else {
                                                            uploadAllFiles();
                                                        }
                                                    } else {
                                                        uploadAllImages();
                                                        uploadAllFiles();
                                                    }


                                                    BDFireStore.collection("events").document(idevent).update("color", "#3498db");

//                                                BDFireStore.collection("events").document(idevent).update("ava", 1);

                                                    //Toast.makeText(getApplicationContext(), "Datos ingresados", Toast.LENGTH_SHORT).show();


                                                }

                                                if ((estado).equals("during")) {


                                                    if (ava <= Integer.parseInt(String.valueOf(edpercentage.getText())) || 0 == Integer.parseInt(String.valueOf(edpercentage.getText())) && during_complete == false) {
                                                        if (Integer.parseInt(String.valueOf(edpercentage.getText())) <= number) {

                                                            ava = Integer.parseInt(String.valueOf(edpercentage.getText()));

                                                            boolean during = true;

                                                            header = "during";
                                                            UUID uuid = UUID.randomUUID();
                                                            uuids = "" + uuid;

                                                            if (ava == number) {
                                                                BDFireStore.collection("events").document(idevent).update("during_complete", true);
                                                                during_complete = true;
                                                            }

                                                            if (check_NoAplica.isChecked()) {
                                                                if (contUris == 0) {
                                                                    Subirdatos();
                                                                } else {
                                                                    uploadAllFiles();
                                                                }
                                                            } else {
                                                                uploadAllImages();
                                                                uploadAllFiles();
                                                            }


                                                            terminado = 2;

                                                            BDFireStore.collection("events").document(idevent).update("ava", ava);


                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "El avance no puede ser mayor al 100%", Toast.LENGTH_LONG ).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "El avance no puede ser menor al anterior", Toast.LENGTH_LONG ).show();
                                                    }


                                                }

                                                if ((estado).equals("after")) {

                                                    boolean after = true;

                                                    header = "after";
                                                    UUID uuid = UUID.randomUUID();
                                                    uuids = "" + uuid;

//                                            Toast.makeText(getApplicationContext(), "Datos ingresados", Toast.LENGTH_LONG ).show();

                                                    ava = number;

                                                    edObserv.setText("");
                                                    if (check_NoAplica.isChecked()) {
                                                        if (contUris == 0) {
                                                            Subirdatos();
                                                        } else {
                                                            uploadAllFiles();
                                                        }
                                                    } else {
                                                        uploadAllImages();
                                                        uploadAllFiles();
                                                    }

                                                    if (ava == number) {
                                                        BDFireStore.collection("events").document(idevent).update("active", false);
                                                    }
                                                    BDFireStore.collection("events").document(idevent).update("ava", ava);


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
                                }else{
                                    Toast.makeText(getApplicationContext(), "No puede subir multimedia si ha seleccionado <<No aplica>>", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "El número mínimo de fotos para poder enviar es " + min_photos, Toast.LENGTH_SHORT ).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "El texto es necesario para poder enviar", Toast.LENGTH_SHORT ).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "El avance es necesario para poder enviar", Toast.LENGTH_SHORT ).show();
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

    private void onclick(View view){
//        if(view.getId()==)
    }

    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(SupervisionActivity.this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }



    private void GuardarInformacionVideos() {

            locationStart();
            created_at_funct();

            datatime.setDate(Fecha);
            datatime.setTime(Hora);

            locationStart();
            ubication.setLat(Lat);
            ubication.setLng(Lng);




            PhotoData.setCreated_at(created_at);
            PhotoData.setUbication(ubication);
            PhotoData.setType("Video");
            PhotoData.setSrc(""+contImg);

            multimedia.add(0, PhotoData);

            PhotoData = new Foto();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permisos otorgados...", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(this, "Aplicacion sin permisos...", Toast.LENGTH_SHORT).show();
                }
                // permissions list of don't granted permission
            }
            return;
        }
    }

    private void initRecyclerView(){
        int mNoOfColumns = Utility.calculateNoOfColumns(getApplicationContext(), 90);

        mRecyclerView = findViewById(R.id.images_recycler);
//        mLayaoutManager= new GridLayoutManager(this, 4);
        mLayaoutManager= new GridLayoutManager(this,mNoOfColumns);
        mAdapter = new RecyclerViewAdapter(this, mTypeAdapter, ListVideos, mItemChecked, new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Uri mImage, int position) {
               

                    if(mTypeAdapter.get(position).equals("Photo")) {
                        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(SupervisionActivity.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Drawable d = new BitmapDrawable(mImageBitmap.get(position));
                        photoView.setImageDrawable(d);
                        mBuilder.setView(mView);
                        final AlertDialog mDialog = mBuilder.create();
                        mDialog.getWindow().setLayout(600, 400);
                        mDialog.show();







                    }
                    if(mTypeAdapter.get(position).equals("Video")) {
//                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SupervisionActivity.this);
//                        View mView = getLayoutInflater().inflate(R.layout.layout_videoview, null);
//                        VideoView videoView = mView.findViewById(R.id.video_recycler);
////                    photoView.setImageURI(Uri.fromFile(fileimagen));
//
//                        videoView.setVideoURI(ListVideos.get(position));
//                        mBuilder.setView(mView);
//                        AlertDialog mDialog = mBuilder.create();
//                        mDialog.getWindow().setLayout(600, 400);
//                        mDialog.show();
                    }


            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(mLayaoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void addImage(Uri uri,int position, String type){
        ListVideos.add(position, uri);
        mTypeAdapter.add(position, type);
        mItemChecked.add(0, false);
        mAdapter.notifyItemInserted(position);
        mLayaoutManager.scrollToPosition(position);
    }

    private void deleteImage(int position){

            mImageBitmap.remove(position);
            mTypeAdapter.remove(position);
            mItemChecked.remove(position);
            ListVideos.remove(position);
            multimedia.remove(position);
            mAdapter.notifyItemRemoved(position);
        contImg--;
    }

    private void initRecyclerViewFiles(){


        mRecyclerViewFiles = findViewById(R.id.archivos_recycler);
        mLayaoutManagerFiles = new LinearLayoutManager(this);
        mAdapterFiles = new ArchivosAdapter(this, ArchivosUris, Type_Archivo, Name_Archivo, archivoChecked);

        mRecyclerViewFiles.setHasFixedSize(true);
        mRecyclerViewFiles.setItemAnimator(new DefaultItemAnimator());

        mRecyclerViewFiles.setLayoutManager(mLayaoutManagerFiles);
        mRecyclerViewFiles.setAdapter(mAdapterFiles);
    }

    private void addFile(){

        File filename = new File(ArchivosUris.get(0).getPath());
        Name_Archivo.add(0, filename.getName());
        archivoChecked.add(0, false);
        mAdapterFiles.notifyItemInserted(0);
        mLayaoutManagerFiles.scrollToPosition(0);
    }

    private void deleteFile(int position){
        ArchivosUris.remove(position);
        Name_Archivo.remove(position);
        Type_Archivo.remove(position);
        archivoChecked.remove(position);
        files.remove(position);
        mAdapterFiles.notifyItemRemoved(position);
        contUris--;
    }

    private void ChequeoConfiguration() {
        locationStart();
        BDFireStore
                .collection("configuration")
                .document("global")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Configuration configuration = documentSnapshot.toObject(Configuration.class);

                event_types = configuration.getEvent_types();
                max_photos = configuration.getMax_photos();
                min_photos = configuration.getMin_photos();

                for (int i = 0; i< event_types.size(); i++){
                    String name = event_types.get(i).getName();
                    if (name.equals(actividad)){
                        Tbefore = event_types.get(i).isBefore();
                        Tduring = event_types.get(i).isDuring();
                        Tafter = event_types.get(i).isAfter();
                        break;
                    }
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ChequeoDeVariables();
            }
        });

        BDFireStore.collection("events").document(idevent).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Evento evento = documentSnapshot.toObject(Evento.class);
                during_complete = evento.isDuring_complete();
                before_complete = evento.isBefore_complete();
            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ChequeoDeVariables();
            }
        });

        BDFireStore.collection("users").document(mAuth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Usuario usuario = documentSnapshot.toObject(Usuario.class);

                username = usuario.getName();
            }
        });

    }


    private void GuardarInformacionArchivos() {
        locationStart();
        created_at_funct();

        PerFile.setCreated_at(created_at);
        files.add(0,PerFile);
        PerFile = new Files();

    }

    private void inicializacionVariables() {

        files = new ArrayList<>();
        contUris=0;

        multimedia = new ArrayList<>();
        contImg=0;

        PhotoData = new Foto();
        /*
        for (int x=0; x<1001; x++) {
            PerFotoArray[x] = new Foto();
        }*/
    }

    private void uploadAllImages() {

        for(int i=0; i<mImageBitmap.size(); i++){
            PhotoData = multimedia.get(i);
            if (PhotoData.getType().equals("Video")){
                uploadVideo(ListVideos.get(i), i);
            }
            if (PhotoData.getType().equals("Imagen")){
                if(mImageBitmap.get(i) != null){
                    uploadImageGlobal(mImageBitmap.get(i), i);
                    //multimedia.add(PhotoData);
                }
            }
        }
    }




    private void created_at_funct() {
        java.util.Date FecAct = new Date();
        Calendar calendar = new GregorianCalendar();
        DateTime dateTime = new DateTime();

//        Fecha =   today.year + "-" + today.month + "-" + today.monthDay;

        String mes = "" + dateTime.getMonthOfYear();
        if (mes.length() == 1) {
            mes = "0" + mes;
        }

        String dia = "" + calendar.get(Calendar.DAY_OF_MONTH);
        if (dia.length() == 1) {
            dia = "0" + dia;
        }
        Fecha =   calendar.get(Calendar.YEAR) + "-" + mes + "-" + dia;

        String minute = ""+calendar.get(Calendar.MINUTE);
        if (minute.length()==1){
            minute = "0"+minute;
        }

        String hora = ""+calendar.get(Calendar.HOUR);
        if (hora.length()==1){
            hora = "0"+hora;
        }

        Hora = hora +":" + minute;

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




        PhotoData.setCreated_at(created_at);
        PhotoData.setUbication(ubication);
        PhotoData.setType("Imagen");
        PhotoData.setSrc(""+contImg);

        multimedia.add(0, PhotoData);

        PhotoData = new Foto();
    }

    private void EventoTerminado() {

        if (estado.equals("after")|| estado.equals("during") && Tafter==false && ava == number || estado.equals("before") && Tduring==false && Tafter==false || during_complete == true && before_complete==true && estado=="after" && ava == number ) {


            BDFireStore.collection("events").document(idevent).update("color", "#27ae60");

            mQuery = BDFireStore.collection("events")
                    .whereEqualTo("user_id", mAuth.getUid())
                    .whereEqualTo("activity_id", activity_id)
                    .whereEqualTo("active", true);

//            BDFireStore.collection("events").document(idevent).update("active", false);

            BDFireStore.collection("events").document(idevent).update("active", false).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    mQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                contEventos = 0;
                                for (DocumentSnapshot document : task.getResult()) {
                                    contEventos++;
                                }
                                if (contEventos == 0) {
                                    BDFireStore.collection("activities").document("" + activity_id).update("users." + mAuth.getUid(), false);
//                                    Toast.makeText(SupervisionActivity.this, "contEventos" + contEventos, Toast.LENGTH_SHORT).show();
                                    mQuery2 = BDFireStore.collection("activities")
                                            .whereEqualTo("users." + mAuth.getUid(), true)
                                            .whereEqualTo("project_id", project_id);


                                    mQuery2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                contActivity = 0;
                                                for (DocumentSnapshot document : task.getResult()) {
                                                    contActivity++;
                                                }
                                                Toast.makeText(SupervisionActivity.this, "contActivity: " + contActivity, Toast.LENGTH_SHORT).show();
                                                if (contActivity == 0) {
                                                    BDFireStore.collection("projects").document("" + project_id).update("users." + mAuth.getUid(), false).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {


                                                        }
                                                    });
                                                }

                                            } else {
                                                Log.d("<E> ActivityFragment:", " Error getting documents: ", task.getException());
                                            }
                                        }
                                    });
                                }
                            } else {
                                Log.d("<E> en EventosFragment:", " Error getting documents: ", task.getException());
                            }


                            Toast.makeText(getApplicationContext(), "Evento terminado", Toast.LENGTH_SHORT).show();


                            NotificationCompat.Builder mBuilder;
                            NotificationManager mNotifyMgr = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

                            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            String NOTIFICACION_CHANNEL_ID = "com.acadep.acadepsistemas.rso.test";

                            int icono = R.mipmap.ic_launcher;
                            Intent intent = new Intent(SupervisionActivity.this, MainActivity.class);
                            PendingIntent pendingIntent = PendingIntent.getActivity(SupervisionActivity.this, 0, intent, 0);

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

                            startActivity(new Intent(SupervisionActivity.this, MainActivity.class));
                            SupervisionActivity.this.finish();
                        }
                    });
                }
            });



//            Toast.makeText(getApplicationContext(), "Evento terminado", Toast.LENGTH_SHORT  ).show();
//
//
//            NotificationCompat.Builder mBuilder;
//            NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
//
//            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            String NOTIFICACION_CHANNEL_ID = "com.acadep.acadepsistemas.rso.test";
//
//            int icono = R.mipmap.ic_launcher;
//            Intent intent = new Intent(SupervisionActivity.this, MainActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(SupervisionActivity.this, 0,intent, 0);
//
//            NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICACION_CHANNEL_ID);
//
//            notiBuilder.setAutoCancel(true)
//                    .setDefaults(Notification.DEFAULT_ALL)
//                    .setWhen(System.currentTimeMillis())
//                    .setSmallIcon(R.drawable.rso)
//                    .setContentTitle("Evento terminado")
//                    .setContentText(nameEvent + " ha finalizado")
//                    .setContentInfo("info")
//                    .setAutoCancel(true)
//                    .setContentIntent(pendingIntent);
//
//
//            notificationManager.notify(new Random().nextInt(), notiBuilder.build());
//
//            startActivity(new Intent(SupervisionActivity.this, MainActivity.class));
//            SupervisionActivity.this.finish();





        }
    }

    private void MostrarOpciones() {

        if((estado).equals("before") && cont1>0){
            Toast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT ).show();
        }else if ((estado).equals("during") && cont2>0){
            Toast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT ).show();
        }else if ((estado).equals("after") && cont3>0){
            Toast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT ).show();
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

                        if (contUris==0) {
                            Toast.makeText(getApplicationContext(), "No hay ningún archivo para borrar", Toast.LENGTH_SHORT ).show();
                        }else{
                            if(contUris<11){
                                contUris--;
                                ArchivosUris.remove(0);
                                files.remove(0);
                                Toast.makeText(getApplicationContext(), "¡Archivo Borrado con exito!", Toast.LENGTH_SHORT  ).show();
                            }else{

                            }

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


        for (int i = 0; i<ArchivosUris.size(); i++){
            if ( ArchivosUris.get(i) != null){
                uploadfileGlobal(ArchivosUris.get(i), i);
            }

        }

    }



    private void mostrarDialogoOpciones() {

        if((estado).equals("before") && cont1>0){
            Toast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT ).show();
        }else if ((estado).equals("during") && cont2>0){
            Toast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT ).show();
        }else if ((estado).equals("after") && cont3>0){
            Toast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT ).show();
        }else {
            final CharSequence[] opciones = {"Elegir PDF", "Elegir Docx", "Elegir Video", "Elegir Audio", "Cancelar"};
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            if (contUris == 0) {
                Toast.makeText(getApplicationContext(), "Solo puede añadir 10 archivos diferentes", Toast.LENGTH_SHORT ).show();
                //Toast.makeText(getApplicationContext(),"Solo puede añadir 10 archivos diferentes \nUna vez agregado un archivo este no puede ser eliminado",Toast.LENGTH_LONG).show();
            }

            builder.setTitle("Elige un Archivo");
            builder.setItems(opciones, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (opciones[i].equals("Elegir PDF")) {

                        PerFile = files.get(0);
                        PerFile.setType("PDF");
                        PerFile.setSrc(""+contUris);
                        files.set(0,PerFile);
                        PerFile = new Files();

                        selectPDF();
                    } else if (opciones[i].equals("Elegir Docx")) {
                        PerFile = files.get(0);
                        PerFile.setType("Docx");
                        PerFile.setSrc(""+contUris);
                        files.set(0,PerFile);
                        PerFile = new Files();

                        selectDocx();
                    } else if (opciones[i].equals("Elegir Video")) {
                        PerFile = files.get(0);
                        PerFile.setType("Video");
                        PerFile.setSrc(""+contUris);
                        files.set(0,PerFile);
                        PerFile = new Files();

                        selectVideo();
                    } else if (opciones[i].equals("Elegir Audio")) {
                        PerFile = files.get(0);
                        PerFile.setType("Audio");
                        PerFile.setSrc(""+contUris);
                        files.set(0,PerFile);
                        PerFile = new Files();

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



//        if(ava ==0 && Tbefore==true && before_complete == false){
        if(Tbefore==true && before_complete == false){
            txtAvance.setVisibility(View.INVISIBLE);
            edpercentage.setVisibility(View.INVISIBLE);
            txtTotal.setVisibility(View.INVISIBLE);
            txtAyuda.setVisibility(View.INVISIBLE);

            ava =0;
            bottomNavigationView.getSelectedItemId();
            bottomNavigationView.setSelectedItemId(R.id.itemDespues);
            bottomNavigationView.setEnabled(true);
            bottomNavigationView.setSelectedItemId(R.id.itemDurante);
            bottomNavigationView.setEnabled(true);
            bottomNavigationView.setSelectedItemId(R.id.itemAntes);
            bottomNavigationView.setEnabled(true);

            estado = "before";
            txtEstado.setText("Evidencia: antes");
        }else {


//            if (ava >= 1 && ava < number && Tduring == true && during_complete == false || Tbefore == false && Tduring == true && during_complete == false|| during_complete == false && ava >= 1 || during_complete == false && Tbefore==true && before_complete==true) {
            if (Tduring == true && during_complete==false){
//                edpercentage.setText(""+ava);


                txtAvance.setVisibility(View.VISIBLE);
                edpercentage.setVisibility(View.VISIBLE);
                txtTotal.setVisibility(View.VISIBLE);
                txtAyuda.setVisibility(View.VISIBLE);

                bottomNavigationView.getSelectedItemId();
                bottomNavigationView.setSelectedItemId(R.id.itemAntes);
                bottomNavigationView.setEnabled(false);
                bottomNavigationView.setSelectedItemId(R.id.itemDespues);
                bottomNavigationView.setEnabled(false);
                bottomNavigationView.setSelectedItemId(R.id.itemDurante);
                bottomNavigationView.setEnabled(true);
                Menu menu = bottomNavigationView.getMenu();
                MenuItem item = menu.getItem(1);
                item.setChecked(true);
                item.setEnabled(true);



                estado = "during";
                txtEstado.setText("Evidencia: Durante");
            }else {

//                if (ava == number && Tafter == true && during_complete == true || Tduring == false && Tafter == true) {
                if (Tafter == true && during_complete == true) {
                    txtAvance.setVisibility(View.INVISIBLE);
                    edpercentage.setVisibility(View.INVISIBLE);
                    txtTotal.setVisibility(View.INVISIBLE);
                    txtAyuda.setVisibility(View.INVISIBLE);

                    bottomNavigationView.getSelectedItemId();
                    bottomNavigationView.setSelectedItemId(R.id.itemAntes);
                    bottomNavigationView.setEnabled(false);
                    bottomNavigationView.setSelectedItemId(R.id.itemDurante);
                    bottomNavigationView.setEnabled(false);
                    bottomNavigationView.setSelectedItemId(R.id.itemDespues);
                    bottomNavigationView.setEnabled(true);
                    Menu menu = bottomNavigationView.getMenu();
                    MenuItem item = menu.getItem(2);
                    item.setChecked(true);
                    item.setEnabled(true);

                    estado = "after";
                    txtEstado.setText("Evidencia: Después");
                }
            }
        }




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
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode==TAKE_PHOTO_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//            takePhoto_AltaCalidad();
//        }
//        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
//            selectPDF();
//        }else{
//            Toast.makeText(getApplicationContext(), "Porfavor otorgue los permisos...", Toast.LENGTH_LONG ).show();
//            //Toast.makeText(getApplicationContext(),"Porfavor otorgue los permisos...",Toast.LENGTH_SHORT).show();
//        }
//
//        if (requestCode == 1000) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                locationStart();
//                return;
//            }
//        }
//    }
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

//    public void AyudaClickListener(View view) {
//        new AlertDialog.Builder(getApplicationContext())
//                .setTitle("Ayuda")
//                .setMessage("El total del avance debe ser igual al total de la tarea para completar esta sección")
//
//
//                // Specifying a listener allows you to take an action before dismissing the dialog.
//                // The dialog is automatically dismissed when a dialog button is clicked.
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Continue with delete operation
//                    }
//                })
//
//                // A null listener allows the button to dismiss the dialog and take no further action.
//                .setNegativeButton(android.R.string.no, null)
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
//    }

//    public void onClickSwitch(View view) {
//        if (view.getId() == R.id.swtBorrar) {
//            if (swtBorrar.isChecked()) {
//                Toast.makeText(SupervisionActivity.this, "Al activar esta opción si da click sobre una imagen la borrará", Toast.LENGTH_SHORT ).show();
//            } else {
//                Toast.makeText(SupervisionActivity.this, "Borrado de fotos desactivado", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }


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
        PhotoData = new Foto();

        for (int x=0; x<contImg; x++) {
            ListVideos.remove(0);
            mTypeAdapter.remove(0);
            mImageBitmap.remove(0);
            mAdapter.notifyItemRemoved(0);
        }

        contImg=0;
        contT=0;
        contT2=0;

//        mTypeAdapter = new ArrayList<>();
//        mImageBitmap = new ArrayList<>();
//        ListImages = new ArrayList<>();
//        ListVideos = new ArrayList<>();
    }

    private void takePhoto_AltaCalidad() {
        final String dir = Environment.getExternalStorageDirectory().toString() + "/Imagenes-De-RSO/";
        File newdir = new File(dir);
        if (!newdir.exists()) {
            newdir.mkdir();
        }

        int n = 10000;
        Random generator = new Random();
        n = generator.nextInt(n);
        String imageName = "Image-" + UUID.randomUUID().toString() + ".jpg";
        String file = dir+imageName;
        File newfile = new File(file);
        try {
            newfile.createNewFile();
        }
        catch (IOException e)
        {
        }

        Uri outputFileUri = FileProvider.getUriForFile(SupervisionActivity.this,"com.acadep.acadepsistemas.rso.fileprovider", newfile);


        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);


        filePath = newfile.getPath();

        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
    }

    public void takePhoto() throws IOException {

        if((estado).equals("before") && ava >=1){
            Toast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT ).show();
        }else if ((estado).equals("during") && ava >99){
            Toast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT ).show();
        }else {


            //-------------------------------------------------------------------- Intengo 2
//            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//            String imageFileName = "Image-" + timeStamp + ".jpg";
//
////            File storageDir = Environment.getExternalStoragePublicDirectory(
////                    Environment.DIRECTORY_PICTURES);
//
//            String root = Environment.getExternalStorageDirectory().toString();
//            File storageDir = new File(root + "/Imagenes-De-RSO");
//
//            pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
//
//            File file = new File(pictureImagePath);
//
//            Uri outputFileUri = Uri.fromFile(file);
//
//            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//
//            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
//
//            startActivityForResult(cameraIntent, CAPTURE_PHOTO);
            //-------------------------------------------------------------------- Intengo 2


            //-------------------------------------------------------------------- Intengo 1
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAPTURE_PHOTO);
            //-------------------------------------------------------------------- Intengo 1

        }
    }







    private void takeVideo() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    private void BorrarFiles() {

        for (int i=0; i<contUris; i++) {
            ArchivosUris.remove(0);
            Name_Archivo.remove(0);
            Type_Archivo.remove(0);
            archivoChecked.remove(0);
            files.remove(0);
            mAdapterFiles.notifyItemRemoved(0);
        }

        contUris=0;
//        files = new ArrayList<>();
//        archivoChecked = new ArrayList<>();
//        Type_Archivo = new ArrayList<>();
//        Name_Archivo = new ArrayList<>();
//        ArchivosUris = new ArrayList<>();
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


        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            Log.d("CameraDemo", "Pic saved");
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);

            double heightd = bitmap.getHeight()*.1720430107526882;
            float heightf =  (float)heightd;
            Bitmap Bitnew = redimensionarImagenMaximo(bitmap, 512 ,  heightf);
            mImageBitmap.add(0, Bitnew);

            addImage( Uri.fromFile(new File(filePath)), 0, "Photo");
//            ListVideos.add(0, Uri.fromFile(new File(filePath)));
            contImg++;
            GuardarInformacionImagenes();

        }

        if(requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {

            Uri videoUri = data.getData();
//            addImage(new File(videoUri.getPath()), 0, "Video");
            addImage(videoUri, 0, "Video");

            Bitmap icon = BitmapFactory.decodeResource(SupervisionActivity.this.getResources(),
                    R.drawable.reproductor_multimedia);

            mImageBitmap.add(0, icon);
//            ListVideos.add(0, videoUri);
            contImg++;

        }


        if (requestCode==86 && resultCode==RESULT_OK && data!=null) {
            if(contUris>9){
                Toast.makeText(getApplicationContext(), "Limite de archivos alcanzado", Toast.LENGTH_LONG ).show();
            }else {
                SelecUri(data);
                addFile();
                contUris++;
            }
            //Toast.makeText(getApplicationContext(),"Tu archivo se ha guardado exitosamente",Toast.LENGTH_SHORT).show();
        }else if (requestCode==87 && resultCode==RESULT_OK && data!=null) {
            if(contUris>9){
                Toast.makeText(getApplicationContext(), "Limite de archivos alcanzado", Toast.LENGTH_LONG ).show();
            }else {
                SelecUri(data);
                addFile();
                contUris++;
            }
            //Toast.makeText(getApplicationContext(),"Tu archivo se ha guardado exitosamente",Toast.LENGTH_SHORT).show();
        } if (requestCode==88 && resultCode==RESULT_OK && data!=null) {
            if(contUris>9){
                Toast.makeText(getApplicationContext(), "Limite de archivos alcanzado", Toast.LENGTH_LONG ).show();
            }else {
                SelecUri(data);
                addFile();
                contUris++;
            }
            //Toast.makeText(getApplicationContext(),"Tu archivo se ha guardado exitosamente",Toast.LENGTH_SHORT).show();
        }
        if (requestCode==89 && resultCode==RESULT_OK && data!=null) {
            if(contUris>9){
                Toast.makeText(getApplicationContext(), "Limite de archivos alcanzado", Toast.LENGTH_LONG ).show();
            }else {
                SelecUri(data);
                addFile();
                contUris++;
            }
           // Toast.makeText(getApplicationContext(),"Tu archivo se ha guardado exitosamente",Toast.LENGTH_SHORT).show();
        }

        if (requestCode==1 && resultCode == RESULT_OK){
            capturedCoolerBitmap = (Bitmap) data.getExtras().get("data");

        }

        if (requestCode==104 && resultCode == RESULT_OK) {



//            File imgFile = new  File(pictureImagePath);
//
//            if(imgFile.exists()){
//
//                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                contImg++;
//                addImage(myBitmap, 0);
//
//            }

            // ---------------
                    capturedCoolerBitmap = (Bitmap) data.getExtras().get("data");
                    //Bitmap resizeImage = Bitmap.createScaledBitmap(capturedCoolerBitmap,CamWidth,CamHegith,false);
                    Bitmap Bitnew = redimensionarImagenMaximo(capturedCoolerBitmap, 1200 , 800);
//                    Bitmap Bitnew = capturedCoolerBitmap;

                    contImg++;
                    saveImageToGallery(Bitnew);
//                    addImage(Bitnew, 0);

        }





    }


    private void SelecUri(Intent data) {


        if(contUris<10){
            ArchivosUris.add(0, data.getData());
            Toast.makeText(getApplicationContext(), "Archivo agregado con exito \nPuede subir " + (restUris-contUris) + " más", Toast.LENGTH_LONG ).show();

        }else{
            Toast.makeText(getApplicationContext(), "Limite de archivos alcanzado", Toast.LENGTH_LONG ).show();
        }

    }

    // ------------------------------------------------------- Inicio de guardar foto en la galeria ------------------------------------------------------------------------------ //

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
        File myDir = new File(root + "/Imagenes-De-RSO");
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

        ListImages.add(0, file);
    }

    // ------------------------------------------------------- Final de guardar foto en la galeria ------------------------------------------------------------------------------ //


    // ------------------------------------------------------- Inicio de Subir archivos ------------------------------------------------------------------------------ //

    private void uploadVideo(Uri videoUri, final int i) {
        progressDialog= new ProgressDialog(SupervisionActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo archivo...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final StorageReference ref = storageReference.child("images").child("evidence").child("video" + UUID.randomUUID().toString());
        final String fileName= "Archivo" + UUID.randomUUID().toString();

        ref.putFile(videoUri)
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
                                    PhotoData = multimedia.get(i);
                                    PhotoData.setSrc(downloadUri.toString());
                                    multimedia.set(i, PhotoData);
                                    PhotoData = new Foto();

                                    contT++;

                                    if(contT == contImg && contUris==0 ){
                                        Subirdatos();


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
                                    Toast.makeText(getApplicationContext(), "Archivo Subido...", Toast.LENGTH_SHORT ).show();
                                    //Toast.makeText(getApplicationContext(),"Archivo Subido...",Toast.LENGTH_SHORT).show();




                                }else{
//                                    Toast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                                    //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
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


    private void uploadImageGlobal(Bitmap bitnew, final int x) {



            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitnew.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dato = baos.toByteArray();




            final StorageReference ref = storageReference.child("images").child("evidence").child("Img" + created_at + UUID.randomUUID().toString());

//        ref.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
        ref.putBytes(dato).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        PhotoData = multimedia.get(x);
                        PhotoData.setSrc(downloadUri.toString());
                        multimedia.set(x, PhotoData);

                        PhotoData = new Foto();

                        contT++;
                        if(contT == contImg && contUris==0 ){
                            Subirdatos();

                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
//            ref.putFile(Uri.fromFile(fileimagenpos)).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                @Override
//                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                    if (!task.isSuccessful()) {
//                        throw task.getException();
//                    }
//                    return ref.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if (task.isSuccessful()) {
//                        Uri downloadUri = task.getResult();
//                        PhotoData = multimedia.get(x);
//                        multimedia.remove(x);
//                        PhotoData.setSrc(downloadUri.toString());
//                        multimedia.add(x, PhotoData);
//
//                        Subirdatos();
//                        if(x == (contImg-1) && contUris==0){
////                            BDFireStore.collection("evidence").document(uuids).set(multimedia, SetOptions.merge());
//                            BorrarImagenes();
//                        }
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });


    }

    private void Subirdatos() {
        created_at_funct();

        User user = new User();
        user.setId(mAuth.getUid());
        user.setName(username);

        check_NoAplica.setChecked(false);



        Total total = new Total();
        total.setNumber(number);
        total.setUnit(unit);
        Data data = new Data(ava, created_at, Observation, header, total, false , user,ref_event, Lat, Lng, multimedia, files);
        BDFireStore.collection("evidence").document(uuids).set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "Información subida exitosamente", Toast.LENGTH_SHORT  ). show();
                edObserv.setText("");
                EventoTerminado();
                ChequeoDeVariables();
                BorrarFiles();
                BorrarImagenes();

                if (before_complete==true && during_complete==false){
                    estado = "during";
                    txtEstado.setText("Evidencia: Durante");
                    txtAvance.setVisibility(View.VISIBLE);
                    edpercentage.setVisibility(View.VISIBLE);
                    txtTotal.setVisibility(View.VISIBLE);
                    txtAyuda.setVisibility(View.VISIBLE);
                }else if (during_complete==true || ava ==number){
                    txtAvance.setVisibility(View.INVISIBLE);
                    edpercentage.setVisibility(View.INVISIBLE);
                    txtTotal.setVisibility(View.INVISIBLE);
                    txtAyuda.setVisibility(View.INVISIBLE);
                    estado = "after";
                    txtEstado.setText("Evidencia: Después");


                }
            }
        });

    }

    private void uploadfileGlobal(Uri ArchivoUri, final int i) {
        progressDialog= new ProgressDialog(SupervisionActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo archivo...");
        progressDialog.setProgress(0);
        progressDialog.show();

        int Tpath = ArchivoUri.getPath().length();
        String nombre = ArchivoUri.getPath().substring(9, Tpath);

         File filename = new File(ArchivoUri.getPath());
        Log.i("Test Path" , ""+ArchivoUri.toString());
        Log.i("Test Path" , ""+ArchivoUri.getPath());
        Log.i("Test Path" , ""+filename.getPath());

        int index = filename.getName().lastIndexOf('/');
        String Name = filename.getName().substring(index+1);




        PerFile = files.get(i);
        PerFile.setName( Name);
//        PerFile.setName(ArchivoUri.getPath());
        files.set(i,PerFile);
        PerFile = new Files();


        final String fileName= "Archivo" + UUID.randomUUID().toString();
        final StorageReference srtreference = storage.getReference();
        srtreference.child("files").child("Evidence").child(fileName).putFile(ArchivoUri)
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

                                    PerFile = files.get(i);
                                    PerFile.setSrc(downloadUri.toString());
                                    files.set(i,PerFile);
                                    PerFile = new Files();



                                    contT2++;
                                    if(contT2 == contUris){

                                        Subirdatos();
//                                        BorrarImagenes();
//                                        BorrarFiles();

                                        progressDialog.setProgress(100);
                                        progressDialog.setMessage("Click para salir...");
                                        Toast.makeText(getApplicationContext(), "Subida de archivos terminada!",Toast.LENGTH_SHORT  ).show();






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
                                    Toast.makeText(getApplicationContext(), "Archivo Subido...", Toast.LENGTH_SHORT  ).show();
                                    //Toast.makeText(getApplicationContext(),"Archivo Subido...",Toast.LENGTH_SHORT).show();




                                }else{
//                                    Toast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
                                    //Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(), "¡Fallo al subirse!", Toast.LENGTH_SHORT, R.style.dangerToast).show();
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

    // ------------------------------------------------------- Final de Subir archivos  ------------------------------------------------------------------------------ //


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    boolean opcion = true;

                    if (menuItem.getItemId()==R.id.itemAntes) {
                        if (ava >=1 ) {
//                            Toast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT ).show();
                            opcion = false;
                            menuItem.setEnabled(false);
                        } else {
                            if(ava == 0 && Tbefore==true ) {
//                                Toast.makeText(getApplicationContext(), "Seccion disponible", Toast.LENGTH_SHORT,  ).show();
                                estado = "before";
                                txtEstado.setText("Antes del Evento");
                            }else{
                                opcion = false;
                                menuItem.setEnabled(false);
                            }
                        }
                    }

                    if (menuItem.getItemId()==R.id.itemDurante){
                        if(ava == number ) {
//                            Toast.makeText(getApplicationContext(), "Ya realizaste esta seccion", Toast.LENGTH_SHORT ).show();
                            opcion = false;
                            menuItem.setEnabled(false);
                        }else {
                            if (ava >=1 && ava <=number  && Tduring==true|| Tbefore == false && Tduring == true) {
//                                Toast.makeText(getApplicationContext(), "Seccion disponible", Toast.LENGTH_SHORT,  ).show();
                                estado = "during";
                                txtEstado.setText("Durante el Evento");
                            }else{
                                opcion = false;
                                menuItem.setEnabled(false);
//                                Toast.makeText(getApplicationContext(), "Te faltan secciones por realizar", Toast.LENGTH_SHORT ).show();
                            }

                        }
                    }

                    if (menuItem.getItemId()==R.id.itemDespues){

                            if (ava == number && Tafter==true || Tduring==false && Tafter==true && ava != 0) {

//                                Toast.makeText(getApplicationContext(), "Seccion disponible", Toast.LENGTH_SHORT,  ).show();
                                estado = "after";
                                txtEstado.setText("Después del Evento");
                            }else{
                                opcion = false;
                                menuItem.setEnabled(false);
//                                Toast.makeText(getApplicationContext(), "Te faltan secciones por realizar", Toast.LENGTH_SHORT ).show();
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
        locationStart();

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
        deleted = extras.getString("deleted");
        ava = extras.getInt("ava");
        number = extras.getInt("number");
        unit = extras.getString("unit");
        during_complete = extras.getBoolean("during_complete");
        before_complete = extras.getBoolean("before_complete");
        activity_id = extras.getString("activity_id");
        project_id = extras.getString("project_id");

        //active=false;



    }

    @Override
    public void onBackPressed() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Estás seguro de salir?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            BorrarImagenes();
                            BorrarFiles();
                            SupervisionActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
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
        boolean status = true;

        Fragment mifragment = null;
        Boolean FragmentoSeleccionado=false;

        if (id == R.id.nav_perfil) {
            Toast.makeText(getApplicationContext(),"Aún en proceso",Toast.LENGTH_SHORT).show();
            status = false;
//        }else if (id == R.id.nav_acty) {
//            Toast.makeText(getApplicationContext(),"Aún en proceso",Toast.LENGTH_SHORT).show();
//            status = false;
        } else if (id == R.id.nav_event) {
            SupervisionActivity.this.finish();
//            mifragment = new EventosFragment();
//            FragmentoSeleccionado=true;
            mifragment = new ProjectFragment();
            FragmentoSeleccionado=true;

           /*Intent intent= new Intent (MainActivity.this, EventosActivity.class);
            startActivity(intent);]*/
            //finish();

//        } else if (id == R.id.nav_ext) {
//            Toast.makeText(getApplicationContext(),"Aún en proceso",Toast.LENGTH_SHORT).show();
//            item.setChecked(false);
//            status = false;
//            SupervisionActivity.this.finish();
//            mifragment = new ActivitysFragment();
//            FragmentoSeleccionado=true;

        }else if(id == R.id.nav_conf) {
            Toast.makeText(getApplicationContext(),"Aún en proceso",Toast.LENGTH_SHORT).show();
            status = false;
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
        return status;
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
//        btnEnviar.setVisibility(View.INVISIBLE);






    }


    public static class Utility {
        public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
            int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5); // +0.5 for correct rounding to int.
            return noOfColumns;
        }
    }
}

