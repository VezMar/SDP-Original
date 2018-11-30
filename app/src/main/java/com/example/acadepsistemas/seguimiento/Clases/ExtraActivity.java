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

public class ExtraActivity extends AppCompatActivity {

    static String idevent;
    static String nameEvent;

    TextView txtname, txtidevent;




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


    static int descision;

    //Variables xxxxxx

    static TextView mensaje1, mensaje2;

    static double Lat;
    static double Lng;


    // GPS LOCATION


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
        setContentView(R.layout.activity_extra);

        recibirDatos();

        txtname = (TextView) findViewById(R.id.txtname);
        txtidevent = (TextView) findViewById(R.id.txtidevent);

        txtname.setText("Evento: "+nameEvent);
        txtidevent.setText("ID: "+idevent);

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

        mensaje1  = (TextView) findViewById(R.id.txtLat);
        mensaje2  = (TextView) findViewById(R.id.txtLng);

        mensaje1.setVisibility(View.INVISIBLE);
        mensaje2.setVisibility(View.INVISIBLE);


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


        btnArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(ExtraActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectPDF();
                } else {
                    ActivityCompat.requestPermissions(ExtraActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });


        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
                fileimagen = null;
                Toast.makeText(getApplicationContext(), "Borrada con exito", Toast.LENGTH_SHORT).show();


            }
        });

        btnBorrar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView2.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
                fileimagen2 = null;
                Toast.makeText(getApplicationContext(), "Borrada con exito", Toast.LENGTH_SHORT).show();

            }
        });

        btnBorrar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView3.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
                fileimagen3 = null;
                Toast.makeText(getApplicationContext(), "Borrada con exito", Toast.LENGTH_SHORT).show();
            }
        });

        btnBorrar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView4.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
                //imageView4.setImageURI(Uri.parse("@drawable/empty_image"));
                //imageView4.findViewById(R.id.imgView4);

                fileimagen4 = null;
                Toast.makeText(getApplicationContext(), "Borrada con exito", Toast.LENGTH_SHORT).show();


            }
        });

        btnBorrar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView5.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
                fileimagen5 = null;
                Toast.makeText(getApplicationContext(), "Borrada con exito", Toast.LENGTH_SHORT).show();
            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    descision = 0;
                    takePhoto();
                }
            }
        });

        btnFoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    descision = 1;
                    takePhoto();
                }
            }
        });

        btnFoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    descision = 2;
                    takePhoto();
                }
            }
        });

        btnFoto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    descision = 3;
                    takePhoto();
                }
            }
        });

        btnFoto5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    descision = 4;
                    takePhoto();
                }
            }
        });


        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Observation = edObserv.getText().toString();
                boolean statuss = true;

                Data data = new Data(Observation, statuss, Lat, Lng);



                mDatabase.child("Eventos").child(idevent).child("extraordinario").setValue(data);
                Toast.makeText(getApplicationContext(), "Datos ingresados", Toast.LENGTH_SHORT).show();
                edObserv.setText("");
                uploadImage1();
                uploadImage2();
                uploadImage3();
                uploadImage4();
                uploadImage5();

                if (pdfUri != null) {
                    uploadfile(pdfUri);
                }

                BorrarImagenes();


            }


        });
    }

    private void BorrarImagenes() {
        imageView.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
        fileimagen = null;

        imageView2.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
        fileimagen2 = null;

        imageView3.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
        fileimagen3 = null;

        imageView4.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
        fileimagen5 = null;

        imageView5.setImageDrawable(Drawable.createFromPath("@drawable/empty_image"));
        fileimagen5 = null;
    }

    public void takePhoto() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAPTURE_PHOTO);
    }

    private void uploadImage1() {
        if (fileimagen != null) {
            final ProgressDialog progressDialog = new ProgressDialog(ExtraActivity.this);
            progressDialog.setTitle("Subiendo....");

            StorageReference ref = storageReference.child("images").child(idevent).child("extraordinario").child("Img" + UUID.randomUUID().toString());
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
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Subido " + (int) progress + "%");
                        }
                    });
        }
    }

    private void uploadImage2() {
        if (fileimagen2 != null) {
            final ProgressDialog progressDialog = new ProgressDialog(ExtraActivity.this);
            progressDialog.setTitle("Subiendo....");

            StorageReference ref = storageReference.child("images").child(idevent).child("extraordinario").child("Img" + UUID.randomUUID().toString());
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
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Subido " + (int) progress + "%");
                        }
                    });
        }
    }

    private void uploadImage3() {
        if (fileimagen3 != null) {
            final ProgressDialog progressDialog = new ProgressDialog(ExtraActivity.this);
            progressDialog.setTitle("Subiendo....");

            StorageReference ref = storageReference.child("images").child(idevent).child("extraordinario").child("Img" + UUID.randomUUID().toString());
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
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Subido " + (int) progress + "%");
                        }
                    });
        }
    }

    private void uploadImage4() {
        if (fileimagen4 != null) {
            final ProgressDialog progressDialog = new ProgressDialog(ExtraActivity.this);
            progressDialog.setTitle("Subiendo....");

            StorageReference ref = storageReference.child("images").child(idevent).child("extraordinario").child("Img" + UUID.randomUUID().toString());
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
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Subido " + (int) progress + "%");
                        }
                    });
        }
    }


    private void uploadImage5() {
        if (fileimagen5 != null) {
            final ProgressDialog progressDialog = new ProgressDialog(ExtraActivity.this);
            progressDialog.setTitle("Subiendo....");

            StorageReference ref = storageReference.child("images").child(idevent).child("extraordinario").child("Img" + UUID.randomUUID().toString());
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
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Subido " + (int) progress + "%");
                        }
                    });
        }
    }

    private void uploadfile(Uri pdfUri) {
        progressDialog= new ProgressDialog(ExtraActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo archivo...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName= "PDF" + UUID.randomUUID().toString();
        StorageReference srtreference = storage.getReference();
        srtreference.child("Documents").child(idevent).child("extraordinario").child(fileName).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getUploadSessionUri().toString();
                        DatabaseReference refDB = dbRef.getReference();

                        refDB.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Archivo Subido...",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"¡Fallo al subirse!",Toast.LENGTH_SHORT).show();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();
            Toast.makeText(getApplicationContext(), "Tu archivo se ha guardado exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Porfavor elija un archivo", Toast.LENGTH_SHORT).show();

        }

        if (requestCode == 104 && resultCode == RESULT_OK) {
            switch (requestCode) {

                case CAPTURE_PHOTO:

                    capturedCoolerBitmap = (Bitmap) data.getExtras().get("data");

                    int CamWidth = 1200;
                    int CamHegith = 800;

                    //Bitmap resizeImage = Bitmap.createScaledBitmap(capturedCoolerBitmap,CamWidth,CamHegith,false);
                    Bitmap Bitnew = redimensionarImagenMaximo(capturedCoolerBitmap, 1200, 800);

                    if (descision == 0) {
                        imageView.setImageBitmap(Bitnew);
                        saveImageToGallery(Bitnew);

                    } else if (descision == 1) {
                        imageView2.setImageBitmap(Bitnew);
                        saveImageToGallery(Bitnew);

                    } else if (descision == 2) {
                        imageView3.setImageBitmap(Bitnew);
                        saveImageToGallery(Bitnew);

                    } else if (descision == 3) {
                        imageView4.setImageBitmap(Bitnew);
                        saveImageToGallery(Bitnew);

                    } else if (descision == 4) {
                        imageView5.setImageBitmap(Bitnew);
                        saveImageToGallery(Bitnew);

                    }


                    break;

                default:
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    break;
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



    private void recibirDatos() {
        Bundle extras = getIntent().getExtras();
        idevent = extras.getString("idEvento");
        nameEvent = extras.getString("nameEvent");
       // Lat = extras.getDouble("Lat");
        //Lng = extras.getDouble("Lng");

    }
}
