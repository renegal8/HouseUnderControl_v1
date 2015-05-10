package holamundo.itesm.mx.houseundercontrol_v1;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
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

    private HouseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_configuracion);

        dao = new HouseOperations(this);

        TextView nombreTV = (TextView) findViewById(R.id.nombreValueTV);
        TextView addressTV = (TextView) findViewById(R.id.addressValueTV);
        TextView cantidadTV = (TextView) findViewById(R.id.cantidadValueTV);
        casaIV = (ImageView) findViewById(R.id.casaIV);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        listaHouse = dao.getAll();

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("position");
        House house = listaHouse.get(id);

        byte[] foto =  house.getFoto();
        fotoBit = BitmapFactory.decodeByteArray(foto, 0, foto.length);

        new LoadImage().execute(R.mipmap.ic_launcher);

        nombreTV.setText(String.valueOf(house.getName()));
        addressTV.setText(String.valueOf( house.getAddress()));
        cantidadTV.setText(String.valueOf( house.getCantCuartos()));


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

}
