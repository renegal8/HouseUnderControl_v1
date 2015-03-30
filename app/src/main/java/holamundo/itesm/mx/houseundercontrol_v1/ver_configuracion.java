package holamundo.itesm.mx.houseundercontrol_v1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ver_configuracion extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_configuracion);

        TextView nombreTV = (TextView) findViewById(R.id.nombreValueTV);
        TextView cantidadTV = (TextView) findViewById(R.id.cantidadValueTV);

        Bundle bundle = getIntent().getExtras();
        String n = bundle.getString("nombre");
        String c =bundle.getString("cantidad");

        nombreTV.setText(n);
        cantidadTV.setText(c);

    }

}
