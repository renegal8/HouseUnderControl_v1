package holamundo.itesm.mx.houseundercontrol_v1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
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

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    String LOG_TAG = "MainActivity";
    Button config;
    Button verconfig;
    Button monitoreoBtn;
    Button localizarBtn;
    Button veralarma;
    Button prueba;

    String tname="";
    String tcant="";
    Bitmap tphoto;
    String tphoto1="";

    private HouseOperations dao;
    private AlarmaHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new HouseOperations(this);
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        config = (Button) findViewById(R.id.boton_configuracion);
        verconfig = (Button) findViewById(R.id.boton_verconfiguracion);
        monitoreoBtn = (Button) findViewById(R.id.monitoreoBtn);
        localizarBtn = (Button) findViewById(R.id.localizarBtn);
        veralarma = (Button) findViewById(R.id.botonAlarma);
        prueba = (Button) findViewById(R.id.prueba);


        config.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                try {
                    Intent configIntent = new Intent(MainActivity.this, activity_config.class);
                    startActivityForResult(configIntent, 1);
                }
                catch(Exception e){
                    Log.e(LOG_TAG, "Failed to send intent", e);
                }
            }
        });

        verconfig.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    Intent verIntent = new Intent(MainActivity.this, ver_todos.class);
                    /*intent.putExtra("nombre",tname);
                    intent.putExtra("cantidad",tcant);
                    //intent.putExtra("photo", tphoto);
                    intent.putExtra("photo", tphoto1); */
                    startActivity(verIntent);
                }
                catch(Exception e){
                    Log.e(LOG_TAG, "Failed to send intent", e);
                }
            }
        });

        monitoreoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    Intent monitoreoIntent = new Intent(MainActivity.this, MonitoreoActivity.class);
                    startActivity(monitoreoIntent);
                }
                catch(Exception e){
                    Log.e(LOG_TAG, "Failed to send intent", e);
                }
            }
        });

        localizarBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                try {
                    Intent mapaIntent = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(mapaIntent);
                }
                catch(Exception e){
                    Log.e(LOG_TAG, "Failed to send intent", e);
                }
            }
        });

        veralarma.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(MainActivity.this, ver_alarmas.class);
                    startActivity(intent);
                }
                catch(Exception e){
                    Log.e(LOG_TAG, "Failed to send intent", e);
                }
            }
        });

        prueba.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                newAlarma(v);
            }
        });

    }

    public void newAlarma(View view){


        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
        String fecha = df.format(c.getTime()) + "      " + df2.format(c.getTime()) ;


        Alarma alarma = new Alarma(fecha,1);
        dao.addAlarma(alarma);

        Toast.makeText(getApplicationContext(), "Alarma Agregada Exitosamente", Toast.LENGTH_SHORT).show();

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            tname = data.getStringExtra("name");
            tcant = data.getStringExtra("cantidad");
//            tphoto = (Bitmap) data.getExtras().get("photo");
            tphoto1 = data.getStringExtra("photo");

        }
    }
}
