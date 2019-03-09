package com.acadep.acadepsistemas.rso.Clases.Prueba;

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
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.acadep.acadepsistemas.rso.Clases.MainActivity;
import com.acadep.acadepsistemas.rso.Clases.Prueba.fragmentosEvidence.ArchivosFragment;
import com.acadep.acadepsistemas.rso.Clases.Prueba.fragmentosEvidence.MultimediaFragment;
import com.acadep.acadepsistemas.rso.Clases.Prueba.fragmentosEvidence.ObservacionesFragment;
import com.acadep.acadepsistemas.rso.Clases.SupervisionActivity;
import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Configuration;
import com.acadep.acadepsistemas.rso.model.Data;
import com.acadep.acadepsistemas.rso.model.Event_types;
import com.acadep.acadepsistemas.rso.model.Evento;
import com.acadep.acadepsistemas.rso.model.Files;
import com.acadep.acadepsistemas.rso.model.Foto;
import com.acadep.acadepsistemas.rso.model.Ref_event;
import com.acadep.acadepsistemas.rso.model.Total;
import com.acadep.acadepsistemas.rso.model.Ubication;
import com.acadep.acadepsistemas.rso.model.User;
import com.acadep.acadepsistemas.rso.model.Usuario;
import com.acadep.acadepsistemas.rso.model.datetime;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.joda.time.DateTime;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;


public class EvidenceActivity extends AppCompatActivity {

    public EvidenceActivity() {
    }

    public EvidenceActivity(String idevent, String nameEvent, String user_id, String actividad, String trabajador, String name, String start, String end, String idactivity, String description, String title_event, ArrayList<String> tools, int ava, int number, String unit, String deleted, boolean checked_NoAplica, String observation, Ref_event ref_event, String header, String uuids, int contT, int contT2, String username, Query mQuery, int contUris, int restUris, List<Uri> archivosUris, List<Files> files, Files perFile, List<String> type_Archivo, List<String> name_Archivo, ArrayList<Boolean> archivoChecked, ProgressDialog progressDialog, ArrayList<String> mTypeAdapter, ArrayList<Uri> listVideos, ArrayList<Boolean> mItemChecked, ArrayList<Bitmap> mImageBitmap, List<Foto> multimedia, int contImg, Foto photoData, String edObserv, String edpercentage, TextView txtAvance, TextView txtTotal, FloatingActionButton actionButton_Enviar, TextView txtEstado, FirebaseFirestore BDFireStore, FirebaseAuth mAuth, FirebaseStorage storage, StorageReference storageReference, FirebaseDatabase dbRef, List<Event_types> event_types, boolean tbefore, boolean tduring, boolean tafter, boolean during_complete, boolean before_complete, int max_photos, int min_photos, String estado, datetime datatime, Ubication ubication, Time today, String fecha, String hora, String created_at, BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener) {
        this.idevent = idevent;
        this.nameEvent = nameEvent;
        this.user_id = user_id;
        this.actividad = actividad;
        this.trabajador = trabajador;
        this.name = name;
        this.start = start;
        this.end = end;
        this.idactivity = idactivity;
        this.description = description;
        this.title_event = title_event;
        this.tools = tools;
        this.ava = ava;
        this.number = number;
        this.unit = unit;
        this.deleted = deleted;
        this.checked_NoAplica = checked_NoAplica;
        Observation = observation;
        this.ref_event = ref_event;
        this.header = header;
        this.uuids = uuids;
        this.contT = contT;
        this.contT2 = contT2;
        this.username = username;
        this.mQuery = mQuery;
        this.contUris = contUris;
        this.restUris = restUris;
        ArchivosUris = archivosUris;
        this.files = files;
        PerFile = perFile;
        Type_Archivo = type_Archivo;
        Name_Archivo = name_Archivo;
        this.archivoChecked = archivoChecked;
        this.progressDialog = progressDialog;
        this.mTypeAdapter = mTypeAdapter;
        ListVideos = listVideos;
        this.mItemChecked = mItemChecked;
        this.mImageBitmap = mImageBitmap;
        this.multimedia = multimedia;
        this.contImg = contImg;
        PhotoData = photoData;
        this.edObserv = edObserv;
        this.edpercentage = edpercentage;
        this.txtAvance = txtAvance;
        this.txtTotal = txtTotal;
        this.actionButton_Enviar = actionButton_Enviar;
        this.txtEstado = txtEstado;
        this.BDFireStore = BDFireStore;
        this.mAuth = mAuth;
        this.storage = storage;
        this.storageReference = storageReference;
        this.dbRef = dbRef;
        this.event_types = event_types;
        Tbefore = tbefore;
        Tduring = tduring;
        Tafter = tafter;
        this.during_complete = during_complete;
        this.before_complete = before_complete;
        this.max_photos = max_photos;
        this.min_photos = min_photos;
        this.estado = estado;
        this.datatime = datatime;
        this.ubication = ubication;
        this.today = today;
        Fecha = fecha;
        Hora = hora;
        this.created_at = created_at;
        this.mOnNavigationItemSelectedListener = mOnNavigationItemSelectedListener;
    }

    //---------------------------------------------------------------
    // Datos del evento

    private  String idevent;
   private static String nameEvent;
   private static String user_id;
   private static String actividad;
   private static String trabajador;
   private static String name;
   private static String start;
   private static String end;
   private static String idactivity;
   private static String description;
   private static String title_event;
   private static ArrayList<String> tools;
   private static int ava;
   private static int number;
   private static String unit;
   private static String deleted;


        // Datos par enviar
       private static boolean checked_NoAplica = false;
       private static String Observation;

       private static Ref_event ref_event = new Ref_event();
       private static String header;
       private static String uuids;

       private static int contT=0;
       private static int contT2=0;

       private static String username;

        private com.google.firebase.firestore.Query mQuery;
        // Datos par enviar

    // Datos del evento
//---------------------------------------------------------------
    // Datos de los archivos

    private static  int contUris = 0;
    private static  int restUris = 9;
   private static List<Uri> ArchivosUris = new ArrayList<>();
   private static List<Files> files = new ArrayList<>();
    private static  Files PerFile = new Files();

   private static List<String> Type_Archivo = new ArrayList<>();
   private static List<String> Name_Archivo = new ArrayList<>();
    private  static  ArrayList<Boolean> archivoChecked= new ArrayList<>();

    private ProgressDialog progressDialog;

    // Datos de los archivos
//---------------------------------------------------------------
    // Datos de la multimedia
   private static ArrayList<String> mTypeAdapter = new ArrayList<>();
   private static ArrayList<Uri> ListVideos = new ArrayList<>();
   private static ArrayList<Boolean> mItemChecked = new ArrayList<>();
   private static ArrayList<Bitmap> mImageBitmap = new ArrayList<>();
   private static List<Foto> multimedia = new ArrayList<>();
   private static int contImg = 0;

   private static Foto PhotoData = new Foto();

    static double Lat;
    static double Lng;
    // Datos de la multimedia
//---------------------------------------------------------------
    // Datos de la observación
        //Declaración de componentes
            private static String edObserv = "";
            private static String edpercentage;
            private TextView txtAvance;
            private TextView txtTotal;
        //Declaración de componentes

    // Datos de la observación

//---------------------------------------------------------------
    // Componentes de EvidenceActivity
    private com.github.clans.fab.FloatingActionButton actionButton_Enviar;
    private TextView txtEstado;
    // Componentes de EvidenceActivity
//---------------------------------------------------------------
    //Firebase - Firestore

    FirebaseFirestore BDFireStore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    FirebaseDatabase dbRef;

    //Firebase - Firestore
//---------------------------------------------------------------
    //Configuration
     private List<Event_types> event_types;
    private boolean Tbefore;
    private boolean Tduring;
    private boolean Tafter;


    private boolean during_complete;
    private boolean before_complete;

     int max_photos;
     int min_photos;

        private static  String estado = "before";

    //Configuration
//---------------------------------------------------------------
    //Tiempo
   private static datetime datatime = new datetime();
   private static Ubication ubication = new Ubication();

   private static Time today = new Time(Time.getCurrentTimezone());
   private static String Fecha;
   private static String Hora;
    private  static String created_at;
    //Tiempo
//---------------------------------------------------------------
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment mifragment = null;
            Boolean FragmentoSeleccionado=false;
            int id = item.getItemId();


                if (id ==R.id.navigation_observaciones){
                    mifragment = new ObservacionesFragment();
                    FragmentoSeleccionado=true;
                }else if (id == R.id.navigation_multmedia) {
                    mifragment = new MultimediaFragment();
                    FragmentoSeleccionado = true;
                }else if (id ==  R.id.navigation_archivos) {
                    mifragment = new ArchivosFragment();
                    FragmentoSeleccionado = true;
                }



            if(FragmentoSeleccionado==true){

                Bundle bundle = new Bundle();
                bundle.putString("idEvento", idevent);
                bundle.putString("nameEvent",nameEvent);
                bundle.putString("actividad",actividad);
                bundle.putString("user_id",user_id);
                bundle.putString("trabajador",trabajador);
                bundle.putString("start",start);
                bundle.putString("end",end);
                bundle.putString("title_event", title_event);
                bundle.putString("idactivity",idactivity);
                bundle.putString("description",description);
                bundle.putStringArrayList("tools",tools);
                bundle.putString("deleted",deleted);
                bundle.putInt("ava",ava);
                bundle.putInt("number",number);
                bundle.putString("unit",unit);
                bundle.putBoolean("during_complete",during_complete);
                bundle.putBoolean("before_complete",before_complete );

                mifragment.setArguments(bundle);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenedor_Evidence, mifragment)
                        .commit();

                item.setChecked(true);
                getSupportActionBar().setTitle(item.getTitle());

            }
            return FragmentoSeleccionado;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evidence);



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        recibirDatos();
        ChequeoConfiguration();
        actionButton_Enviar = findViewById(R.id.fab_action_enviar);



        Fragment mifragment = null;
//                mifragment = new EventosFragment();
        mifragment = new ObservacionesFragment();

        Bundle bundle = new Bundle();
        bundle.putString("idEvento", idevent);
        bundle.putString("nameEvent",nameEvent);
        bundle.putString("actividad",actividad);
        bundle.putString("user_id",user_id);
        bundle.putString("trabajador",trabajador);
        bundle.putString("start",start);
        bundle.putString("end",end);
        bundle.putString("title_event", title_event);
        bundle.putString("idactivity",idactivity);
        bundle.putString("description",description);
        bundle.putStringArrayList("tools",tools);
        bundle.putString("deleted",deleted);
        bundle.putInt("ava",ava);
        bundle.putInt("number",number);
        bundle.putString("unit",unit);
        bundle.putBoolean("during_complete",during_complete);
        bundle.putBoolean("before_complete",before_complete );

        mifragment.setArguments(bundle);



        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedor_Evidence, mifragment)
                .commit();

        actionButton_Enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                checked_NoAplica = check_NoAplica.isChecked();
                final String advanceed = String.valueOf(edpercentage);
                if(!advanceed.equals("")) {
                    Observation = ""+ EvidenceActivity.getEdObserv();
                    if (!Observation.equals("")) {
                        if (contImg >= min_photos || checked_NoAplica == true) {
                            if (checked_NoAplica==true && contImg == 0 || checked_NoAplica==false && contImg >= min_photos  ){
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
                                            ref_event.setName(title_event);


                                            if ((estado).equals("before")) {


                                                boolean before = true;


                                                header = "before";
                                                UUID uuid = UUID.randomUUID();
                                                uuids = "" + uuid;


                                                BDFireStore.collection("events").document(idevent).update("before_complete", true);
                                                before_complete = true;

                                                if (checked_NoAplica==true) {
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

                                                edpercentage = EvidenceActivity.getEdpercentage();
                                                if(edpercentage==null){
                                                    edpercentage="0";
                                                }
                                                ava = EvidenceActivity.getAva();
                                                if (ava <= Integer.parseInt(edpercentage)) {
                                                    if (Integer.parseInt(String.valueOf(edpercentage)) <= number) {

                                                        ava = Integer.parseInt(String.valueOf(edpercentage));

                                                        boolean during = true;

                                                        header = "during";
                                                        UUID uuid = UUID.randomUUID();
                                                        uuids = "" + uuid;

                                                        if (ava == number) {
                                                            BDFireStore.collection("events").document(idevent).update("during_complete", true);
                                                            during_complete = true;
                                                        }

                                                        if (checked_NoAplica==true) {
                                                            if (contUris == 0) {
                                                                Subirdatos();
                                                            } else {
                                                                uploadAllFiles();
                                                            }
                                                        } else {
                                                            uploadAllImages();
                                                            uploadAllFiles();
                                                        }


//                                                        terminado = 2;

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

                                                edObserv = "";
                                                if (checked_NoAplica==true) {
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
        });

    }

    private void Subirdatos() {
        created_at_funct();

        User user = new User();
        user.setId(mAuth.getUid());
        user.setName(username);

        checked_NoAplica = false;



        Total total = new Total();
        total.setNumber(number);
        total.setUnit(unit);
        Data data = new Data(ava, created_at, Observation, header, total, false , user, ref_event, Lat, Lng, multimedia, files);
        BDFireStore.collection("evidence").document(uuids).set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "Información subida exitosamente", Toast.LENGTH_SHORT  ). show();
                edObserv= "";
                EventoTerminado();
                ChequeoDeVariables();
                BorrarFiles();
                BorrarImagenes();

                if (before_complete==true && during_complete==false){
                    estado = "Tarea en curso";
//                    txtEstado.setText("Evidencia: Durante");
//                    txtAvance.setVisibility(View.VISIBLE);
//                    edpercentage.setVisibility(View.VISIBLE);
//                    txtTotal.setVisibility(View.VISIBLE);
//                    txtAyuda.setVisibility(View.VISIBLE);
                }else if (during_complete==true || ava ==number){
//                    txtAvance.setVisibility(View.INVISIBLE);
//                    edpercentage.setVisibility(View.INVISIBLE);
//                    txtTotal.setVisibility(View.INVISIBLE);
//                    txtAyuda.setVisibility(View.INVISIBLE);
                    estado = "after";
//                    txtEstado.setText("Fin de la tarea");


                }
            }
        });

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

    private void uploadAllFiles() {
//        uploadfileGlobal(ArchivoUri);


        for (int i = 0; i<ArchivosUris.size(); i++){
            if ( ArchivosUris.get(i) != null){
                uploadfileGlobal(ArchivosUris.get(i), i);
            }

        }

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
        progressDialog= new ProgressDialog(EvidenceActivity.this);
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

    private void uploadfileGlobal(Uri ArchivoUri, final int i) {
        progressDialog= new ProgressDialog(EvidenceActivity.this);
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

    private void BorrarFiles() {

        for (int i=0; i<contUris; i++) {
            ArchivosUris.remove(0);
            Name_Archivo.remove(0);
            Type_Archivo.remove(0);
            archivoChecked.remove(0);
            files.remove(0);
//            mAdapterFiles.notifyItemRemoved(0);
        }

        contUris=0;
//        files = new ArrayList<>();
//        archivoChecked = new ArrayList<>();
//        Type_Archivo = new ArrayList<>();
//        Name_Archivo = new ArrayList<>();
//        ArchivosUris = new ArrayList<>();
    }

    private void BorrarImagenes() {

        multimedia = new ArrayList<>();
        PhotoData = new Foto();

        for (int x=0; x<contImg; x++) {
            ListVideos.remove(0);
            mTypeAdapter.remove(0);
            mImageBitmap.remove(0);
//            mAdapter.notifyItemRemoved(0);
        }

        contImg=0;
        contT=0;
        contT2=0;

//        mTypeAdapter = new ArrayList<>();
//        mImageBitmap = new ArrayList<>();
//        ListImages = new ArrayList<>();
//        ListVideos = new ArrayList<>();
    }

    private void recibirDatos() {


        Bundle extras = getIntent().getExtras();
        idevent = extras.getString("idEvento");
        nameEvent = extras.getString("nameEvent");
        actividad = extras.getString("actividad");
        user_id = extras.getString("user_id");
        trabajador= extras.getString("trabajador");
        start= extras.getString("start");
        end= extras.getString("end");
        title_event = extras.getString("title_event");
        idactivity= extras.getString("idactivity");
        description= extras.getString("description");
        tools = extras.getStringArrayList("tools");
        deleted = extras.getString("deleted");
        ava = extras.getInt("ava");
        number = extras.getInt("number");
        unit = extras.getString("unit");
        during_complete = extras.getBoolean("during_complete");
        before_complete = extras.getBoolean("before_complete");
        //active=false;


        EvidenceActivity.setIdactivity(idactivity);
        EvidenceActivity.setAva(ava);

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

    private void EventoTerminado() {

        if (estado.equals("after")|| estado.equals("during") && Tafter==false && ava == number || estado.equals("before") && Tduring==false && Tafter==false || during_complete == true && before_complete==true && estado=="after" && ava == number ) {


            BDFireStore.collection("events").document(idevent).update("color", "#27ae60");

//            mQuery = BDFireStore.collection("events")
//                    .whereEqualTo("user_id", mAuth.getUid())
//                    .whereEqualTo("activity_id", activity_id)
//                    .whereEqualTo("active", true);
            BDFireStore.collection("events").document(idevent).update("active", false);

//            BDFireStore.collection("events").document(idevent).update("active", false).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    mQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                contEventos = 0;
//                                for (DocumentSnapshot document : task.getResult()) {
//                                    contEventos++;
//                                }
//                                if (contEventos == 0) {
//                                    BDFireStore.collection("activities").document("" + activity_id).update("users." + mAuth.getUid(), false);
//                                    Toast.makeText(SupervisionActivity.this, "contEventos" + contEventos, Toast.LENGTH_SHORT).show();
//                                    mQuery2 = BDFireStore.collection("activities")
//                                            .whereEqualTo("users." + mAuth.getUid(), true)
//                                            .whereEqualTo("project_id", project_id);
//
//
//                                    mQuery2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                            if (task.isSuccessful()) {
//                                                contActivity = 0;
//                                                for (DocumentSnapshot document : task.getResult()) {
//                                                    contActivity++;
//                                                }
//                                                Toast.makeText(SupervisionActivity.this, "contActivity: " + contActivity, Toast.LENGTH_SHORT).show();
//                                                if (contActivity == 0) {
//                                                    BDFireStore.collection("projects").document("" + project_id).update("users." + mAuth.getUid(), false).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<Void> task) {
//
//                                                            Toast.makeText(getApplicationContext(), "Evento terminado", Toast.LENGTH_SHORT).show();
//
//
//                                                            NotificationCompat.Builder mBuilder;
//                                                            NotificationManager mNotifyMgr = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
//
//                                                            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                                                            String NOTIFICACION_CHANNEL_ID = "com.acadep.acadepsistemas.rso.test";
//
//                                                            int icono = R.mipmap.ic_launcher;
//                                                            Intent intent = new Intent(SupervisionActivity.this, MainActivity.class);
//                                                            PendingIntent pendingIntent = PendingIntent.getActivity(SupervisionActivity.this, 0, intent, 0);
//
//                                                            NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICACION_CHANNEL_ID);
//
//                                                            notiBuilder.setAutoCancel(true)
//                                                                    .setDefaults(Notification.DEFAULT_ALL)
//                                                                    .setWhen(System.currentTimeMillis())
//                                                                    .setSmallIcon(R.drawable.rso)
//                                                                    .setContentTitle("Evento terminado")
//                                                                    .setContentText(nameEvent + " ha finalizado")
//                                                                    .setContentInfo("info")
//                                                                    .setAutoCancel(true)
//                                                                    .setContentIntent(pendingIntent);
//
//
//                                                            notificationManager.notify(new Random().nextInt(), notiBuilder.build());
//
//                                                            startActivity(new Intent(SupervisionActivity.this, MainActivity.class));
//                                                        }
//                                                    });
//                                                }
//
//                                            } else {
//                                                Log.d("<E> ActivityFragment:", " Error getting documents: ", task.getException());
//                                            }
//                                        }
//                                    });
//                                }
//                            } else {
//                                Log.d("<E> en EventosFragment:", " Error getting documents: ", task.getException());
//                            }
//                        }
//                    });
//                }
//            });



            Toast.makeText(getApplicationContext(), "Evento terminado", Toast.LENGTH_SHORT  ).show();


            NotificationCompat.Builder mBuilder;
            NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            String NOTIFICACION_CHANNEL_ID = "com.acadep.acadepsistemas.rso.test";

            int icono = R.mipmap.ic_launcher;
            Intent intent = new Intent(EvidenceActivity.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(EvidenceActivity.this, 0,intent, 0);

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

            startActivity(new Intent(EvidenceActivity.this, MainActivity.class));





        }
    }

    private void ChequeoDeVariables() {



//        if(ava ==0 && Tbefore==true && before_complete == false){
        if(Tbefore==true && before_complete == false){
//            txtAvance.setVisibility(View.INVISIBLE);
//            edpercentage.setVisibility(View.INVISIBLE);
//            txtTotal.setVisibility(View.INVISIBLE);
//            txtAyuda.setVisibility(View.INVISIBLE);

            ava = 0;

            estado = "before";
//            txtEstado.setText("Evidencia: antes");
        }else {


//            if (ava >= 1 && ava < number && Tduring == true && during_complete == false || Tbefore == false && Tduring == true && during_complete == false|| during_complete == false && ava >= 1 || during_complete == false && Tbefore==true && before_complete==true) {
            if (Tduring == true && during_complete==false){
//                edpercentage.setText(""+ava);


//                txtAvance.setVisibility(View.VISIBLE);
//                edpercentage.setVisibility(View.VISIBLE);
//                txtTotal.setVisibility(View.VISIBLE);
//                txtAyuda.setVisibility(View.VISIBLE);



                estado = "during";
//                txtEstado.setText("Evidencia: Durante");
            }else {

//                if (ava == number && Tafter == true && during_complete == true || Tduring == false && Tafter == true) {
                if (Tafter == true && during_complete == true) {
//                    txtAvance.setVisibility(View.INVISIBLE);
//                    edpercentage.setVisibility(View.INVISIBLE);
//                    txtTotal.setVisibility(View.INVISIBLE);
//                    txtAyuda.setVisibility(View.INVISIBLE);



                    estado = "after";
//                    txtEstado.setText("Evidencia: Después");
                }
            }
        }




    }

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        EvidenceActivity.Localizacion Local = new Localizacion();
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
//        mensaje1.setText("Localización agregada");
//        mensaje2.setText("");

//        created_at_funct();

        //txtFecha.setText(created_at.substring(0, 9));
        //txtHora.setText(created_at.substring(10, 15));
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
//                    mensaje2.setText("Mi direccion es: \n"
//                            + DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* Aqui empieza la Clase Localizacion */
    public static class Localizacion implements LocationListener {
        EvidenceActivity mainActivity3;
        public EvidenceActivity getMainActivity() {
            return mainActivity3;
        }
        public void setMainActivity(EvidenceActivity mainActivity) {
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
//            this.mainActivity3.setLocation(loc);
        }
        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
//            mensaje1.setText("GPS Desactivado");
        }
        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
//            mensaje1.setText("GPS Activado");
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

        public void setMainActivity(FragmentActivity activity) {
        }
    }

    public String getIdevent() {
        return idevent;
    }

    public void setIdevent(String idevent) {
        this.idevent = idevent;
    }

    public static String getNameEvent() {
        return nameEvent;
    }

    public static void setNameEvent(String nameEvent) {
        EvidenceActivity.nameEvent = nameEvent;
    }

    public static String getUser_id() {
        return user_id;
    }

    public static void setUser_id(String user_id) {
        EvidenceActivity.user_id = user_id;
    }

    public static String getActividad() {
        return actividad;
    }

    public static void setActividad(String actividad) {
        EvidenceActivity.actividad = actividad;
    }

    public static String getTrabajador() {
        return trabajador;
    }

    public static void setTrabajador(String trabajador) {
        EvidenceActivity.trabajador = trabajador;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        EvidenceActivity.name = name;
    }

    public static String getStart() {
        return start;
    }

    public static void setStart(String start) {
        EvidenceActivity.start = start;
    }

    public static String getEnd() {
        return end;
    }

    public static void setEnd(String end) {
        EvidenceActivity.end = end;
    }

    public static String getIdactivity() {
        return idactivity;
    }

    public static void setIdactivity(String idactivity) {
        EvidenceActivity.idactivity = idactivity;
    }

    public static String getDescription() {
        return description;
    }

    public static void setDescription(String description) {
        EvidenceActivity.description = description;
    }

    public static String getTitle_event() {
        return title_event;
    }

    public static void setTitle_event(String title_event) {
        EvidenceActivity.title_event = title_event;
    }

    public static ArrayList<String> getTools() {
        return tools;
    }

    public static void setTools(ArrayList<String> tools) {
        EvidenceActivity.tools = tools;
    }

    public static int getAva() {
        return ava;
    }

    public static void setAva(int ava) {
        EvidenceActivity.ava = ava;
    }

    public static int getNumber() {
        return number;
    }

    public static void setNumber(int number) {
        EvidenceActivity.number = number;
    }

    public static String getUnit() {
        return unit;
    }

    public static void setUnit(String unit) {
        EvidenceActivity.unit = unit;
    }

    public static String getDeleted() {
        return deleted;
    }

    public static void setDeleted(String deleted) {
        EvidenceActivity.deleted = deleted;
    }

    public static boolean isChecked_NoAplica() {
        return checked_NoAplica;
    }

    public static void setChecked_NoAplica(boolean checked_NoAplica) {
        EvidenceActivity.checked_NoAplica = checked_NoAplica;
    }

    public static String getObservation() {
        return Observation;
    }

    public static void setObservation(String observation) {
        Observation = observation;
    }

    public static Ref_event getRef_event() {
        return ref_event;
    }

    public static void setRef_event(Ref_event ref_event) {
        EvidenceActivity.ref_event = ref_event;
    }

    public static String getHeader() {
        return header;
    }

    public static void setHeader(String header) {
        EvidenceActivity.header = header;
    }

    public static String getUuids() {
        return uuids;
    }

    public static void setUuids(String uuids) {
        EvidenceActivity.uuids = uuids;
    }

    public static int getContT() {
        return contT;
    }

    public static void setContT(int contT) {
        EvidenceActivity.contT = contT;
    }

    public static int getContT2() {
        return contT2;
    }

    public static void setContT2(int contT2) {
        EvidenceActivity.contT2 = contT2;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        EvidenceActivity.username = username;
    }

    public Query getmQuery() {
        return mQuery;
    }

    public void setmQuery(Query mQuery) {
        this.mQuery = mQuery;
    }

    public static int getContUris() {
        return contUris;
    }

    public static void setContUris(int contUris) {
        EvidenceActivity.contUris = contUris;
    }

    public static int getRestUris() {
        return restUris;
    }

    public static void setRestUris(int restUris) {
        EvidenceActivity.restUris = restUris;
    }

    public static List<Uri> getArchivosUris() {
        return ArchivosUris;
    }

    public static void setArchivosUris(List<Uri> archivosUris) {
        ArchivosUris = archivosUris;
    }

    public static List<Files> getFiles() {
        return files;
    }

    public static void setFiles(List<Files> files) {
        EvidenceActivity.files = files;
    }

    public static Files getPerFile() {
        return PerFile;
    }

    public static void setPerFile(Files perFile) {
        PerFile = perFile;
    }

    public static List<String> getType_Archivo() {
        return Type_Archivo;
    }

    public static void setType_Archivo(List<String> type_Archivo) {
        Type_Archivo = type_Archivo;
    }

    public static List<String> getName_Archivo() {
        return Name_Archivo;
    }

    public static void setName_Archivo(List<String> name_Archivo) {
        Name_Archivo = name_Archivo;
    }

    public static ArrayList<Boolean> getArchivoChecked() {
        return archivoChecked;
    }

    public static void setArchivoChecked(ArrayList<Boolean> archivoChecked) {
        EvidenceActivity.archivoChecked = archivoChecked;
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    public static ArrayList<String> getmTypeAdapter() {
        return mTypeAdapter;
    }

    public static void setmTypeAdapter(ArrayList<String> mTypeAdapter) {
        EvidenceActivity.mTypeAdapter = mTypeAdapter;
    }

    public static ArrayList<Uri> getListVideos() {
        return ListVideos;
    }

    public static void setListVideos(ArrayList<Uri> listVideos) {
        ListVideos = listVideos;
    }

    public static ArrayList<Boolean> getmItemChecked() {
        return mItemChecked;
    }

    public static void setmItemChecked(ArrayList<Boolean> mItemChecked) {
        EvidenceActivity.mItemChecked = mItemChecked;
    }

    public static ArrayList<Bitmap> getmImageBitmap() {
        return mImageBitmap;
    }

    public static void setmImageBitmap(ArrayList<Bitmap> mImageBitmap) {
        EvidenceActivity.mImageBitmap = mImageBitmap;
    }

    public static List<Foto> getMultimedia() {
        return multimedia;
    }

    public static void setMultimedia(List<Foto> multimedia) {
        EvidenceActivity.multimedia = multimedia;
    }

    public static int getContImg() {
        return contImg;
    }

    public static void setContImg(int contImg) {
        EvidenceActivity.contImg = contImg;
    }

    public static Foto getPhotoData() {
        return PhotoData;
    }

    public static void setPhotoData(Foto photoData) {
        PhotoData = photoData;
    }

    public static double getLat() {
        return Lat;
    }

    public static void setLat(double lat) {
        Lat = lat;
    }

    public static double getLng() {
        return Lng;
    }

    public static void setLng(double lng) {
        Lng = lng;
    }

    public static String getEdObserv() {
        return edObserv;
    }


    public static void setEdObserv(String edObserv) {
        EvidenceActivity.edObserv = edObserv;
    }

    public static String getEdpercentage() {
        return edpercentage;
    }

    public static void setEdpercentage(String edpercentage) {
        EvidenceActivity.edpercentage = edpercentage;
    }

    public TextView getTxtAvance() {
        return txtAvance;
    }

    public void setTxtAvance(TextView txtAvance) {
        this.txtAvance = txtAvance;
    }

    public TextView getTxtTotal() {
        return txtTotal;
    }

    public void setTxtTotal(TextView txtTotal) {
        this.txtTotal = txtTotal;
    }

    public FloatingActionButton getActionButton_Enviar() {
        return actionButton_Enviar;
    }

    public void setActionButton_Enviar(FloatingActionButton actionButton_Enviar) {
        this.actionButton_Enviar = actionButton_Enviar;
    }

    public TextView getTxtEstado() {
        return txtEstado;
    }

    public void setTxtEstado(TextView txtEstado) {
        this.txtEstado = txtEstado;
    }

    public FirebaseFirestore getBDFireStore() {
        return BDFireStore;
    }

    public void setBDFireStore(FirebaseFirestore BDFireStore) {
        this.BDFireStore = BDFireStore;
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public FirebaseStorage getStorage() {
        return storage;
    }

    public void setStorage(FirebaseStorage storage) {
        this.storage = storage;
    }

    public StorageReference getStorageReference() {
        return storageReference;
    }

    public void setStorageReference(StorageReference storageReference) {
        this.storageReference = storageReference;
    }

    public FirebaseDatabase getDbRef() {
        return dbRef;
    }

    public void setDbRef(FirebaseDatabase dbRef) {
        this.dbRef = dbRef;
    }

    public List<Event_types> getEvent_types() {
        return event_types;
    }

    public void setEvent_types(List<Event_types> event_types) {
        this.event_types = event_types;
    }

    public boolean isTbefore() {
        return Tbefore;
    }

    public void setTbefore(boolean tbefore) {
        Tbefore = tbefore;
    }

    public boolean isTduring() {
        return Tduring;
    }

    public void setTduring(boolean tduring) {
        Tduring = tduring;
    }

    public boolean isTafter() {
        return Tafter;
    }

    public void setTafter(boolean tafter) {
        Tafter = tafter;
    }

    public boolean isDuring_complete() {
        return during_complete;
    }

    public void setDuring_complete(boolean during_complete) {
        this.during_complete = during_complete;
    }

    public boolean isBefore_complete() {
        return before_complete;
    }

    public void setBefore_complete(boolean before_complete) {
        this.before_complete = before_complete;
    }

    public int getMax_photos() {
        return max_photos;
    }

    public void setMax_photos(int max_photos) {
        this.max_photos = max_photos;
    }

    public int getMin_photos() {
        return min_photos;
    }

    public void setMin_photos(int min_photos) {
        this.min_photos = min_photos;
    }

    public static String getEstado() {
        return estado;
    }

    public static void setEstado(String estado) {
        EvidenceActivity.estado = estado;
    }

    public static datetime getDatatime() {
        return datatime;
    }

    public static void setDatatime(datetime datatime) {
        EvidenceActivity.datatime = datatime;
    }

    public static Ubication getUbication() {
        return ubication;
    }

    public static void setUbication(Ubication ubication) {
        EvidenceActivity.ubication = ubication;
    }

    public static Time getToday() {
        return today;
    }

    public static void setToday(Time today) {
        EvidenceActivity.today = today;
    }

    public static String getFecha() {
        return Fecha;
    }

    public static void setFecha(String fecha) {
        Fecha = fecha;
    }

    public static String getHora() {
        return Hora;
    }

    public static void setHora(String hora) {
        Hora = hora;
    }

    public static String getCreated_at() {
        return created_at;
    }

    public static void setCreated_at(String created_at) {
        EvidenceActivity.created_at = created_at;
    }

    public BottomNavigationView.OnNavigationItemSelectedListener getmOnNavigationItemSelectedListener() {
        return mOnNavigationItemSelectedListener;
    }

    public void setmOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener) {
        this.mOnNavigationItemSelectedListener = mOnNavigationItemSelectedListener;
    }
}
