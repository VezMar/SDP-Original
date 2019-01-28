package com.acadep.acadepsistemas.rso.Clases;

import android.Manifest;
import android.app.AlertDialog;
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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.acadepsistemas.seguimiento.Manifest;
import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Data;
import com.acadep.acadepsistemas.rso.model.Extra;
import com.acadep.acadepsistemas.rso.model.Files;
import com.acadep.acadepsistemas.rso.model.Foto;
import com.acadep.acadepsistemas.rso.model.Ubication;
import com.acadep.acadepsistemas.rso.model.datetime;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.muddzdev.styleabletoast.StyleableToast;


import org.joda.time.DateTime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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

    static File [] FileImagenArray = new File[6];
    private static Files PerFilesArray[] = new Files[10];
    private static com.acadep.acadepsistemas.rso.model.Foto PerFotoArray[] = new Foto[6];

    static File nula = new File("/ada/");


    static File fileimagen;
    static File fileimagen2;
    static File fileimagen3;
    static File fileimagen4;
    static File fileimagen5;
    static File fileimagen6;


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


    private Uri filePath;
    static List<String> Foto =  new ArrayList<>();
    private static List<Foto> multimedia = new ArrayList<>();
    private static List<Files> files = new ArrayList<>();

    private static ImageView imageView;
    private static ImageView imageView2;
    private static ImageView imageView3;
    private static ImageView imageView4;
    private static ImageView imageView5;
    private static ImageView imageView6;

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

    // FILES

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

    static int contImg=0;
    // FILES

    static String u;
    static String Observation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);


        recibirDatos();
        inicializacionVariables();


        txtname = (TextView) findViewById(R.id.txtname);
        txtidevent = (TextView) findViewById(R.id.txtidevent);

        txtname.setText("Actividad: "+ title);
        txtidevent.setText("ID: "+ activity_id);

        edObserv = (EditText) findViewById(R.id.edObserv);
        btnEnviar = (FloatingTextButton) findViewById(R.id.btnEnviar);

        btnArchivo = (FloatingTextButton) findViewById(R.id.btnArchivo);
        btnBorrarArchivo = (FloatingTextButton) findViewById(R.id.btnBorrarArchivo);

        btnFoto = (FloatingTextButton) findViewById(R.id.btnFoto);


        btnBorrar = (FloatingTextButton) findViewById(R.id.btnBorrar);



        imageView = (ImageView) findViewById(R.id.imgView);
        imageView2 = (ImageView) findViewById(R.id.imgView2);
        imageView3 = (ImageView) findViewById(R.id.imgView3);
        imageView4 = (ImageView) findViewById(R.id.imgView4);
        imageView5 = (ImageView) findViewById(R.id.imgView5);
        imageView6 = (ImageView) findViewById(R.id.imgView6);

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

        checkBox1 = (CheckBox) findViewById(R.id.Check1);
        checkBox2 = (CheckBox) findViewById(R.id.Check2);
        checkBox3 = (CheckBox) findViewById(R.id.Check3);
        checkBox4 = (CheckBox) findViewById(R.id.Check4);
        checkBox5 = (CheckBox) findViewById(R.id.Check5);
        checkBox6 = (CheckBox) findViewById(R.id.Check6);

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

        locationStart();
        btnArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (ContextCompat.checkSelfPermission(ExtraActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        GuardarInformacionArchivos();
                        mostrarDialogoOpciones();
                    } else {
                        ActivityCompat.requestPermissions(ExtraActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
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

                BorrarFotos();


            }
        });



        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationStart();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(String.valueOf(Manifest.permission.CAMERA)) != PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(ExtraActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                }

                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(ExtraActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);
                } else {
                    locationStart();

                    if(fileimagen !=null && fileimagen2 !=null && fileimagen3 !=null && fileimagen4 !=null && fileimagen5 !=null && fileimagen6 !=null){
                        StyleableToast.makeText(getApplicationContext(), "Ya no puede añadir más fotos", Toast.LENGTH_SHORT, R.style.warningToast).show();
                    }else{
                        locationStart();

                        GuardarInformacion();
                        takePhoto();

                    }
                }
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

    private void BorrarFotos() {


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

    private void uploadAllImages() {

        for(int i=0; i<FileImagenArray.length; i++){
            if(FileImagenArray[i] != nula){
                uploadImageGlobal(FileImagenArray[i], i);
                multimedia.add(PerFotoArray[i]);
            }
        }
    }



    private void uploadImageGlobal(File fileimagenpos, final int x) {
        if (fileimagenpos != null) {

            final ProgressDialog progressDialog = new ProgressDialog(ExtraActivity.this);
            progressDialog.setTitle("Subiendo....");

            final StorageReference ref = storageReference.child("images").child("extra").child("Img" + UUID.randomUUID().toString());
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

                        if(x == (contImg-1) && contUris == 0){

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
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAPTURE_PHOTO);
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

    private void saveImageToGallery(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/ImagenesSeguimiento");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String imageName = "Image-" + n + ".jpg";
        File file = new File(myDir, imageName);


        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            String resizeCoolerImagePath = file.getAbsolutePath();
            out.flush();
            out.close();

            Toast.makeText(getApplicationContext(), "Tu foto se ha guardado exitosamente", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Hubo un error", Toast.LENGTH_SHORT).show();
        }


        if (descision == 0) {
            FileImagenArray[0] = file;
            imageView.setVisibility(View.VISIBLE);
            checkBox1.setVisibility(View.VISIBLE);
        } else if (descision == 1) {
            FileImagenArray[1] = file;
            imageView2.setVisibility(View.VISIBLE);
            checkBox2.setVisibility(View.VISIBLE);
        } else if (descision == 2) {
            FileImagenArray[2] = file;
            imageView3.setVisibility(View.VISIBLE);
            checkBox3.setVisibility(View.VISIBLE);
        } else if (descision == 3) {
            FileImagenArray[3] = file;
            imageView4.setVisibility(View.VISIBLE);
            checkBox4.setVisibility(View.VISIBLE);
        } else if (descision == 4) {
            FileImagenArray[4] = file;
            imageView5.setVisibility(View.VISIBLE);
            checkBox5.setVisibility(View.VISIBLE);
        } else if (descision == 5) {
            FileImagenArray[5] = file;
            imageView6.setVisibility(View.VISIBLE);
            checkBox6.setVisibility(View.VISIBLE);
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
        title = extras.getString("title");

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
