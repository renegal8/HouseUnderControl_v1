package holamundo.itesm.mx.houseundercontrol_v1;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import com.parse.Parse;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SaveCallback;


public class MainActivity extends ActionBarActivity {

    String LOG_TAG = "MainActivity";
    Button config;
    Button verconfig;
    Button monitoreoBtn;
    Button localizarBtn;
    Button veralarma;
    Button botonOpciones;
    MediaPlayer buttonSound;
    Boolean bandera;

    String tname = "";
    String tcant = "";
    String tphoto1 = "";

    String t="";
    String l="";
    String st="";

    private HouseOperations dao;
    private SQLiteDatabase db;


    private int notificationCount;
    private final int MY_NOTIFICATION_ID = 1;
    private final String tickerText = "Notification message";
    private final String contentTitle = "Alarma Activada!!!";
    private final String contentText = "Tu Alarma ha sido Activada";
    private Intent notificationIntent;
    private PendingIntent pendingIntent;
    NotificationManager notificationManager;

    Handler customHandler = new android.os.Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Parse.enableLocalDatastore(getApplicationContext());
       // Parse.initialize(getApplicationContext(), "iKGWAwlQ9Z2AWSyDMb24uxXi1jHV0J4QiAtA51Gq", "fZ0HR3NhJ9OFDXzAsZpmMo7habc07M8CYaZEZGwC");
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });

        customHandler.post(sendData);

        dao = new HouseOperations(this);
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        config = (Button) findViewById(R.id.boton_configuracion);
        bandera= false;
        verconfig = (Button) findViewById(R.id.boton_verconfiguracion);
        monitoreoBtn = (Button) findViewById(R.id.monitoreoBtn);
        localizarBtn = (Button) findViewById(R.id.localizarBtn);
        veralarma = (Button) findViewById(R.id.botonAlarma);
        botonOpciones = (Button) findViewById(R.id.botonOpciones);
        buttonSound = MediaPlayer.create(MainActivity.this,R.raw.beep);


        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                try {
                    Intent configIntent = new Intent(MainActivity.this, activity_config.class);
                    startActivityForResult(configIntent, 1);
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Failed to send intent", e);
                }
            }
        });

        verconfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                try {
                    Intent verIntent = new Intent(MainActivity.this, ver_todos.class);
                    startActivity(verIntent);
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Failed to send intent", e);
                }
            }
        });

        monitoreoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                try {
                    Intent monitoreoIntent = new Intent(MainActivity.this, MonitoreoTodos.class);
                    startActivity(monitoreoIntent);
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Failed to send intent", e);
                }
            }
        });

        localizarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                try {
                    Intent mapaIntent = new Intent(MainActivity.this, MapTodos.class);
                    startActivity(mapaIntent);
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Failed to send intent", e);
                }
            }
        });

        veralarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                try {

                    Intent intent = new Intent(MainActivity.this, ver_alarmas.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Failed to send intent", e);
                }
            }
        });

        botonOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                try {

                    Intent intent = new Intent(MainActivity.this, OpcionesActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Failed to send intent", e);
                }
            }
        });


    }


    public void newAlarma() {


        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
        String fecha = df.format(c.getTime()) + "      " + df2.format(c.getTime());

        Alarma alarma = new Alarma(fecha, 1,st,l,t);
        dao.addAlarma(alarma);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            tname = data.getStringExtra("name");
            tcant = data.getStringExtra("cantidad");
            tphoto1 = data.getStringExtra("photo");

        }
    }

    public boolean isConnected (){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        }else {
            return false;
        }
    }

    private final Runnable sendData = new Runnable(){
        public void run(){
            try {

                if (isConnected()) {
                    ParseQuery<ParseObject> queryObtencion = ParseQuery.getQuery("Monitoreo");
                    //queryObtencion.fromLocalDatastore();
                    queryObtencion.getInBackground("G1ejbjTEOj", new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {


                                String actAlarma = object.get("statAlarma").toString().trim();


                                if(actAlarma.equals("ON"))
                                {
                                    t=object.get("temperatura").toString();
                                    l=object.get("luz").toString();
                                    st=object.get("statLuz").toString();

                                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext());
                                    notificationBuilder.setContentTitle(contentTitle);
                                    notificationBuilder.setTicker(tickerText);
                                    notificationBuilder.setSmallIcon(android.R.drawable.stat_sys_warning);
                                    notificationBuilder.setContentText(contentText + " (" + ++notificationCount + ")");
                                    notificationBuilder.setContentIntent(pendingIntent);
                                    notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    notificationManager.notify(MY_NOTIFICATION_ID, notificationBuilder.build());
                                   if(!bandera) {
                                       newAlarma();
                                       Toast.makeText(getApplicationContext(), "Historial de Alarma Guardado", Toast.LENGTH_SHORT).show();
                                        bandera = true;
                                   }
                                }
                                else {
                                    bandera = false;
                                }

                            }
                            else {
                               // Toast.makeText(getApplicationContext(), "No se encontró información en el Servidor", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {
                    //Toast.makeText(getApplicationContext(), "No hay conexion", Toast.LENGTH_LONG).show();
                }

                customHandler.postDelayed(this, 25000);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };




    @Override
    protected void onDestroy() {
        super.onDestroy();
        customHandler.removeCallbacks(sendData);
    }

    @Override
    protected void onResume(){
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.onResume();

        customHandler.post(sendData);
    }




}
