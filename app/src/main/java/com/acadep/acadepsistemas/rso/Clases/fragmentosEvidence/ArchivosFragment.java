package com.acadep.acadepsistemas.rso.Clases.fragmentosEvidence;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.acadep.acadepsistemas.rso.Adapter.ArchivosAdapter;
import com.acadep.acadepsistemas.rso.Clases.EvidenceActivity;
import com.acadep.acadepsistemas.rso.R;
import com.acadep.acadepsistemas.rso.model.Files;
import com.acadep.acadepsistemas.rso.model.Ubication;
import com.acadep.acadepsistemas.rso.model.datetime;
import com.github.clans.fab.FloatingActionMenu;

import org.joda.time.DateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ArchivosFragment extends Fragment {

    public ArchivosFragment() {
        // Required empty public constructor
    }

    static EvidenceActivity evidenceActivity = new EvidenceActivity();

    private String realPath;

    static List<Uri> ArchivosUris = new ArrayList<>();
    static List<String> Type_Archivo = new ArrayList<>();
    static List<String> Name_Archivo = new ArrayList<>();
    static ArrayList<Boolean> archivoChecked = new ArrayList<>();

    static List<Files> files = new ArrayList<>();

    public static final int MULTIPLE_PERMISSIONS = 10;
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    static Time today = new Time(Time.getCurrentTimezone());
    static String Fecha;
    static String Hora;
    static datetime datatime = new datetime();
    static Ubication ubication = new Ubication();
    static double Lat;
    static double Lng;
    static String created_at;
    private String filePath;
    Files PerFile = new Files();

    static int contUris = 0;
    static int restUris = 9;


    static RecyclerView mRecyclerViewFiles;
    static RecyclerView.Adapter mAdapterFiles;
    static RecyclerView.LayoutManager mLayaoutManagerFiles;


    static com.github.clans.fab.FloatingActionButton actionButton_Borrar_Seleccionados;
    static com.github.clans.fab.FloatingActionButton actionButton_Upload_PDF;
    static com.github.clans.fab.FloatingActionButton actionButton_Upload_Docx;
    static com.github.clans.fab.FloatingActionButton actionButton_Upload_Video;
    static com.github.clans.fab.FloatingActionButton actionButton_Upload_Audio;
    static FloatingActionMenu floatingActionsMenu;


    static boolean during_complete;
    static boolean before_complete;
    static String deleted;

    static boolean Tbefore;
    static boolean Tduring;
    static boolean Tafter;

    static String estado = "before";
    TextView txtEstado;


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

        ArchivosUris = EvidenceActivity.getArchivosUris();
        Type_Archivo = EvidenceActivity.getType_Archivo();
        Name_Archivo = EvidenceActivity.getName_Archivo();
        archivoChecked = EvidenceActivity.getArchivoChecked();
        files = EvidenceActivity.getFiles();
        contUris = EvidenceActivity.getContUris();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_archivos, container, false);
        initRecyclerViewFiles(view);

        txtEstado = (TextView) view.findViewById(R.id.txtEstado);
//        txtEstado.setVisibility(View.INVISIBLE);
        floatingActionsMenu = view.findViewById(R.id.FloatingActionMenuPrincipal);
        actionButton_Upload_PDF = view.findViewById(R.id.fab_action_3);
        actionButton_Upload_Docx = view.findViewById(R.id.fab_action_4);
        actionButton_Upload_Video = view.findViewById(R.id.fab_action_5);
        actionButton_Upload_Audio = view.findViewById(R.id.fab_action_6);
        actionButton_Borrar_Seleccionados = view.findViewById(R.id.fab_action_2_1);

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


        actionButton_Upload_PDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPDF();
                floatingActionsMenu.close(true);
            }
        });

        actionButton_Upload_Docx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDocx();
                floatingActionsMenu.close(true);
            }
        });

        actionButton_Upload_Video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectVideo();
                floatingActionsMenu.close(true);
            }
        });

        actionButton_Upload_Audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectAudio();

                Type_Archivo.add(0, "Audio");


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
//                        for (int i=(ListVideos.size()-1); 0<=i; i--){
//                            if (mItemChecked.get(i)==true){
//                                Log.i("mItemChecked - ", "Borrado" + i);
//                                deleteImage(i);
//
//                            }else{
//                                Log.i("mItemChecked - ", "Es false" + i);
//                            }
//                        }
//
                        for (int i = (ArchivosUris.size() - 1); 0 <= i; i--) {
                            if (archivoChecked.get(i) == true) {
                                deleteFile(i);
                                Log.i("mArchivoChecked - ", "Borrado" + i);
                            } else {
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


        return view;
    }

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

    private void initRecyclerViewFiles(View view) {

//        for (int i=0; i<archivoChecked.size(); i++){
//            Log.i("asdasdasdasdasd", String.valueOf(archivoChecked.get(0)));
//        }


        mRecyclerViewFiles = view.findViewById(R.id.archivos_recycler);
        mLayaoutManagerFiles = new LinearLayoutManager(getContext());
        mAdapterFiles = new ArchivosAdapter(getContext(), ArchivosUris, Type_Archivo, Name_Archivo, archivoChecked);

//        mRecyclerViewFiles.setHasFixedSize(true);
        mRecyclerViewFiles.setItemAnimator(new DefaultItemAnimator());

        mRecyclerViewFiles.setLayoutManager(mLayaoutManagerFiles);
        mRecyclerViewFiles.setAdapter(mAdapterFiles);
    }

    private void ChequeoDeVariables() {
        if (Tbefore == true && before_complete == false) {
//            ava = 0;
//            txtEstado.setText("Inicio de la tarea");
            estado = "before";
            EvidenceActivity.setEstado("before");
        } else {
            if (Tduring == true && during_complete == false) {
//                txtEstado.setText("Durante la tarea");
                estado = "during";
                EvidenceActivity.setEstado("during");
            } else {
                if (Tafter == true && during_complete == true) {
//                    txtEstado.setText("Después de la tarea");
                    estado = "after";
                    EvidenceActivity.setEstado("after");
                }
            }
        }
    }

    private void GuardarInformacionArchivos(String type, String name, String realpath) {
        locationStart();
        created_at_funct();

        PerFile.setCreated_at(created_at);
        PerFile.setType(type);
//        PerFile.setSrc(""+uri);
//        PerFile.setSrc(""+uri.getPath());
        PerFile.setSrc("" + realpath);
        PerFile.setName("" + name);

        files.add(0, PerFile);

        PerFile = new Files();

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
        Fecha = calendar.get(Calendar.YEAR) + "-" + mes + "-" + dia;

        String minute = "" + calendar.get(Calendar.MINUTE);
        if (minute.length() == 1) {
            minute = "0" + minute;
        }

        String hora = "" + calendar.get(Calendar.HOUR);
        if (hora.length() == 1) {
            hora = "0" + hora;
        }

        Hora = hora + ":" + minute;

        created_at = Fecha + "T" + Hora;

    }

    private void selectPDF() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        String paths = getContext().getFilesDir().getAbsolutePath();
        Log.i("Datos", "asdasdasd - " + paths);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            if (contUris > 9) {
                Toast.makeText(getContext(), "Limite de archivos alcanzado", Toast.LENGTH_LONG).show();
            } else {

                realPath = getPathFromUri(getContext(), data.getData());
                if (realPath==null || realPath=="null"){
                    Toast.makeText(getContext(), "El archivo que intenta subir no se encuentra en el dispositivo, porfavor descarguelo e intente de nuevo", Toast.LENGTH_LONG).show();
                }else{

                    contUris++;
                    addFile(data, 0, "PDF");
                }

            }
            //Toast.makeText(getApplicationContext(),"Tu archivo se ha guardado exitosamente",Toast.LENGTH_SHORT).show();
        } else if (requestCode == 87 && resultCode == RESULT_OK && data != null) {
            if (contUris > 9) {
                Toast.makeText(getContext(), "Limite de archivos alcanzado", Toast.LENGTH_LONG).show();
            } else {
                realPath = getPathFromUri(getContext(), data.getData());
                if (realPath==null || realPath=="null"){
                    Toast.makeText(getContext(), "El archivo que intenta subir no se encuentra en el dispositivo, porfavor descarguelo e intente de nuevo", Toast.LENGTH_LONG).show();
                }else{

                    contUris++;
                    addFile(data, 0, "Video");
                }
            }
            //Toast.makeText(getApplicationContext(),"Tu archivo se ha guardado exitosamente",Toast.LENGTH_SHORT).show();
        }
        if (requestCode == 88 && resultCode == RESULT_OK && data != null) {
            if (contUris > 9) {
                Toast.makeText(getContext(), "Limite de archivos alcanzado", Toast.LENGTH_LONG).show();
            } else {
                realPath = getPathFromUri(getContext(), data.getData());
                if (realPath==null || realPath=="null"){
                    Toast.makeText(getContext(), "El archivo que intenta subir no se encuentra en el dispositivo, porfavor descarguelo e intente de nuevo", Toast.LENGTH_LONG).show();
                }else{

                    contUris++;
                    addFile(data, 0, "Audio");
                }
            }
            //Toast.makeText(getApplicationContext(),"Tu archivo se ha guardado exitosamente",Toast.LENGTH_SHORT).show();
        }
        if (requestCode == 89 && resultCode == RESULT_OK && data != null) {
            if (contUris > 9) {
                Toast.makeText(getContext(), "Limite de archivos alcanzado", Toast.LENGTH_LONG).show();
            } else {
                realPath = getPathFromUri(getContext(), data.getData());
                if (realPath==null || realPath=="null"){
                    Toast.makeText(getContext(), "El archivo que intenta subir no se encuentra en el dispositivo, porfavor descarguelo e intente de nuevo", Toast.LENGTH_LONG).show();
                }else{

                    contUris++;
                    addFile(data, 0, "Docx");
                }
            }
            // Toast.makeText(getApplicationContext(),"Tu archivo se ha guardado exitosamente",Toast.LENGTH_SHORT).show();
        }
    }

    private void SelecUri(Intent data) {
        if (contUris < 10) {
//            ArchivosUris.add(0, data.getData());
//            ArchivosUris.add(0, Uri.fromFile(new File(realPath)));
            data.getData();
            Uri.fromFile(new File(realPath));
            evidenceActivity.setArchivosUris(ArchivosUris);
            Toast.makeText(getContext(), "Archivo agregado con exito \nPuede subir " + (restUris - contUris) + " más", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getContext(), "Limite de archivos alcanzado", Toast.LENGTH_LONG).show();
        }
    }

    private void addFile(Intent data, int i, String type) {

        Uri uri = data.getData();


        String uriString = uri.toString();
        File file = new File(uriString);
        String displayname = null;
        if (uriString.startsWith("content://")) {
            Cursor cursor = null;
            try {
                cursor = getActivity().getApplicationContext().getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    displayname = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                    Log.i("display", "" + displayname);
                }
            } finally {
                cursor.close();
            }
        } else if (uriString.startsWith("file://")) {
            displayname = file.getName();
        }


//        String path = uri.toString(); // "file:///mnt/sdcard/FileName.mp3"
//        try {
//            File filez = new File(new URI(path));
//            Log.i("display", ""+filez);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }


        GuardarInformacionArchivos(type, displayname, "" + realPath);

//        File filename = new File(ArchivosUris.get(0).getPath());
        ArchivosUris.add(0, uri);
        Type_Archivo.add(0, type);
        Name_Archivo.add(0, displayname);
        archivoChecked.add(0, false);

        mAdapterFiles.notifyItemInserted(0);
        mLayaoutManagerFiles.scrollToPosition(0);


        EvidenceActivity.setArchivosUris(ArchivosUris);
        EvidenceActivity.setType_Archivo(Type_Archivo);
        EvidenceActivity.setName_Archivo(Name_Archivo);
        EvidenceActivity.setArchivoChecked(archivoChecked);
        EvidenceActivity.setFiles(files);
        EvidenceActivity.setContUris(contUris);
    }

    private void deleteFile(int position) {
        ArchivosUris.remove(position);
        Name_Archivo.remove(position);
        Type_Archivo.remove(position);
        archivoChecked.remove(position);
        files.remove(position);
        mAdapterFiles.notifyItemRemoved(position);
        contUris--;

        EvidenceActivity.setArchivosUris(ArchivosUris);
        EvidenceActivity.setName_Archivo(Name_Archivo);
        EvidenceActivity.setType_Archivo(Type_Archivo);
        EvidenceActivity.setArchivoChecked(archivoChecked);
        EvidenceActivity.setFiles(files);
        EvidenceActivity.setContUris(contUris);
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

    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;

    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
