package holamundo.itesm.mx.houseundercontrol_v1;

import android.content.Intent;
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
    String tname;
    String tcant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        config = (Button) findViewById(R.id.boton_configurar);
        verconfig = (Button) findViewById(R.id.boton_verConfiguracion);

        config.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(MainActivity.this, activity_config.class);
                    startActivityForResult(intent, 1);
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
                    Intent intent = new Intent(MainActivity.this, ver_configuracion.class);
                    intent.putExtra("nombre",tname);
                    intent.putExtra("cantidad",tcant);
                    startActivity(intent);
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

        }
    }
}
