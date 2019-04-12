package com.acadep.acadepsistemas.rso.Clases;

import android.Manifest;
import android.app.AlertDialog;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.acadepsistemas.seguimiento.Manifest;
import com.acadep.acadepsistemas.rso.Adapter.ArchivosAdapter;
import com.acadep.acadepsistemas.rso.Adapter.RecyclerViewAdapter;
import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Activity;
import com.acadep.acadepsistemas.rso.model.Extra;
import com.acadep.acadepsistemas.rso.model.Files;
import com.acadep.acadepsistemas.rso.model.Foto;
import com.acadep.acadepsistemas.rso.model.Ubication;
import com.acadep.acadepsistemas.rso.model.datetime;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.muddzdev.styleabletoast.StyleableToast;


import org.joda.time.DateTime;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;


import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class ExtraActivity extends AppCompatActivity {

    static String idevent;
    static String nameEvent;

    static String activity_id;
    static String title;

    TextView txtname, txtidevent;





    private static final String CARPETA_PRINCIPAL = "DCIM/";
    private static final String CARPETA_IMAGEN = "Camera";
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;
    public static final int MULTIPLE_PERMISSIONS = 10;
    String[] permissions= new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};


    static File [] FileImagenArray = new File[6];
    private static Files PerFilesArray[] = new Files[10];
    private static com.acadep.acadepsistemas.rso.model.Foto PerFotoArray[] = new Foto[6];

    static File nula = new File("/ada/");


    private BottomNavigationView bottomNavigationView;
    private FloatingActionMenu floatingActionsMenu;
    private com.github.clans.fab.FloatingActionButton actionButton_Take_photo;
    private com.github.clans.fab.FloatingActionButton actionButton_Take_video;
    private com.github.clans.fab.FloatingActionButton actionButton_Upload_PDF;
    private com.github.clans.fab.FloatingActionButton actionButton_Upload_Docx;
    private com.github.clans.fab.FloatingActionButton actionButton_Upload_Video;
    private com.github.clans.fab.FloatingActionButton actionButton_Upload_Audio;

    private com.github.clans.fab.FloatingActionButton actionButton_2_1;


    static Bitmap capturedCoolerBitmap;
    //Variables xxxxxx


    static int descision;

    //Variables xxxxxx

    static TextView mensaje1, mensaje2;

    static double Lat;
    static double Lng;


    // GPS LOCATION
    CheckBox checkBox1 ,checkBox2,checkBox3,checkBox4, checkBox5,checkBox6;

    FloatingTextButton btnEnviar;
    private FloatingTextButton btnFoto;


    //Variables Fotos

    private static final int REQUEST_PERM_WRITE_STORAGE = 102;
    private static final int CAPTURE_PHOTO = 104;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    int TAKE_PHOTO_CODE = 0;



    //    Imagenes
    private ArrayList<Bitmap> mImageBitmap = new ArrayList<>();
    private ArrayList<String> mTypeAdapter = new ArrayList<>();
    private ArrayList<Boolean> mItemChecked = new ArrayList<>();

    private static List<File> ListImages = new ArrayList<>();

    private static ArrayList<Uri> ListVideos = new ArrayList<>();



    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayaoutManager;

    private RecyclerView mRecyclerViewFiles;
    private RecyclerView.Adapter mAdapterFiles;
    private RecyclerView.LayoutManager mLayaoutManagerFiles;

    private String filePath;

    private Switch swtBorrar;

    static int max_photos;
    static int min_photos;


    //Imagenes

    private static Foto PhotoData = new Foto();
    static List<String> Foto =  new ArrayList<>();
    private static List<Foto> multimedia = new ArrayList<>();
    private static List<Files> files = new ArrayList<>();

    Files PerFile = new Files();

    private static List<Uri> ArchivosUris = new ArrayList<>();
    private static List<String> Type_Archivo = new ArrayList<>();
    private static List<String> Name_Archivo = new ArrayList<>();
    private ArrayList<Boolean> archivoChecked= new ArrayList<>();


    private static ImageView noImage;

    FloatingTextButton btnBorrar;


    //Subir archivo

    FloatingTextButton btnArchivo;
    FloatingTextButton btnBorrarArchivo;
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
    FirebaseFirestore BDFireStore= FirebaseFirestore.getInstance();
    //FIRESTORE

    //FECHA Y HORA,Ubicacion
    private static Time today = new Time(Time.getCurrentTimezone());
    private static String Fecha;
    private static String Hora;
    static String created_at;

    private static datetime datatime = new datetime();
    private static Ubication ubication = new Ubication();
    //FECHA Y HORA,

    // Contadores

    static int contUris=0;

    static int restUris=9;

    static int contImg=0;

    int contT=0;
    int contT2=0;
    // Contadores

    static String u;
    static String Observation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);

    

        recibirDatos();
        inicializacionVariables();
//        initRecyclerView();

        txtname = (TextView) findViewById(R.id.txtname);
        txtidevent = (TextView) findViewById(R.id.txtidevent);

        txtname.setText("Actividad: "+ title);
        txtidevent.setText("ID: "+ activity_id);

        edObserv = (EditText) findViewById(R.id.edObserv);
        btnEnviar = (FloatingTextButton) findViewById(R.id.btnEnviar);


        floatingActionsMenu = findViewById(R.id.FloatingActionMenuPrincipal);
//        floatingActionsMenu.setIconAnimated(false);

        actionButton_Take_video = findViewById(R.id.fab_action_2);
        actionButton_Take_photo = findViewById(R.id.fab_action_1);
        actionButton_Upload_PDF = findViewById(R.id.fab_action_3);
        actionButton_Upload_Docx = findViewById(R.id.fab_action_4);
        actionButton_Upload_Video = findViewById(R.id.fab_action_5);
        actionButton_Upload_Audio = findViewById(R.id.fab_action_6);

        actionButton_2_1 = findViewById(R.id.fab_action_2_1);

//        actionButton_Take_video.bringToFront();
//        actionButton_Take_photo.bringToFront();
//        actionButton_Upload_PDF.bringToFront();
//        actionButton_Upload_Docx.bringToFront();
//        actionButton_Upload_Video.bringToFront();
//        actionButton_Upload_Audio.bringToFront();






        mensaje1  = (TextView) findViewById(R.id.txtLat);
        mensaje2  = (TextView) findViewById(R.id.txtLng);

        mensaje1.setVisibility(View.INVISIBLE);
        mensaje2.setVisibility(View.INVISIBLE);


        //Firebase Inicializacion
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dbRef = FirebaseDatabase.getInstance();
//        ref = FirebaseDatabase.getInstance().getReference().child("Eventos").child(idevent).child("observation");
        //Instancias
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        //Fin Instancias
        storageReference = storage.getReference();
        //Fin Firebase Inicializacion

        // FireStore
        mFireStore = FirebaseFirestore.getInstance();

        // FireStore



        if (checkPermissions()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            } else {
                locationStart();
            }
        } else{

        }


        actionButton_Take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarInformacionImagenes();
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

        actionButton_2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Confirmación");
                builder.setMessage("¿Está seguro de que desea borrar lo seleccionado?");
//                                                    StyleableToast.makeText(getApplicationContext(), "Una vez realizada, esta acción no se puede revertir", Toast.LENGTH_SHORT, R.style.warningToast).show();
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



        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observation = edObserv.getText().toString();


                if(!Observation.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Confirmación");
                    builder.setMessage("¿Está seguro?");
                    StyleableToast.makeText(getApplicationContext(), "Una vez realizada, esta acción no se puede revertir", Toast.LENGTH_SHORT, R.style.warningToast).show();
                    // builder.setCancelable(false);
                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            boolean statuss = true;
                            UUID uuid = UUID.randomUUID();
                            u = ""+uuid;

                            Subirdatos();
                            uploadAllImages();
                            uploadAllFiles();



                            Toast.makeText(getApplicationContext(), "Datos ingresados", Toast.LENGTH_SHORT).show();
                            edObserv.setText("");

                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }else{
                    StyleableToast.makeText(getApplicationContext(), "El texto es necesario para poder enviar", Toast.LENGTH_SHORT, R.style.warningToastMiddle).show();
                }

            }
        });

    }

    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(ExtraActivity.this,p);
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



    private void initRecyclerView(){
        int mNoOfColumns = SupervisionActivity.Utility.calculateNoOfColumns(getApplicationContext(), 90);

        mRecyclerView = findViewById(R.id.images_recycler);
//        mLayaoutManager= new GridLayoutManager(this, 4);
        mLayaoutManager= new GridLayoutManager(this,mNoOfColumns);
        mAdapter = new RecyclerViewAdapter(this, mTypeAdapter, ListVideos, mItemChecked, new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Uri mImage, int position) {


                if(mTypeAdapter.get(position).equals("Photo")) {
                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(ExtraActivity.this);
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
        mAdapterFiles.notifyItemRemoved(position);
        contUris--;
    }

    private void startAsyncTask() {
        ExampleAsyncTask task = new ExampleAsyncTask();
        task.execute((contImg+contUris));

    }

    private void GuardarInformacionArchivos() {
        created_at_funct();
        PerFilesArray[contUris].setCreated_at(created_at);

    }

    private void mostrarDialogoOpciones() {


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

    private void uploadVideo(Uri videoUri, final int i) {
        progressDialog= new ProgressDialog(ExtraActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo archivo...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final StorageReference ref = storageReference.child("extra").child("video" + UUID.randomUUID().toString());
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
                                    Subirdatos();
                                    if(contT == contImg && contUris==0 ){

                                        BorrarImagenes();
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

    private void uploadImageGlobal(Bitmap bitmap, final int x) {


//            File fileimagenpos
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] dato = baos.toByteArray();


        final ProgressDialog progressDialog = new ProgressDialog(ExtraActivity.this);
        progressDialog.setTitle("Subiendo....");

//            final StorageReference ref = storageReference.child("x").child("Img" + created_at + UUID.randomUUID().toString());
        final StorageReference ref = storageReference.child("images").child("evidence").child("Img" + created_at + UUID.randomUUID().toString());
        // StorageReference ref = storageReference.child("images/"+UUID.randomUUID().toString());


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
                    Subirdatos();
                    if(contT == contImg && contUris==0 ){
                        BorrarImagenes();
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
        Extra extra = new Extra(created_at, Observation, "extra", activity_id, Lat, Lng, multimedia, files);
        BDFireStore.collection("extra").document(u).set(extra, SetOptions.merge());
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

    private void GuardarInformacion() {

        locationStart();
        created_at_funct();

        datatime.setDate(Fecha);
        datatime.setTime(Hora);

        locationStart();
        ubication.setLat(Lat);
        ubication.setLng(Lng);


        for (int i = 0; i < 6; i++) {
            if (FileImagenArray[i] == nula) {
                locationStart();
                PerFotoArray[i].setCreated_at(created_at);
                PerFotoArray[i].setUbication(ubication);
                PerFotoArray[i].setType("image");
                break;
            }
        }
    }

    private void BorrarImagenes() {

        multimedia = new ArrayList<>();
        contImg=0;
        for (int x=0; x<6; x++) {
            PerFotoArray[x] = new Foto();
            FileImagenArray[x] = nula;
        }

    }

    public void takePhoto() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAPTURE_PHOTO);
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
        String imageName = "Image-" + n + ".jpg";
        String file = dir+imageName;
        File newfile = new File(file);
        try {
            newfile.createNewFile();
        }
        catch (IOException e)
        {
        }

        Uri outputFileUri = FileProvider.getUriForFile(ExtraActivity.this,"com.acadep.acadepsistemas.rso.fileprovider", newfile);


        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);


        filePath = newfile.getPath();


        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
    }

    private void takeVideo() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    private void uploadfileGlobal(Uri ArchivoUri, final int i) {
        progressDialog= new ProgressDialog(ExtraActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo archivo...");
        progressDialog.setProgress(0);
        progressDialog.show();

        int Tpath = ArchivoUri.getPath().length();
        String nombre = ArchivoUri.getPath().substring(9, Tpath);
        PerFilesArray[i].setName(ArchivoUri.getPath());

        final String fileName= "Archivo" + UUID.randomUUID().toString();
        final StorageReference srtreference = storage.getReference();
        srtreference.child("files").child("extra").child(fileName).putFile(ArchivoUri)
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


                                    progressDialog.hide();

                                    if(i == (contUris-1)){
                                        try {
                                            Thread.sleep(10000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        BorrarImagenes();
                                        BorrarFiles();

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
        }

        if(requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {

            Uri videoUri = data.getData();
//            addImage(new File(videoUri.getPath()), 0, "Video");
            addImage(videoUri, 0, "Video");

            Bitmap icon = BitmapFactory.decodeResource(ExtraActivity.this.getResources(),
                    R.drawable.reproductor_multimedia);

            mImageBitmap.add(0, icon);
//            ListVideos.add(0, videoUri);
            contImg++;

        }

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
                    Bitmap Bitnew = redimensionarImagenMaximo(capturedCoolerBitmap, 1600, 1200);

                    contImg++;


                    break;

                default:
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    break;
            }
        }



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

    private void MostrarOpciones() {


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
                        StyleableToast.makeText(getApplicationContext(), "No hay ningún archivo para borrar", Toast.LENGTH_SHORT, R.style.warningToast).show();
                    }else{
                        if(contUris<11){
                            contUris--;
                            ArchivosUris.remove(0);
                            files.remove(0);
                            StyleableToast.makeText(getApplicationContext(), "¡Archivo Borrado con exito!", Toast.LENGTH_SHORT, R.style.doneToast).show();
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



    private void SelecUri(Intent data) {


        if(contUris<10){
            ArchivosUris.add(0, data.getData());
            StyleableToast.makeText(getApplicationContext(), "Archivo agregado con exito \nPuede subir " + (restUris-contUris) + " más", Toast.LENGTH_LONG, R.style.sucessToast).show();

        }else{
            StyleableToast.makeText(getApplicationContext(), "Limite de archivos alcanzado", Toast.LENGTH_LONG, R.style.warningToast).show();
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

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(ExtraActivity.this);
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

//        txtFecha.setText(created_at.substring(0, 9));
//        txtHora.setText(created_at.substring(10, 15));
    }
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
//            selectPDF();
//        }else{
//            StyleableToast.makeText(getApplicationContext(), "Porfavor otorgue los permisos...", Toast.LENGTH_LONG, R.style.warningToast).show();
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
    public static class Localizacion implements LocationListener {
        ExtraActivity mainActivity3;
        public ExtraActivity getMainActivity() {
            return mainActivity3;
        }
        public void setMainActivity(ExtraActivity mainActivity) {
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

    }

    private void created_at_funct() {
        java.util.Date FecAct = new Date();
        Calendar calendar = new GregorianCalendar();
        DateTime dateTime = new DateTime();

//        Fecha =   today.year + "-" + today.month + "-" + today.monthDay;
        Fecha =   calendar.get(Calendar.YEAR) + "-" + dateTime.getMonthOfYear() + "-" + calendar.get(Calendar.DAY_OF_MONTH);

        Hora = calendar.get(Calendar.HOUR)+":" + calendar.get(Calendar.MINUTE);

        created_at = Fecha + "T" +Hora;

    }

    private void recibirDatos() {
        Bundle extras = getIntent().getExtras();
        activity_id = extras.getString("activity_id");

        BDFireStore
                .collection("activities")
                .document(activity_id)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Activity activity = documentSnapshot.toObject(Activity.class);

                title = activity.getTitle();


            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                int Tletra = title.length();
                if(Tletra > 23){
                    title = title.substring(0, 22) + "...";
                }
                txtname.setText("Actividad: "+ title);
                txtidevent.setText("ID: "+ activity_id);
            }
        });

       // Lat = extras.getDouble("Lat");
        //Lng = extras.getDouble("Lng");
    }

    private class  ExampleAsyncTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(contUris>0) {
                progressDialog = new ProgressDialog(ExtraActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setTitle("Subiendo archivos...");
                progressDialog.setProgress(0);
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(Integer... integers) {



//            for (int i = 0; i <integers[0]; i ++){
//                publishProgress((i*100) / integers[0]);
//                try {
//                    Thread.sleep((integers[0]*1000));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }

            return "Subida de archivos terminada!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }


    }

}
