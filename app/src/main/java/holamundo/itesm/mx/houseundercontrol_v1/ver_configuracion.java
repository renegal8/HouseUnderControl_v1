package holamundo.itesm.mx.houseundercontrol_v1;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class ver_configuracion extends ActionBarActivity {

    ImageView casaIV;
    Bitmap imagenBP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_configuracion);

        TextView nombreTV = (TextView) findViewById(R.id.nombreValueTV);
        TextView cantidadTV = (TextView) findViewById(R.id.cantidadValueTV);
        casaIV = (ImageView) findViewById(R.id.casaIV);

        Bundle bundle = getIntent().getExtras();
        String n = bundle.getString("nombre");
        String c =bundle.getString("cantidad");
        imagenBP = (Bitmap) bundle.getParcelable("photo");
        casaIV.setImageBitmap(imagenBP);

        nombreTV.setText(n);
        cantidadTV.setText(c);

    }

}
