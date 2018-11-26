package com.example.acadepsistemas.seguimiento.Clases;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.bottomappbar.BottomAppBarTopEdgeTreatment;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acadepsistemas.seguimiento.Fragmentos.EventosFragment;
import com.example.acadepsistemas.seguimiento.Fragmentos.SupervisionFragment;
//import com.example.acadepsistemas.seguimiento.Manifest;
import com.example.acadepsistemas.seguimiento.R;
import com.example.acadepsistemas.seguimiento.model.Data;
import com.example.acadepsistemas.seguimiento.model.Evento;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.github.chrisbanes.photoview.PhotoView;
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


import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.UUID;

public class SupervisionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    private static final String CARPETA_PRINCIPAL= "DCIM/";
    private static final String CARPETA_IMAGEN = "Camera";
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;

    static File fileimagen;
    static File fileimagen2;
    static File fileimagen3;
    static File fileimagen4;
    static File fileimagen5;


   static Bitmap capturedCoolerBitmap;



    private static final int REQUEST_PERM_WRITE_STORAGE = 102;
    private static final int CAPTURE_PHOTO = 104;

//Varibales X
    Button btnEnviar;
    private Button btnFoto;
    private Button btnFoto2;
    private Button btnFoto3;
    private Button btnFoto4;
    private Button btnFoto5;



    ListView listView;


    static int conteo= 0;
    static int descision;

    static String idevent;
    EditText edObserv;
    static TextView txtEstado;

    boolean statusChange;


    public String rolesUser;
    static String estado= "before";
    static String actividad;

//Variables Fotos
    private Uri filePath;
    private static ImageView imageView;
    private static ImageView imageView2;
    private static ImageView imageView3;
    private static ImageView imageView4;
    private static ImageView imageView5;

    private static ImageView noImage;

    Button btnBorrar;
    Button btnBorrar2;
    Button btnBorrar3;
    Button btnBorrar4;
    Button btnBorrar5;



    static ImageView[] valores = new ImageView[]{imageView, imageView2, imageView3, imageView4, imageView5};
    //FIREBASE

    FirebaseAuth mAuth;
    FirebaseStorage storage;

    DatabaseReference mDatabase;
    DatabaseReference ref;

    StorageReference storageReference;

    //FIRESTORE
    private FirebaseFirestore mFireStore;
    private Query mQuery;



    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervision);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recibirDatos();
        //Inicializacion de varibales
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        txtEstado = (TextView) findViewById(R.id.txtEstado);

        txtEstado.setText("Antes de la Actividad");


        edObserv = (EditText) findViewById(R.id.edObserv);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);

        btnFoto = (Button) findViewById(R.id.btnFoto);
        btnFoto2 = (Button) findViewById(R.id.btnFoto2);
        btnFoto3 = (Button) findViewById(R.id.btnFoto3);
        btnFoto4 = (Button) findViewById(R.id.btnFoto4);
        btnFoto5 = (Button) findViewById(R.id.btnFoto5);

        btnBorrar = (Button) findViewById(R.id.btnBorrar);
        btnBorrar2 = (Button) findViewById(R.id.btnBorrar2);
        btnBorrar3 = (Button) findViewById(R.id.btnBorrar3);
        btnBorrar4 = (Button) findViewById(R.id.btnBorrar4);
        btnBorrar5 = (Button) findViewById(R.id.btnBorrar5);


        imageView = (ImageView) findViewById(R.id.imgView);
        imageView2 = (ImageView) findViewById(R.id.imgView2);
        imageView3 = (ImageView) findViewById(R.id.imgView3);
        imageView4 = (ImageView) findViewById(R.id.imgView4);
        imageView5 = (ImageView) findViewById(R.id.imgView5);

        noImage=imageView5;

        //Firebase Inicializacion
        mDatabase = FirebaseDatabase.getInstance().getReference();
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


        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
                fileimagen=null;
                Toast.makeText(getApplicationContext(),"Borrada con exito", Toast.LENGTH_SHORT).show();



            }
        });

        btnBorrar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView2.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
                fileimagen2=null;
                Toast.makeText(getApplicationContext(),"Borrada con exito", Toast.LENGTH_SHORT).show();

            }
        });

        btnBorrar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView3.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
                fileimagen3=null;
                Toast.makeText(getApplicationContext(),"Borrada con exito", Toast.LENGTH_SHORT).show();
            }
        });

        btnBorrar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView4.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
                //imageView4.setImageURI(Uri.parse("@drawable/empty_image"));
                //imageView4.findViewById(R.id.imgView4);

                fileimagen4=null;
                Toast.makeText(getApplicationContext(),"Borrada con exito", Toast.LENGTH_SHORT).show();


            }
        });

        btnBorrar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView5.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
                fileimagen5=null;
                Toast.makeText(getApplicationContext(),"Borrada con exito", Toast.LENGTH_SHORT).show();
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
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(SupervisionActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);
                } else {
                    descision=0;
                    takePhoto();
                }
            }
        });

        btnFoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(String.valueOf(Manifest.permission.CAMERA)) != PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(SupervisionActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                }

                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(SupervisionActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);
                } else {
                    descision=1;
                    takePhoto();
                }
            }
        });

        btnFoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(String.valueOf(Manifest.permission.CAMERA)) != PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(SupervisionActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                }

                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(SupervisionActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);
                } else {
                    descision=2;
                    takePhoto();
                }
            }
        });

        btnFoto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(String.valueOf(Manifest.permission.CAMERA)) != PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(SupervisionActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                }

                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(SupervisionActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);
                } else {
                    descision=3;
                    takePhoto();
                }
            }
        });

        btnFoto5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(String.valueOf(Manifest.permission.CAMERA)) != PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(SupervisionActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                }

                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(SupervisionActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);
                } else {
                    descision=4;
                    takePhoto();
                }
            }
        });





        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Observation = edObserv.getText().toString();
                boolean statuss = true;

                Data data = new Data(Observation,statuss);


                if((estado).equals("before")) {
                    if ((mDatabase.child("Eventos").child(idevent).child("observation").child("before").child("status")).equals(null))
                    {

                        Toast.makeText(getApplicationContext(),"Ya realizaste esta actividad", Toast.LENGTH_SHORT).show();
                    }else{
                       // String k = mDatabase.child("Eventos").child(idevent).child("observation").child("before").child("status").;
                        mDatabase.child("Eventos").child(idevent).child("observation").child("before").setValue(data);
                        Toast.makeText(getApplicationContext(), "Datos ingresados" , Toast.LENGTH_SHORT).show();
                        edObserv.setText("");
                        uploadImage1();
                        uploadImage2();
                        uploadImage3();
                        uploadImage4();
                        uploadImage5();

                        BorrarImagenes();
                    }
                }
                if ((estado).equals("during")){
                    if (mDatabase.child("Eventos").child(idevent).child("observation").child("during").child("status").equals("true"))
                    {
                        Toast.makeText(getApplicationContext(),"Ya realizaste esta actividad", Toast.LENGTH_SHORT).show();
                    }else {
                        mDatabase.child("Eventos").child(idevent).child("observation").child("during").setValue(data);
                        Toast.makeText(getApplicationContext(), "Datos ingresados", Toast.LENGTH_SHORT).show();
                        edObserv.setText("");
                        uploadImage1();
                        uploadImage2();
                        uploadImage3();
                        uploadImage4();
                        uploadImage5();

                        BorrarImagenes();
                    }
                }
                if ((estado).equals("after")){
                    if (mDatabase.child("Eventos").child(idevent).child("observation").child("after").child("status").equals("true"))
                    {
                        Toast.makeText(getApplicationContext(),"Ya realizaste esta actividad", Toast.LENGTH_SHORT).show();
                    }else {
                        mDatabase.child("Eventos").child(idevent).child("observation").child("after").setValue(data);
                        Toast.makeText(getApplicationContext(), "Datos ingresados", Toast.LENGTH_SHORT).show();
                        edObserv.setText("");
                        uploadImage1();
                        uploadImage2();
                        uploadImage3();
                        uploadImage4();
                        uploadImage5();

                        BorrarImagenes();
                    }

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

    private void BorrarImagenes() {
        imageView.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
        fileimagen=null;

        imageView2.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
        fileimagen2=null;

        imageView3.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
        fileimagen3=null;

        imageView4.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
        fileimagen5=null;

        imageView5.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
        fileimagen5=null;
    }

    public void takePhoto() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAPTURE_PHOTO);
    }

    private void uploadImage1() {
        if(fileimagen!=null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Subiendo....");

            StorageReference ref = storageReference.child("images").child(idevent).child(estado).child("Img"+UUID.randomUUID().toString());
           // StorageReference ref = storageReference.child("images/"+UUID.randomUUID().toString());

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
                            Toast.makeText(getApplicationContext(), "Fallo al Subir", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Subido " + (int)progress+"%");
                        }
                    });
        }
    }

    private void uploadImage2() {
        if(fileimagen2!=null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Subiendo....");

            StorageReference ref = storageReference.child("images").child(idevent).child(estado).child("Img"+UUID.randomUUID().toString());
            // StorageReference ref = storageReference.child("images/"+UUID.randomUUID().toString());

            ref.putFile(Uri.fromFile(fileimagen2))
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
                            Toast.makeText(getApplicationContext(), "Fallo al Subir", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Subido " + (int)progress+"%");
                        }
                    });
        }
    }

    private void uploadImage3() {
        if(fileimagen3!=null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Subiendo....");

            StorageReference ref = storageReference.child("images").child(idevent).child(estado).child("Img"+UUID.randomUUID().toString());
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
                            Toast.makeText(getApplicationContext(), "Fallo al Subir", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Subido " + (int)progress+"%");
                        }
                    });
        }
    }

    private void uploadImage4() {
        if(fileimagen4!=null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Subiendo....");

            StorageReference ref = storageReference.child("images").child(idevent).child(estado).child("Img"+UUID.randomUUID().toString());
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
                            Toast.makeText(getApplicationContext(), "Fallo al Subir", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Subido " + (int)progress+"%");
                        }
                    });
        }
    }


    private void uploadImage5() {
        if(fileimagen5!=null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Subiendo....");

            StorageReference ref = storageReference.child("images").child(idevent).child(estado).child("Img"+UUID.randomUUID().toString());
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
                            Toast.makeText(getApplicationContext(), "Fallo al Subir", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Subido " + (int)progress+"%");
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

/*
        MediaScannerConnection.scanFile(getApplicationContext(), new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {
                Log.i("Path", ""+path);
            }
        });

        bitmap = BitmapFactory.decodeFile(path);
        imageView.setImageBitmap(bitmap);*/

/*           Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
            imageView.setImageBitmap(bitmap);
            //filePath = data.getData();

       // Toast.makeText(getApplicationContext(), "Uri " + fileimagen, Toast.LENGTH_SHORT).show();
*/
        //----------------------------Magical Camera


        if (resultCode == RESULT_OK){
            switch (requestCode){

                case CAPTURE_PHOTO:

                    capturedCoolerBitmap = (Bitmap) data.getExtras().get("data");

                    int CamWidth = 1200;
                    int CamHegith = 800;

                    //Bitmap resizeImage = Bitmap.createScaledBitmap(capturedCoolerBitmap,CamWidth,CamHegith,false);
                    Bitmap Bitnew = redimensionarImagenMaximo(capturedCoolerBitmap, 1200, 800);

                    if(descision==0) {
                        imageView.setImageBitmap(Bitnew);
                        saveImageToGallery(Bitnew);

                    } else if (descision==1){
                        imageView2.setImageBitmap(Bitnew);
                        saveImageToGallery(Bitnew);

                    } else if (descision==2){
                        imageView3.setImageBitmap(Bitnew);
                        saveImageToGallery(Bitnew);

                    } else if (descision==3){
                        imageView4.setImageBitmap(Bitnew);
                        saveImageToGallery(Bitnew);

                    } else if (descision==4){
                        imageView5.setImageBitmap(Bitnew);
                        saveImageToGallery(Bitnew);

                    }


                    break;

                default:
                    Toast.makeText(getApplicationContext(),"Error" , Toast.LENGTH_SHORT).show();
                    break;
            }
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

        if(descision==0) {
            fileimagen = file;
        } else if (descision==1){
            fileimagen2 = file;
        } else if (descision==2){
            fileimagen3 = file;
        } else if (descision==3){
            fileimagen4 = file;
        } else if (descision==4){
            fileimagen5 = file;
        }

        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            String resizeCoolerImagePath = file.getAbsolutePath();
            out.flush();
            out.close();

            Toast.makeText(getApplicationContext(),"Tu foto se ha guardado exitosamente",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Hubo un error",Toast.LENGTH_SHORT).show();
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()){
                        case R.id.itemAntes:
                            estado="before";
                            txtEstado.setText("Antes de la Actividad");
                            //Toast.makeText(getApplicationContext(),"Estado = " +estado, Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.itemDurante:
                            estado="during";
                            txtEstado.setText("Durante la Actividad");
                           // Toast.makeText(getApplicationContext(),"Estado = " +estado, Toast.LENGTH_SHORT).show();
                        break;

                        case R.id.itemDespues:
                            estado="after";
                            txtEstado.setText("Después de la Actividad");
                            //Toast.makeText(getApplicationContext(),"Estado = " +estado, Toast.LENGTH_SHORT).show();
                            break;
                    }

                    return true;
                }
            };

    private void recibirDatos() {
        Bundle extras = getIntent().getExtras();
        idevent = extras.getString("idEvento");
        actividad = extras.getString("actividad");
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment mifragment = null;
        Boolean FragmentoSeleccionado=false;

        if (id == R.id.nav_acty) {
            Toast.makeText(getApplicationContext(),"Aún en proceso",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_event) {
            mifragment = new EventosFragment();
            FragmentoSeleccionado=true;

           /*Intent intent= new Intent (Main2Activity.this, EventosActivity.class);
            startActivity(intent);]*/
            //finish();

        } else if (id == R.id.nav_ext) {
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

    }
}
