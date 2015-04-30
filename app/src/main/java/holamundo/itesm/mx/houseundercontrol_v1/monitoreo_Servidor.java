package holamundo.itesm.mx.houseundercontrol_v1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class monitoreo_Servidor extends ActionBarActivity {

    List<Clima> climasList;
    ClimaAdapter adaptadorLista;
    ListView listaClimasLV;
    ProgressDialog progressDialog;
    String url = "http://api.openweathermap.org/data/2.5/forecast?lat=25.67&lon=-100.32";
    private HouseOperations dao;

    Handler customHandler = new android.os.Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoreo__servidor);

        dao = new HouseOperations(this);
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        listaClimasLV = (ListView) findViewById(R.id.listaLV);
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        };
        listaClimasLV.setOnItemClickListener(itemListener);
        //Checa si esta conectado o no
        if(isConnected()){
            new LoadData().execute(url);
        }
        else{
            Toast.makeText(getApplicationContext(), "No hay conexion", Toast.LENGTH_LONG).show();
        }
    }

    private Runnable updateTimerThread = new Runnable()
    {
        public void run()
        {
            if (isConnected()) {
                Toast.makeText(getApplicationContext(), "Ejecute URL", Toast.LENGTH_LONG).show();
                new LoadData().execute(url);
            } else {
                Toast.makeText(getApplicationContext(), "No hay conexion", Toast.LENGTH_LONG).show();
            }
            customHandler.postDelayed(this, 10000);
        }
    };

    public boolean isConnected (){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        }else {
            return false;
        }
    }

    public class LoadData extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(monitoreo_Servidor.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject json;
            ClimaServiceCall jsonObject = new ClimaServiceCall();
            try {
                Log.e("LoadDatadoInBackground", params[0]);
                json= jsonObject.getJSONFromUrl(params[0]);
                return json;

            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            climasList = new ArrayList<Clima>();
            String ciudad = null;
            String pais = null;

            JSONArray dataJsonArray = null;
            JSONObject dataJsonObject = null;
            try {

                dataJsonObject = jsonObject.getJSONObject("city");
                ciudad = dataJsonObject.getString("name");
                pais = dataJsonObject.getString("country");

                dataJsonArray = jsonObject.getJSONArray("list");
                for (int i=0;i<dataJsonObject.length();i++){
                    JSONObject c = dataJsonArray.getJSONObject(i);
                    JSONObject c1 = c.getJSONObject("main");
                    double temp = c1.getDouble("temp")-273.15;
                    double temp_min = c1.getDouble("temp_min")-273.15;
                    double temp_max = c1.getDouble("temp_max")-273.15;
                    if(temp < 15)
                    {
                        Calendar calendar = Calendar.getInstance();
                        System.out.println("Current time =&gt; " + calendar.getTime());

                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
                        String fecha = df.format(calendar.getTime()) + "      " + df2.format(calendar.getTime());


                        Alarma alarma = new Alarma(fecha, 1);
                        dao.addAlarma(alarma);

                        Toast.makeText(getApplicationContext(), "Alarma Agregada Exitosamente", Toast.LENGTH_SHORT).show();


                    }

                    Clima clima = new Clima(temp,temp_max,temp_min);
                    climasList.add(clima);
                }

            } catch (Exception e){
                e.printStackTrace();
            }

            adaptadorLista = new ClimaAdapter(monitoreo_Servidor.this,R.layout.activity_rowclima,climasList);
            listaClimasLV.setAdapter(adaptadorLista);
            TextView ciudadTV = (TextView) findViewById(R.id.tituloClimaTV);
            ciudadTV.setText("Pronistico de temperatura "+ ciudad+pais);
            if (progressDialog.isShowing()){
                progressDialog.dismiss();}
            super.onPostExecute(jsonObject);
        }
    }

}
