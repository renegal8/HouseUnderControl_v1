package holamundo.itesm.mx.houseundercontrol_v1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;


public class MapActivity extends ActionBarActivity {

    HouseOperations dao;
    List<House> listaHouse;
    MediaPlayer buttonSound;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        buttonSound = MediaPlayer.create(MapActivity.this, R.raw.beep);
        dao = new HouseOperations(getApplicationContext());

        final TextView addressTV = (TextView)findViewById(R.id.addressTV);
        final Button localizarBtn = (Button) findViewById(R.id.localizarBtn);

        listaHouse = dao.getHouse();

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("position");
        House house = listaHouse.get(id);

        String address = house.getAddress();

        addressTV.setText(address);


        View.OnClickListener miListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                buttonSound.start();
                String direccionStr = addressTV.getText().toString();
                direccionStr = direccionStr.replace(' ', '+');
                Uri uri = Uri.parse("geo:0,0?q=" + direccionStr);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);


            }
        };

        localizarBtn.setOnClickListener(miListener);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
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
