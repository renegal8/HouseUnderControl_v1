package holamundo.itesm.mx.houseundercontrol_v1;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;


public class MonitoreoActivity extends ActionBarActivity {

    ImageView casaIV;
    Bitmap imageBP;
    Bitmap fotoBit;

    int id;
    String name;
    int cantCuartos;
    byte[] foto;
    int idFoto;
    String fecha;

    HouseOperations dao;
    List<House> house;

    int i = 0;

    String DEBUG_TAG = "Monitoreo";

    private HouseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoreo);

        dao = new HouseOperations(getApplicationContext());

        MyGestureDetector myGestureDetector = new MyGestureDetector();
        final GestureDetector gestureDetector = new GestureDetector(this, myGestureDetector);
        gestureDetector.setOnDoubleTapListener(myGestureDetector);

        TextView nombreTV = (TextView) findViewById(R.id.nombreTV);
        casaIV = (ImageView) findViewById(R.id.casaIV);

        house = dao.getHouse();

        i = house.size() - 1;

        id = house.get(i).getId();
        name = house.get(i).getName();
        cantCuartos = house.get(i).getCantCuartos();
        foto = house.get(i).getFoto();
        idFoto = house.get(i).getIdFoto();
        fecha = house.get(i).getFecha();


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
    }

    @Override
    protected void onPause() {
        dao.close();
        super.onPause();
    }

    class MyGestureDetector implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG, "onDown: " + event.toString());
            return true;

        }


        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            Toast.makeText(MonitoreoActivity.this, "onFling", Toast.LENGTH_SHORT).show();
            //Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());

            if (event1.getX() < event2.getX()) {
                Toast.makeText(getApplicationContext(), "Left", Toast.LENGTH_SHORT).show();

            }

            if (event1.getX() > event2.getX()) {
                Toast.makeText(getApplicationContext(), "Right", Toast.LENGTH_SHORT).show();


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
