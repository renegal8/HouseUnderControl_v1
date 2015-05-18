package holamundo.itesm.mx.houseundercontrol_v1;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class editConfiguration extends ActionBarActivity {
        String LOG_TAG = "ConfigurarActivity";
        String DEBUG_TAG = "Configuracion";
        String[] cuartos = new String[]{};
        ArrayAdapter<String> adapterCuartos;
        Button guardarBtn;
        TextView cantCuartosTV;
        EditText nombreET;
        EditText addressET;
        ImageView img;
        Bitmap imageBP;
       String nameOld;

        MediaPlayer buttonSound;

        int index;

        private HouseOperations dao;
        private HouseHelper dbHelper;
        private SQLiteDatabase db;

        private static final Integer[] cuarto1 = { R.mipmap.casa1,R.mipmap.casa1_2};
        private static final Integer[] cuarto2 = { R.mipmap.casa2,R.mipmap.casa2_2,R.mipmap.casa2_3};
        private static final Integer[] cuarto3 = { R.mipmap.casa3,R.mipmap.casa3_2};
        private static final Integer[] cuarto4 = { R.mipmap.casa4,R.mipmap.casa4_2};
        private static final Integer[] arrT = new Integer[10];

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_activity_config);

            dao = new HouseOperations(getApplicationContext());

            MyGestureDetector myGestureDetector = new MyGestureDetector();
            final GestureDetector gestureDetector = new GestureDetector(this, myGestureDetector);
            gestureDetector.setOnDoubleTapListener(myGestureDetector);

            ListView cuartosLV = (ListView) findViewById(R.id.cuartosLV);
            cantCuartosTV = (TextView) findViewById(R.id.cantCuartosValueTV);
            nombreET = (EditText)findViewById(R.id.nombreValueET);
            addressET = (EditText)findViewById(R.id.addressET);
            guardarBtn = (Button) findViewById(R.id.botonGuardar);
            img = (ImageView) findViewById(R.id.casaIV);

            Bundle extras = getIntent().getExtras();
            nameOld = extras.getString("position");

            buttonSound = MediaPlayer.create(editConfiguration.this, R.raw.beep);

            Toast.makeText(getApplicationContext(), "Favor de introducir los nuevos valores de la casa", Toast.LENGTH_LONG).show();

            AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    buttonSound.start();
                    String cantidad = ((TextView)view).getText().toString();
                    if(cantidad=="1"){
                        cantCuartosTV.setText("1");
                        //                   img.setImageResource(R.mipmap.casa1);
                        imageBP = BitmapFactory.decodeResource(getResources(), R.mipmap.casa1);
                        img.setImageBitmap(imageBP);}
                    else if(cantidad=="2"){
                        cantCuartosTV.setText("2");
//                    img.setImageResource(R.mipmap.casa2);
                        imageBP = BitmapFactory.decodeResource(getResources(),R.mipmap.casa2);
                        img.setImageBitmap(imageBP);}
                    else if(cantidad=="3"){
                        cantCuartosTV.setText("3");
//                    img.setImageResource(R.mipmap.casa3);
                        imageBP = BitmapFactory.decodeResource(getResources(),R.mipmap.casa3);
                        img.setImageBitmap(imageBP);}
                    else if(cantidad=="4"){
                        cantCuartosTV.setText("4");
//                    img.setImageResource(R.mipmap.casa4);
                        imageBP = BitmapFactory.decodeResource(getResources(),R.mipmap.casa4);
                        img.setImageBitmap(imageBP);}

                    index = 0;
                }
            };

            cuartosLV.setOnItemClickListener(itemListener);

            cuartos = new String[]{
                    "1", "2", "3", "4"
            };

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.activity_row, R.id.rowTV, cuartos);

            cuartosLV.setAdapter(adapter);


            guardarBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    buttonSound.start();

                    if(nombreET.getText().toString().trim().matches("")|| cantCuartosTV.getText().toString().trim().matches(""))
                    {
                        Toast.makeText(getApplicationContext(), "Faltan Datos por Llenar", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        editHouse(v);
                    }
                }
            });

            img.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, final MotionEvent event) {
                    gestureDetector.onTouchEvent(event);
                    return true;
                }
            });
        }

        public void editHouse(View view){

            String name = nombreET.getText().toString();
            int cantCuartos = Integer.parseInt(cantCuartosTV.getText().toString());
            int idFoto = index;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            //bp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageBP.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteImage = stream.toByteArray();
            String address = addressET.getText().toString();

            Calendar c = Calendar.getInstance();
            System.out.println("Current time =&gt; "+c.getTime());

            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String fecha = df.format(c.getTime());


            House house = new House(name , cantCuartos, byteImage, idFoto, fecha, address);
            dao.editHouse(house,nameOld);

            Toast.makeText(getApplicationContext(), "Casa Editada Exitosamente", Toast.LENGTH_SHORT).show();


            finish();

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
                //Toast.makeText(activity_config.this, "onFling", Toast.LENGTH_SHORT).show();
                //Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());

                if (event1.getX() < event2.getX()) {
                    //Toast.makeText(getApplicationContext(), "Left", Toast.LENGTH_SHORT).show();
                    index = index - 1;
                    if (index < 0) {
                        switch (Integer.parseInt(cantCuartosTV.getText().toString())) {
                            case 1:
                                index = cuarto1.length - 1;
                                break;
                            case 2:
                                index = cuarto2.length - 1;
                                break;
                            case 3:
                                index = cuarto3.length - 1;
                                break;
                            case 4:
                                index = cuarto4.length - 1;
                                break;

                        }
                    }
                    //Drawable d = getResources().getDrawable(R.mipmap.casa1);
                    switch (Integer.parseInt(cantCuartosTV.getText().toString())) {
                        case 1:
                            //d = getResources().getDrawable(cuarto1[index]);
                            imageBP = BitmapFactory.decodeResource(getResources(),cuarto1[index]);
                            break;
                        case 2:
                            //d = getResources().getDrawable(cuarto2[index]);
                            imageBP = BitmapFactory.decodeResource(getResources(),cuarto2[index]);
                            break;
                        case 3:
                            //d = getResources().getDrawable(cuarto3[index]);
                            imageBP = BitmapFactory.decodeResource(getResources(),cuarto3[index]);
                            break;
                        case 4:
                            //d = getResources().getDrawable(cuarto4[index]);
                            imageBP = BitmapFactory.decodeResource(getResources(),cuarto4[index]);
                            break;
                        //Drawable d = getResources().getDrawable(imagenes[index]);
                    }

                    //img.setImageDrawable(d);
                    img.setImageBitmap(imageBP);
                }

                if (event1.getX() > event2.getX()) {
                    // Toast.makeText(getApplicationContext(), "Right", Toast.LENGTH_SHORT).show();
                    index = index + 1;
                    int cantSel = 0;
                    switch (Integer.parseInt(cantCuartosTV.getText().toString())) {
                        case 1:
                            cantSel = cuarto1.length;
                            break;
                        case 2:
                            cantSel = cuarto2.length;
                            break;
                        case 3:
                            cantSel = cuarto3.length;
                            break;
                        case 4:
                            cantSel = cuarto4.length;
                            break;
                    }
                    if (index > (cantSel - 1))
                        index = 0;

                    //Drawable e = getResources().getDrawable(R.mipmap.casa1);
                    ;
                    switch (Integer.parseInt(cantCuartosTV.getText().toString())) {
                        case 1:
                            //e = getResources().getDrawable(cuarto1[index]);
                            imageBP = BitmapFactory.decodeResource(getResources(),cuarto1[index]);
                            break;
                        case 2:
                            //e = getResources().getDrawable(cuarto2[index]);
                            imageBP = BitmapFactory.decodeResource(getResources(),cuarto2[index]);
                            break;
                        case 3:
                            //e = getResources().getDrawable(cuarto3[index]);
                            imageBP = BitmapFactory.decodeResource(getResources(),cuarto3[index]);
                            break;
                        case 4:
                            //e = getResources().getDrawable(cuarto4[index]);
                            imageBP = BitmapFactory.decodeResource(getResources(),cuarto4[index]);
                            break;
                    }

                    //img.setImageDrawable(e);
                    img.setImageBitmap(imageBP);

                }

                return true;
            }

            @Override
            public void onShowPress(MotionEvent event) {
                //Toast.makeText(activity_config.this, "onShowPress", Toast.LENGTH_SHORT).show();
                Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
            }

            @Override
            public boolean onSingleTapUp(MotionEvent event) {
                // Toast.makeText(activity_config.this, "onSingleTapUp", Toast.LENGTH_SHORT).show();
                Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent event) {
                //Toast.makeText(activity_config.this, "onDoubleTap", Toast.LENGTH_SHORT).show();
                Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent event) {
                // Toast.makeText(activity_config.this, "onDoubleTapEvent", Toast.LENGTH_SHORT).show();
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
                // Toast.makeText(activity_config.this, "onSingleTapConfirmed", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public void onLongPress(MotionEvent event) {
                //Toast.makeText(activity_config.this, "onLongPress", Toast.LENGTH_SHORT).show();

            }


        }


        public void onBackPressed() {
            super.onBackPressed();
            Toast.makeText(this, "Operacion Cancelada", Toast.LENGTH_SHORT).show();
            return;
        }

    }
