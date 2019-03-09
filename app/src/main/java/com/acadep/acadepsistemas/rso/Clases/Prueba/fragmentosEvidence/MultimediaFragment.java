package com.acadep.acadepsistemas.rso.Clases.Prueba.fragmentosEvidence;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.acadep.acadepsistemas.rso.Adapter.RecyclerViewAdapter;
import com.acadep.acadepsistemas.rso.Clases.Prueba.EvidenceActivity;
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


    int TAKE_PHOTO_CODE = 0;
    static final int REQUEST_VIDEO_CAPTURE = 1;

    static com.github.clans.fab.FloatingActionButton actionButton_Take_photo;
    static com.github.clans.fab.FloatingActionButton actionButton_Take_video;
    static FloatingActionMenu floatingActionsMenu;

    public MultimediaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multimedia, container, false);
        initRecyclerView(view);

        floatingActionsMenu = view.findViewById(R.id.FloatingActionMenuPrincipal);
        actionButton_Take_photo = view.findViewById(R.id.fab_action_1);
        actionButton_Take_video = view.findViewById(R.id.fab_action_2);


        if (checkPermissions()) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            } else {
                locationStart();
            }
        } else{

        }


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
//                takeVideo();

                floatingActionsMenu.close(true);
            }
        });


        return view;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getContext(), "Permisos otorgados...", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(this, "Aplicacion sin permisos...", Toast.LENGTH_SHORT).show();
                }
                // permissions list of don't granted permission
            }
            return;
        }
    }

    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(getContext(),p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
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

//    private void takeVideo() {
//        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
//        }
//    }




    private void created_at_funct() {
        java.util.Date FecAct = new Date();
        Calendar calendar = new GregorianCalendar();
        DateTime dateTime = new DateTime();

//        Fecha =   today.year + "-" + today.month + "-" + today.monthDay;
        Fecha =   calendar.get(Calendar.YEAR) + "-" + dateTime.getMonthOfYear() + "-" + calendar.get(Calendar.DAY_OF_MONTH);



        Hora = today.hour +":" + calendar.get(Calendar.MINUTE);

        created_at = Fecha + "T" +Hora;

    }


    private void locationStart() {
        LocationManager mlocManager = (LocationManager) evidenceActivity.getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
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


}
