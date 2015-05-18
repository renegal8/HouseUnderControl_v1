package holamundo.itesm.mx.houseundercontrol_v1;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.sql.SQLException;
import java.util.List;


public class MonitoreoActivity extends ActionBarActivity {

    ImageView casaIV;
    Bitmap fotoBit;

    int id;
    String name;
    int cantCuartos;
    byte[] foto;
    int idFoto;
    String fecha;
    MediaPlayer buttonSound;

    TextView tempTV;
    TextView luzTV;
    TextView statLuzTV;
    TextView statAlarmaTV;
    TextView txtRecom;

    HouseOperations dao;
    List<House> listaHouse;

    int i = 0;

    String DEBUG_TAG = "Monitoreo";

    private HouseHelper dbHelper;
    private SQLiteDatabase db;

    Handler customHandler = new android.os.Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoreo);

        customHandler.post(sendData);

        dao = new HouseOperations(getApplicationContext());

        MyGestureDetector myGestureDetector = new MyGestureDetector();
        final GestureDetector gestureDetector = new GestureDetector(this, myGestureDetector);
        gestureDetector.setOnDoubleTapListener(myGestureDetector);

        tempTV = (TextView) findViewById(R.id.tempValueTV);
        luzTV = (TextView) findViewById(R.id.luzValueTV);
        statLuzTV = (TextView) findViewById(R.id.statLuzValueTV);
        statAlarmaTV = (TextView) findViewById(R.id.statAlarmaValueTV);
        txtRecom = (TextView) findViewById(R.id.txtRecom);

        buttonSound = MediaPlayer.create(MonitoreoActivity.this,R.raw.beep);

        if (isConnected()) {
            ParseQuery<ParseObject> queryObtencion = ParseQuery.getQuery("Monitoreo");
            //queryObtencion.fromLocalDatastore();
            queryObtencion.getInBackground("G1ejbjTEOj", new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {

                        tempTV.setText(object.get("temperatura").toString()+ " ºC");
                        luzTV.setText(object.get("luz").toString()+ " Candelas");
                        statLuzTV.setText(object.get("statLuz").toString());
                        statAlarmaTV.setText(object.get("statAlarma").toString());
                        if(Integer.parseInt(object.get("luz").toString())<=400) {
                            txtRecom.setText("Luz baja, abrir cortina para alumbrar");
                        } else if ((Integer.parseInt(object.get("luz").toString())>400)&&(Integer.parseInt(object.get("luz").toString())<=800)){
                            txtRecom.setText("Intensidad de luz adecuada");
                        } else if ((Integer.parseInt(object.get("luz").toString())>800)){
                            txtRecom.setText("Casa muy alumbrada, apagar luz");
                        }
                        //Toast.makeText(getApplicationContext(), "Información Actualizada", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(MonitoreoActivity.this, "No se encontró información en el Servidor", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No hay conexion", Toast.LENGTH_LONG).show();
        }


        //Declaracion de variables
        TextView nombreTV = (TextView) findViewById(R.id.nombreTV);
        casaIV = (ImageView) findViewById(R.id.casaIV);
        final Button encenderAlarmaButton = (Button) findViewById(R.id.encenderAlarmaButton);
        final Button encenderFocoButton = (Button) findViewById(R.id.encenderFocoButton);
        final Button apagarAlarmaButton = (Button) findViewById(R.id.apagarAlarmaButton);
        final Button apagarFocoButton = (Button) findViewById(R.id.apagarFocoButton);

        //Listener de Botones
        View.OnClickListener miListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(encenderAlarmaButton.isPressed()){
                    if (isConnected()) {
                    buttonSound.start();
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Manual");

                    query.getInBackground("6qhB3uYqqB", new GetCallback<ParseObject>() {
                        public void done(ParseObject manual, ParseException e) {
                            if (e == null) {

                                manual.put("enableAlarma", 1);
                                manual.saveInBackground();
                                Toast.makeText(MonitoreoActivity.this, "Petición de Encender Alarma Enviada", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "No hay conexion", Toast.LENGTH_LONG).show();
                }


                }else if(apagarAlarmaButton.isPressed()){
                    if (isConnected()) {
                    buttonSound.start();
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Manual");

                    query.getInBackground("6qhB3uYqqB", new GetCallback<ParseObject>() {
                        public void done(ParseObject manual, ParseException e) {
                            if (e == null) {

                                manual.put("enableAlarma", 0);
                                manual.saveInBackground();
                                Toast.makeText(MonitoreoActivity.this, "Petición de Apagar Alarma Enviada", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "No hay conexion", Toast.LENGTH_LONG).show();
                }


                }else if(encenderFocoButton.isPressed()){

                    if (isConnected()) {
                    buttonSound.start();
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Manual");

                    query.getInBackground("6qhB3uYqqB", new GetCallback<ParseObject>() {
                        public void done(ParseObject manual, ParseException e) {
                            if (e == null) {

                                manual.put("enableLuz", 1);
                                manual.saveInBackground();
                                Toast.makeText(MonitoreoActivity.this, "Petición de Encender Luz Enviada", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "No hay conexion", Toast.LENGTH_LONG).show();
                }

                }else if(apagarFocoButton.isPressed()){

                if (isConnected()) {
                    buttonSound.start();
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Manual");

                    query.getInBackground("6qhB3uYqqB", new GetCallback<ParseObject>() {
                        public void done(ParseObject manual, ParseException e) {
                            if (e == null) {

                                manual.put("enableLuz", 0);
                                manual.saveInBackground();
                                Toast.makeText(MonitoreoActivity.this, "Petición de Apagar Luz Enviada", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "No hay conexion", Toast.LENGTH_LONG).show();
                }
                }

            }
        };
        encenderAlarmaButton.setOnClickListener(miListener);
        encenderFocoButton.setOnClickListener(miListener);
        apagarAlarmaButton.setOnClickListener(miListener);
        apagarFocoButton.setOnClickListener(miListener);


        listaHouse = dao.getHouse();
        Bundle extras = getIntent().getExtras();
        i = extras.getInt("position");
        House house = listaHouse.get(i);


        id = house.getId();
        name = house.getName();
        cantCuartos = house.getCantCuartos();
        foto = house.getFoto();
        idFoto = house.getIdFoto();
        fecha = house.getFecha();


        fotoBit = BitmapFactory.decodeByteArray(foto, 0, foto.length);

        nombreTV.setText(name);
        casaIV.setImageBitmap(fotoBit);

        casaIV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, final MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
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

                                tempTV.setText(object.get("temperatura").toString()+ " ºC");
                                luzTV.setText(object.get("luz").toString()+ " Candelas");
                                statLuzTV.setText(object.get("statLuz").toString());
                                statAlarmaTV.setText(object.get("statAlarma").toString());
                                if(Integer.parseInt(object.get("luz").toString())<=400) {
                                    txtRecom.setText("Luz baja, abrir cortina para alumbrar");
                                } else if ((Integer.parseInt(object.get("luz").toString())>400)&&(Integer.parseInt(object.get("luz").toString())<=800)){
                                    txtRecom.setText("Intensidad de luz adecuada");
                                } else if ((Integer.parseInt(object.get("luz").toString())>800)){
                                    txtRecom.setText("Casa muy alumbrada, apagar luz");
                                }
                                Toast.makeText(getApplicationContext(), "Información Actualizada", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(MonitoreoActivity.this, "No se encontró información en el Servidor", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "No hay conexion", Toast.LENGTH_SHORT).show();
                }

                customHandler.postDelayed(this, 10000);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    public void onStop(){
        super.onStop();
        customHandler.removeCallbacks(sendData);
        //Log.i(LOG_TAG, "Entered the onStop() method");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        customHandler.removeCallbacks(sendData);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_monitoreo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    @Override
    protected void onPause() {
        dao.close();
        super.onPause();
        customHandler.removeCallbacks(sendData);
    }

    class MyGestureDetector implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG, "onDown: " + event.toString());
            return true;

        }


        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            //Toast.makeText(MonitoreoActivity.this, "onFling", Toast.LENGTH_SHORT).show();
            //Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());

            if (event1.getX() < event2.getX()) {
               // Toast.makeText(getApplicationContext(), "Left", Toast.LENGTH_SHORT).show();

            }

            if (event1.getX() > event2.getX()) {
               // Toast.makeText(getApplicationContext(), "Right", Toast.LENGTH_SHORT).show();


            }

            return true;
        }

        @Override
        public void onShowPress(MotionEvent event) {
            //Toast.makeText(MonitoreoActivity.this, "onShowPress", Toast.LENGTH_SHORT).show();
            Log.d(DEBUG_TAG, "onShowPress: " + event.toString());

            int xCoord = (int) event.getX();
            int yCoord = (int) event.getY();

            String Coord = "X:"+xCoord+"  "+"Y:"+yCoord;



            if (cantCuartos==2 && idFoto==0){
                Toast.makeText(MonitoreoActivity.this, Coord, Toast.LENGTH_SHORT).show();
                if ((xCoord >= 1030 && xCoord <= 1284) && (yCoord>=528 && yCoord<=1090)){
                    Toast.makeText(MonitoreoActivity.this, "Casa 2-1, Cuarto 1", Toast.LENGTH_SHORT).show();
                }
            } else if (cantCuartos==2 && idFoto==1){

            } else if (cantCuartos==3 && idFoto==0){
               // Toast.makeText(MonitoreoActivity.this, "entro if", Toast.LENGTH_SHORT).show();
                if ((xCoord >= 701 && xCoord <= 1216) && (yCoord>=33 && yCoord<=396)){
                    Toast.makeText(MonitoreoActivity.this, "Casa 3-1, Cuarto 1", Toast.LENGTH_SHORT).show();
                }
            }

            //Toast.makeText(MonitoreoActivity.this, yc, Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            //Toast.makeText(MonitoreoActivity.this, "onSingleTapUp", Toast.LENGTH_SHORT).show();
            Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent event) {
            //Toast.makeText(MonitoreoActivity.this, "onDoubleTap", Toast.LENGTH_SHORT).show();
            Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent event) {
            //Toast.makeText(MonitoreoActivity.this, "onDoubleTapEvent", Toast.LENGTH_SHORT).show();
            Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            // Toast.makeText(activity_config.this, "onScroll", Toast.LENGTH_SHORT).show();
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            //Toast.makeText(MonitoreoActivity.this, "onSingleTapConfirmed", Toast.LENGTH_SHORT).show();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent event) {
            //Toast.makeText(MonitoreoActivity.this, "onLongPress", Toast.LENGTH_SHORT).show();

        }


    }


}
