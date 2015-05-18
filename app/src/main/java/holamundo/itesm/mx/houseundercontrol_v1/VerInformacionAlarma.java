package holamundo.itesm.mx.houseundercontrol_v1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;


public class VerInformacionAlarma extends ActionBarActivity {

    int id;
    String name;
    String fecha;
    HouseOperations dao;
    List<Alarma> listaAlarma;
    String TAG = "Ver Información Alarma Activity";
    Alarma alarma;
    TextView fechaAlarma;
    TextView temperatura;
    TextView intensidadLuz;
    TextView statuzFoco;

    private HouseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_informacion_alarma);

        dao = new HouseOperations(this);

        fechaAlarma = (TextView) findViewById(R.id.fechaAlarmaValueTV);
        temperatura = (TextView) findViewById(R.id.tempValueTV);
        intensidadLuz = (TextView) findViewById(R.id.luzValueTV);
        statuzFoco = (TextView) findViewById(R.id.statLuzValueTV);

        listaAlarma = dao.getAllAlarma();

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("position");
        alarma = listaAlarma.get(id);

        fechaAlarma.setText(String.valueOf(alarma.getFecha()));
        temperatura.setText(String.valueOf(alarma.getTemperatura())+ " ºC");
        intensidadLuz.setText(String.valueOf(alarma.getIntensidadLuz())+ " Candelas");
        statuzFoco.setText(String.valueOf(alarma.getStatuzFoco()));

    }

    @Override
    protected void onResume() {
        try {
            dao.open();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.onResume();
    }

    @Override
    protected void onPause() {
        dao.close();
        super.onPause();
    }
}

