package holamundo.itesm.mx.houseundercontrol_v1;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends ActionBarActivity {

    String LOG_TAG = "MainActivity";
    Button config;
    Button verconfig;
    Button monitoreoBtn;
    Button localizarBtn;
    String tname="";
    String tcant="";
    Bitmap tphoto;
    String tphoto1="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        config = (Button) findViewById(R.id.boton_configuracion);
        verconfig = (Button) findViewById(R.id.boton_verconfiguracion);
        monitoreoBtn = (Button) findViewById(R.id.monitoreoBtn);
        localizarBtn = (Button) findViewById(R.id.localizarBtn);

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
                    Intent verIntent = new Intent(MainActivity.this, ver_configuracion.class);
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
