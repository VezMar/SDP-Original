package com.acadep.acadepsistemas.rso.Clases;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.acadep.acadepsistemas.rso.Adapter.RecyclerViewAdapter;
import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Foto;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;


public class RecyclerTest  extends AppCompatActivity {
    //
    static Bitmap capturedCoolerBitmap;

    private static final int REQUEST_PERM_WRITE_STORAGE = 102;
    private static final int CAPTURE_PHOTO = 104;

//    Imagenes
        private ArrayList<Bitmap> mImageBitmap = new ArrayList<>();
        private static List<File> ListImages = new ArrayList<>();

        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayaoutManager;
        //Imagenes
    private FloatingTextButton btnPhoto;
    private FloatingTextButton btnEnviarTest;
    private Switch swtBorrar;



    //FireStore
    StorageReference storageReference;
    FirebaseStorage storage;
    FirebaseFirestore BDFireStore = FirebaseFirestore.getInstance();


    private int counter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_test);

        initRecyclerView();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        btnPhoto = findViewById(R.id.btnPhoto);
        btnEnviarTest = findViewById(R.id.btnEnviarTest);

        swtBorrar = findViewById(R.id.swtBorrar);




        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(String.valueOf(Manifest.permission.CAMERA)) != PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(RecyclerTest.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                }

                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(RecyclerTest.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);
                } else {


                    takePhoto();

                }
            }
        });


        btnEnviarTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadAllImages();
            }
        });

    }

    public void onClickSwitch(final View view) {
                    if (view.getId() == R.id.swtBorrar) {
                        if (swtBorrar.isChecked()) {
                            StyleableToast.makeText(RecyclerTest.this, "Al activar esta opción si da click sobre una imagen la borrará", Toast.LENGTH_SHORT, R.style.warningToastMiddle).show();
                            Toast.makeText(RecyclerTest.this, "Borrado de fotos activado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RecyclerTest.this, "Borrado de fotos desactivado", Toast.LENGTH_SHORT).show();
                        }
                    }

    }

    private void uploadAllImages() {

        for(int i=0; i<ListImages.size(); i++){
            if(ListImages.get(i) != null){
                uploadImageGlobal(ListImages.get(i), i);
                //images.put(""+i, ListImages.indexOf(i));
            }
        }
    }

    private void uploadImageGlobal(File fileimagenpos, final int x) {
        if (fileimagenpos != null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Subiendo....");

            final StorageReference ref = storageReference.child("pruebas").child("Img" + UUID.randomUUID().toString());
            // StorageReference ref = storageReference.child("images/"+UUID.randomUUID().toString());




//            ref.putFile().continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
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

//                        PerFotoArray[x].setSrc(downloadUri.toString());
//
//                        Subirdatos();
//
//                        if(x == (contImg-1) && contUris==0){
//
//                            BorrarImagenes();
//                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void takePhoto() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAPTURE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==104 && resultCode == RESULT_OK) {
            switch (requestCode) {

                case CAPTURE_PHOTO:

                    capturedCoolerBitmap = (Bitmap) data.getExtras().get("data");

                    int CamWidth = 1200;
                    int CamHegith = 800;

                    //Bitmap resizeImage = Bitmap.createScaledBitmap(capturedCoolerBitmap,CamWidth,CamHegith,false);
                    Bitmap Bitnew = redimensionarImagenMaximo(capturedCoolerBitmap, 1200 , 800);

                    //contImg++;

                    saveImageToGallery(Bitnew);
                    addImage(Bitnew, 0);


                    break;

                default:
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
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

        ListImages.add(0, file);


    }

    private void addImage(Bitmap bitmap,int position){

        mImageBitmap.add(position, bitmap);
        mAdapter.notifyItemInserted(position);
        mLayaoutManager.scrollToPosition(position);
    }

    private void delateImage(int position){
        mImageBitmap.remove(position);
        ListImages.remove(position);
        mAdapter.notifyItemRemoved(position);

    }


    private void initImagesBitmap(){
//        mImageBitmap.add("https://definicion.mx/wp-content/uploads/gral/Planicie.jpg");
//        mImageBitmap.add("https://s1.significados.com/foto/planicie_bg.jpg");
//        mImageBitmap.add("http://spmedia.s3.amazonaws.com/wp-content/uploads/2013/02/CCLAPLANICIE.jpg");
//        mImageBitmap.add("https://photo980x880.mnstatic.com/ebbe869f8b2d0336ec387685f1da6370/planicie-de-nardab_84091.jpg");

//        initRecyclerView();
    }

    private void initRecyclerView(){
        mRecyclerView = findViewById(R.id.test_recycler);
        mLayaoutManager= new GridLayoutManager(this, 3);
        mAdapter = new RecyclerViewAdapter(this, mImageBitmap, new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Bitmap mImage, int position) {
                Toast.makeText(RecyclerTest.this, "posicion " + position, Toast.LENGTH_SHORT).show();
                if (swtBorrar.isChecked()){
                    delateImage(position);
                }else{

                }

            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(mLayaoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


}
