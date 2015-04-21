package holamundo.itesm.mx.houseundercontrol_v1;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;


public class ver_configuracion extends ActionBarActivity {

    ImageView casaIV;
    Bitmap imageBP;
    Bitmap fotoBit;

    int id;
    String name;
    String cantCuartos;
    byte[] foto;
    int idFoto;
    String fecha;
    String address;
    HouseOperations dao;
    List<House> listaHouse;


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

        listaHouse = dao.getAll();

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("position");
        House house = listaHouse.get(id);

        byte[] foto =  house.getFoto();
        //        int idFoto =  house.getId();
        //        String fecha =  house.getFecha();

        fotoBit = BitmapFactory.decodeByteArray(foto, 0, foto.length);

        nombreTV.setText(String.valueOf(house.getName()));
        addressTV.setText(String.valueOf( house.getAddress()));
        cantidadTV.setText(String.valueOf( house.getCantCuartos()));
        casaIV.setImageBitmap(fotoBit);




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

}
