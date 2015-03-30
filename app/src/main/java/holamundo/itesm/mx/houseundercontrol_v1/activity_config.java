package holamundo.itesm.mx.houseundercontrol_v1;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;


public class activity_config extends ActionBarActivity {

    String[] cuartos = new String[]{};
    ArrayAdapter<String> adapterCuartos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_config);

        ListView cuartosLV = (ListView) findViewById(R.id.cuartosLV);

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {



            }
        };

        cuartosLV.setOnItemClickListener(itemListener);

        cuartos = new String[]{
                "1 Cuarto", "2 Cuartos", "3 Cuartos", "4 Cuartos"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.activity_row, R.id.rowTV, cuartos);

        cuartosLV.setAdapter(adapter);

        ImageView img= (ImageView) findViewById(R.id.casaIV);
        img.setImageResource(R.mipmap.casa1);

        Button guardarBtn = (Button) findViewById(R.id.guardarBtn);

        guardarBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_config.this, MainActivity.class);
                //                       intent.putExtra("email", emailET.getText().toString());
                //                       intent.putExtra("password", passwordET.getText().toString());

                startActivity(intent);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_config, menu);
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
}
