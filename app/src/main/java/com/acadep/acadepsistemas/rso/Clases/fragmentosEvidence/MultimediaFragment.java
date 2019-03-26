package com.acadep.acadepsistemas.rso.Clases.fragmentosEvidence;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.acadep.acadepsistemas.rso.Adapter.RecyclerViewAdapter;
import com.acadep.acadepsistemas.rso.Clases.EvidenceActivity;
import com.acadep.acadepsistemas.rso.Clases.SupervisionActivity;
import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Foto;
import com.acadep.acadepsistemas.rso.model.Ubication;
import com.acadep.acadepsistemas.rso.model.datetime;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.clans.fab.FloatingActionMenu;

import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class MultimediaFragment extends Fragment {

    static EvidenceActivity evidenceActivity = new EvidenceActivity();

    public static final int MULTIPLE_PERMISSIONS = 10;
    String[] permissions= new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    static RecyclerView mRecyclerView;
    static RecyclerView.Adapter mAdapter;
    static RecyclerView.LayoutManager mLayaoutManager;

    private static Foto PhotoData = new Foto();

    static ArrayList<String> mTypeAdapter = new ArrayList<>();
    static ArrayList<Uri> ListVideos = new ArrayList<>();
    static ArrayList<Boolean> mItemChecked = new ArrayList<>();
    static ArrayList<Bitmap> mImageBitmap = new ArrayList<>();
    static List<Foto> multimedia = new ArrayList<>();
    static int contImg = 0;


    static Time today = new Time(Time.getCurrentTimezone());
    static String Fecha;
    static String Hora;
    static datetime datatime = new datetime();
    static Ubication ubication = new Ubication();
    static double Lat;
    static double Lng;
    static String created_at;
    private String filePath;


    static final int RESULT_LOAD_IMG = 92;
    int TAKE_PHOTO_CODE = 0;
    static final int REQUEST_VIDEO_CAPTURE = 1;

    static com.github.clans.fab.FloatingActionButton actionButton_Take_photo;
    static com.github.clans.fab.FloatingActionButton actionButton_Take_video;
    static com.github.clans.fab.FloatingActionButton actionButton_Upload_Image;
    static com.github.clans.fab.FloatingActionButton actionButton_Borrar_Seleccionados;
    static FloatingActionMenu floatingActionsMenu;


    static boolean during_complete;
    static boolean before_complete;
    static String deleted;

    static boolean Tbefore;
    static boolean Tduring;
    static boolean Tafter;

    static  String estado = "before";

    TextView txtEstado;
    CheckBox check_NoAplica;

    public MultimediaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        locationStart();

        during_complete = EvidenceActivity.isDuring_complete();
        before_complete = EvidenceActivity.isBefore_complete();

        Tbefore = EvidenceActivity.isTbefore();
        Tduring = EvidenceActivity.isTduring();
        Tafter = EvidenceActivity.isTafter();

        estado = EvidenceActivity.getEstado();

        mTypeAdapter = EvidenceActivity.getmTypeAdapter();
        ListVideos   = EvidenceActivity.getListVideos();
        mItemChecked = EvidenceActivity.getmItemChecked();
        mImageBitmap = EvidenceActivity.getmImageBitmap();
        multimedia   = EvidenceActivity.getMultimedia();
        contImg      = EvidenceActivity.getContImg();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multimedia, container, false);
        initRecyclerView(view);

        txtEstado = (TextView) view.findViewById(R.id.txtEstado);
        floatingActionsMenu = view.findViewById(R.id.FloatingActionMenuPrincipal);
        actionButton_Take_photo = view.findViewById(R.id.fab_action_1);
        actionButton_Take_video = view.findViewById(R.id.fab_action_2);
        actionButton_Upload_Image = view.findViewById(R.id.fab_action_3);
        actionButton_Borrar_Seleccionados = view.findViewById(R.id.fab_action_2_1);


//        check_NoAplica = view.findViewById(R.id.check_NoAplica);

        ChequeoDeVariables();

//        if (checkPermissions()) {
//            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
//            } else {
//                locationStart();
//            }
//        } else{
//
//        }


//        check_NoAplica.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (check_NoAplica.isChecked()){
//                    evidenceActivity.setChecked_NoAplica(true);
//                }else{
//                    evidenceActivity.setChecked_NoAplica(false);
//                }
//            }
//        });

        actionButton_Upload_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

                floatingActionsMenu.close(true);
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

        actionButton_Borrar_Seleccionados.setOnClickListener(new View.OnClickListener() {
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

//                        for (int i=(ArchivosUris.size()-1); 0<=i ;i--){
//                            if (archivoChecked.get(i)==true){
//                                deleteFile(i);
//                                Log.i("mArchivoChecked - ", "Borrado" + i);
//                            }else{
//                                Log.i("mArchivoChecked - ", "Es false" + i);
//                            }
//                        }
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


        return view;
    }

    private void ChequeoDeVariables() {
        if(Tbefore==true && before_complete == false){
//            ava = 0;
            txtEstado.setText("Inicio de la tarea");
            estado = "before";
            EvidenceActivity.setEstado("before");
        }else {
            if (Tduring == true && during_complete==false){
                txtEstado.setText("Durante la tarea");
                estado = "during";
                EvidenceActivity.setEstado("during");
            }else {
                if (Tafter == true && during_complete == true) {
                    txtEstado.setText("Después de la tarea");
                    estado = "after";
                    EvidenceActivity.setEstado("after");
                }
            }
        }
    }

    private void initRecyclerView(View view){
        int mNoOfColumns = SupervisionActivity.Utility.calculateNoOfColumns(getContext(), 90);

        mRecyclerView = view.findViewById(R.id.images_recycler);
//        mLayaoutManager= new GridLayoutManager(this, 4);
        mLayaoutManager= new GridLayoutManager(getContext(), mNoOfColumns);
        mAdapter = new RecyclerViewAdapter(getContext(), mTypeAdapter, ListVideos, mItemChecked, new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Uri mImage, int position) {


                if(mTypeAdapter.get(position).equals("Photo")) {
                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                    View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                    PhotoView photoView = mView.findViewById(R.id.imageView);
                    Drawable d = new BitmapDrawable(mImageBitmap.get(position));
                    photoView.setImageDrawable(d);
                    mBuilder.setView(mView);
                    final AlertDialog mDialog = mBuilder.create();
                    mDialog.getWindow().setLayout(600, 400);
                    mDialog.show();

                }
                if(mTypeAdapter.get(position).equals("video")) {
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

    private void addImage(Uri uri,int position, String type, Bitmap bitmap){
        ListVideos.add(position, uri);
        mTypeAdapter.add(position, type);
        mItemChecked.add(0, false);
        mAdapter.notifyItemInserted(position);
        mLayaoutManager.scrollToPosition(position);
        mImageBitmap.add(0, bitmap);

        EvidenceActivity.setListVideos(ListVideos);
        EvidenceActivity.setmTypeAdapter(mTypeAdapter);
        EvidenceActivity.setmItemChecked(mItemChecked);
        EvidenceActivity.setmImageBitmap(mImageBitmap);
        EvidenceActivity.setContImg(contImg);


    }

    private void deleteImage(int position){

        mImageBitmap.remove(position);
        mTypeAdapter.remove(position);
        mItemChecked.remove(position);
        ListVideos.remove(position);
        multimedia.remove(position);
        mAdapter.notifyItemRemoved(position);
        contImg--;

        EvidenceActivity.setListVideos(ListVideos);
        EvidenceActivity.setmTypeAdapter(mTypeAdapter);
        EvidenceActivity.setmItemChecked(mItemChecked);
        EvidenceActivity.setmImageBitmap(mImageBitmap);
        EvidenceActivity.setContImg(contImg);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MULTIPLE_PERMISSIONS:{
//                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    Toast.makeText(getContext(), "Permisos otorgados...", Toast.LENGTH_SHORT).show();
//                } else {
////                    Toast.makeText(this, "Aplicacion sin permisos...", Toast.LENGTH_SHORT).show();
//                }
//                // permissions list of don't granted permission
//            }
//            return;
//        }
//    }

//    private  boolean checkPermissions() {
//        int result;
//        List<String> listPermissionsNeeded = new ArrayList<>();
//        for (String p:permissions) {
//            result = ContextCompat.checkSelfPermission(getContext(),p);
//            if (result != PackageManager.PERMISSION_GRANTED) {
//                listPermissionsNeeded.add(p);
//            }
//        }
//        if (!listPermissionsNeeded.isEmpty()) {
//            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
//            return false;
//        }
//        return true;
//    }

    private void GuardarInformacionImagenes(int i) {

        locationStart();
        created_at_funct();

        datatime.setDate(Fecha);
        datatime.setTime(Hora);

        locationStart();
        Lat = evidenceActivity.getLat();
        Lng = evidenceActivity.getLng();
        ubication.setLat(Lat);
        ubication.setLng(Lng);


        if (i ==2) {
            PhotoData.setType("gallery");
        }
        if (i ==1) {
            PhotoData.setType("image");
        }


        PhotoData.setCreated_at(created_at);
        PhotoData.setUbication(ubication);

        PhotoData.setSrc(""+contImg);

        multimedia.add(0, PhotoData);

        evidenceActivity.setMultimedia(multimedia);

        PhotoData = new Foto();
    }

    private void GuardarInformacionVideos() {

        locationStart();
        created_at_funct();

        datatime.setDate(Fecha);
        datatime.setTime(Hora);

        locationStart();
        Lat = evidenceActivity.getLat();
        Lng = evidenceActivity.getLng();
        ubication.setLat(Lat);
        ubication.setLng(Lng);




        PhotoData.setCreated_at(created_at);
        PhotoData.setUbication(ubication);
        PhotoData.setType("video");
        PhotoData.setSrc(""+contImg);

        multimedia.add(0, PhotoData);

        evidenceActivity.setMultimedia(multimedia);

        PhotoData = new Foto();

    }

    private void selectImage(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
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

        Uri outputFileUri = FileProvider.getUriForFile(getContext(),"com.acadep.acadepsistemas.rso.fileprovider", newfile);


        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);


        filePath = newfile.getPath();

        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
    }

    private void takeVideo() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            Log.d("CameraDemo", "Pic saved");
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);

            double heightd = bitmap.getHeight()*.1720430107526882;
            float heightf =  (float)heightd;
            Bitmap Bitnew = redimensionarImagenMaximo(bitmap, 512 ,  heightf);

            contImg++;
            addImage( Uri.fromFile(new File(filePath)), 0, "Photo", Bitnew);
//            ListVideos.add(0, Uri.fromFile(new File(filePath)));

            GuardarInformacionImagenes(1);


//            EvidenceActivity.setContImg(contImg);

        }

        if(requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {

            Uri videoUri = data.getData();
//            addImage(new File(videoUri.getPath()), 0, "Video");

            Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),
                    R.drawable.reproductor_multimedia);

            contImg++;
            addImage(videoUri, 0, "Video", icon);

//            ListVideos.add(0, videoUri);


//            EvidenceActivity.setmImageBitmap(mImageBitmap);
//            EvidenceActivity.setContImg(contImg);

        }

        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK){


            Uri imageUri = data.getData();

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            double heightd = bitmap.getHeight()*.1720430107526882;
            float heightf =  (float)heightd;
            Bitmap Bitnew = redimensionarImagenMaximo(bitmap, 512 ,  heightf);
            contImg++;
            addImage(imageUri, 0, "Photo", Bitnew);


            GuardarInformacionImagenes(2);

//            EvidenceActivity.setmImageBitmap(mImageBitmap);
//            EvidenceActivity.setContImg(contImg);

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


    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        EvidenceActivity.Localizacion Local = new EvidenceActivity.Localizacion();
        Local.setMainActivity(getActivity());
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);

        created_at_funct();

        //txtFecha.setText(created_at.substring(0, 9));
        //txtHora.setText(created_at.substring(10, 15));
    }







}
