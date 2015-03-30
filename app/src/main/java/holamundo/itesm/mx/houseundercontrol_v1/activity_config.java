package holamundo.itesm.mx.houseundercontrol_v1;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class activity_config extends ActionBarActivity {
    String LOG_TAG = "ConfigurarActivity";
    String[] cuartos = new String[]{};
    ArrayAdapter<String> adapterCuartos;
    Button guardarBtn;
    TextView cantCuartosTV;
    EditText nombreET;
    ImageView img;
    Bitmap imageBP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_config);

        ListView cuartosLV = (ListView) findViewById(R.id.cuartosLV);
        cantCuartosTV = (TextView) findViewById(R.id.cantCuartosValueTV);
        nombreET = (EditText)findViewById(R.id.nombreValueET);
        guardarBtn = (Button) findViewById(R.id.botonGuardar);
        img = (ImageView) findViewById(R.id.casaIV);

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cantidad = ((TextView)view).getText().toString();
                if(cantidad=="1"){
                    cantCuartosTV.setText("1");
 //                   img.setImageResource(R.mipmap.casa1);
                    imageBP = BitmapFactory.decodeResource(getResources(),R.mipmap.casa1);
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


                if(nombreET.getText().toString().trim().matches("")|| cantCuartosTV.getText().toString().trim().matches(""))
                {
                    Toast.makeText(getApplicationContext(), "Faltan Datos por Llenar", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("name", nombreET.getText().toString());
                        returnIntent.putExtra("cantidad", cantCuartosTV.getText().toString());
//                        returnIntent.putExtra("photo", imageBP);
                        returnIntent.putExtra("photo", cantCuartosTV.getText().toString());
                        setResult(RESULT_OK, returnIntent);
                        Toast.makeText(getApplicationContext(), "Configuracion Correcta", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "Failed to send intent", e);
                    }
                }
            }
        });
    }


}
