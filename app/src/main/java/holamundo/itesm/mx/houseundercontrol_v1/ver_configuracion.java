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
    List<House> house;

    int i = 0;

    private HouseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_configuracion);

        dao = new HouseOperations(getApplicationContext());

        TextView nombreTV = (TextView) findViewById(R.id.nombreValueTV);
        TextView addressTV = (TextView) findViewById(R.id.addressValueTV);
        TextView cantidadTV = (TextView) findViewById(R.id.cantidadValueTV);
        casaIV = (ImageView) findViewById(R.id.casaIV);

        house = dao.getHouse();

        i = house.size() - 1;

        //for (i=0; i<house.size(); i++) {
            //if (i) {
                int id = house.get(i).getId();
                String name = house.get(i).getName();
                String cantCuartos = String.valueOf(house.get(i).getCantCuartos());
                byte[] foto = house.get(i).getFoto();
                int idFoto = house.get(i).getId();
                String fecha = house.get(i).getFecha();
                String address = house.get(i).getAddress();
            //}
        //}

        fotoBit = BitmapFactory.decodeByteArray(foto, 0, foto.length);

        nombreTV.setText(name);
        addressTV.setText(address);
        cantidadTV.setText(cantCuartos);
        casaIV.setImageBitmap(fotoBit);

        // INTENT
       /* Bundle bundle = getIntent().getExtras();
        String n = bundle.getString("nombre");
        String c = bundle.getString("cantidad");
        String photo = bundle.getString("photo"); */

     /*   nombreTV.setText(n);
        cantidadTV.setText(c); */
     /*   if (!photo.matches("")) {

        if (photo.equals("1")) {
            imageBP = BitmapFactory.decodeResource(getResources(), R.mipmap.casa1);
            casaIV.setImageBitmap(imageBP);
        } else if (photo.equals("2")) {
            imageBP = BitmapFactory.decodeResource(getResources(), R.mipmap.casa2);
            casaIV.setImageBitmap(imageBP);
        } else if (photo.equals("3")) {
            imageBP = BitmapFactory.decodeResource(getResources(), R.mipmap.casa3);
            casaIV.setImageBitmap(imageBP);
        } else if (photo.equals("4")) {
            imageBP = BitmapFactory.decodeResource(getResources(), R.mipmap.casa4);
            casaIV.setImageBitmap(imageBP);
        }
    } */
//

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
