package com.acadep.acadepsistemas.rso.Clases;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.acadep.acadepsistemas.rso.Adapter.RecyclerViewAdapter;
import com.acadep.acadepsistemas.rso.R;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;


public class RecyclerTest  extends AppCompatActivity {
    //
    static Bitmap capturedCoolerBitmap;

    private static final int REQUEST_PERM_WRITE_STORAGE = 102;
    private static final int CAPTURE_PHOTO = 104;

    //
    private ArrayList<String> mImageBitmap = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayaoutManager;

    private FloatingTextButton btnPhoto;

    private int counter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_test);

        initImagesBitmap();

        btnPhoto = findViewById(R.id.btnPhoto);


        mRecyclerView = findViewById(R.id.test_recycler);
        mLayaoutManager= new GridLayoutManager(this, 3);
        mAdapter = new RecyclerViewAdapter(this, mImageBitmap, new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(String mImage, int position) {
                Toast.makeText(RecyclerTest.this, "posicion " + position, Toast.LENGTH_SHORT).show();
                delateImage(position);
            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(mLayaoutManager);
        mRecyclerView.setAdapter(mAdapter);

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

    private void addImage(int position){

//        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
//        byte [] b=baos.toByteArray();
//        String temp=Base64.encodeToString(b, Base64.DEFAULT);
//        Bitmap bitmap,
        mImageBitmap.add(position, "https://concepto.de/wp-content/uploads/2015/03/naturaleza-medio-ambiente-e1505407093531.jpeg");
        mAdapter.notifyItemInserted(position);
        mLayaoutManager.scrollToPosition(position);
    }

    private void delateImage(int position){
        mImageBitmap.remove(position);
        mAdapter.notifyItemRemoved(position);

    }


    private void initImagesBitmap(){
        mImageBitmap.add("https://definicion.mx/wp-content/uploads/gral/Planicie.jpg");
        mImageBitmap.add("https://s1.significados.com/foto/planicie_bg.jpg");
        mImageBitmap.add("http://spmedia.s3.amazonaws.com/wp-content/uploads/2013/02/CCLAPLANICIE.jpg");
        mImageBitmap.add("https://photo980x880.mnstatic.com/ebbe869f8b2d0336ec387685f1da6370/planicie-de-nardab_84091.jpg");

//        initRecyclerView();
    }

    private void initRecyclerView(){
//        RecyclerView recyclerView = findViewById(R.id.test_recycler);
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mImageBitmap);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }
}
