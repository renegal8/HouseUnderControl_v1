package holamundo.itesm.mx.houseundercontrol_v1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ver_configuracion extends ActionBarActivity {

    ImageView casaIV;
    Bitmap imageBP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_configuracion);

        TextView nombreTV = (TextView) findViewById(R.id.nombreValueTV);
        TextView cantidadTV = (TextView) findViewById(R.id.cantidadValueTV);
        casaIV = (ImageView) findViewById(R.id.casaIV);

        Bundle bundle = getIntent().getExtras();
        String n = bundle.getString("nombre");
        String c = bundle.getString("cantidad");
//        imagenBP = (Bitmap) bundle.getParcelable("photo");
//        casaIV.setImageBitmap(imagenBP);
        String photo = bundle.getString("photo");

        nombreTV.setText(n);
        cantidadTV.setText(c);
        if (!photo.matches("")) {

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
    }
//

    }

}
