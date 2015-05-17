package holamundo.itesm.mx.houseundercontrol_v1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class ver_configuracion extends ActionBarActivity {

    ImageView casaIV;
    Bitmap imageBP;
    Bitmap fotoBit;
    ProgressBar progressBar;
    int id;
    String name;
    String cantCuartos;
    byte[] foto;
    int idFoto;
    String fecha;
    String address;
    HouseOperations dao;
    List<House> listaHouse;
    String TAG = "Ver Configuración Activity";
    EditText nombreET;
    EditText addressET;
    int idHouse;

    MediaPlayer buttonSound;

    private HouseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_configuracion);

        dao = new HouseOperations(this);

        //TextView nombreTV = (TextView) findViewById(R.id.nombreValueTV);
        //TextView addressTV = (TextView) findViewById(R.id.addressValueTV);
        nombreET = (EditText) findViewById(R.id.nombreValueET);
        addressET = (EditText) findViewById(R.id.addressValueET);
        TextView cantidadTV = (TextView) findViewById(R.id.cantidadValueTV);
        Button guardarBttn = (Button) findViewById(R.id.guardarBttn);
        casaIV = (ImageView) findViewById(R.id.casaIV);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        buttonSound = MediaPlayer.create(ver_configuracion.this, R.raw.beep);

        listaHouse = dao.getAll();

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("position");
        House house = listaHouse.get(id);

        //Toast.makeText(getApplicationContext(), house.getId(), Toast.LENGTH_LONG).show();

        byte[] foto =  house.getFoto();
        fotoBit = BitmapFactory.decodeByteArray(foto, 0, foto.length);

        new LoadImage().execute(R.mipmap.ic_launcher);

        //nombreTV.setText(String.valueOf(house.getName()));
        //addressTV.setText(String.valueOf( house.getAddress()));
        nombreET.setText(String.valueOf(house.getName()));
        addressET.setText(String.valueOf( house.getAddress()));
        cantidadTV.setText(String.valueOf( house.getCantCuartos()));
        idHouse = house.getId();

        guardarBttn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                buttonSound.start();

                if(nombreET.getText().toString().trim().matches("")|| addressET.getText().toString().trim().matches(""))
                {
                    Toast.makeText(getApplicationContext(), "Hay información Vacía", Toast.LENGTH_SHORT).show();
                }
                else {
                    changeHouse(v);
                }
            }
        });


    }

    @Override
    protected void onResume(){
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
    class LoadImage extends AsyncTask <Integer, Integer, Bitmap>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(Integer... params){
            Bitmap imagen = BitmapFactory.decodeResource(getResources(),params[0]);
            for(int i = 0; i < 25; i++)
            {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e){
                    Log.e(TAG, e.toString());
                }
                publishProgress(i*5);
            }
            return imagen;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            casaIV.setImageBitmap(fotoBit);
        }
    }

    public void changeHouse(View view){


        String name = nombreET.getText().toString();
        String address = addressET.getText().toString();

        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String fecha = df.format(c.getTime());


        House house = new House(idHouse, name, fecha, address);
        dao.updateHouse(house);

        Toast.makeText(getApplicationContext(), "Casa Editada Exitosamente", Toast.LENGTH_SHORT).show();

     //   Intent intent = new Intent(ver_configuracion.this, MainActivity.class);
     //   startActivity(intent);
        finish();

    }

}
