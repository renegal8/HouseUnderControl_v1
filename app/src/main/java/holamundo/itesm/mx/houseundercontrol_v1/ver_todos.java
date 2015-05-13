package holamundo.itesm.mx.houseundercontrol_v1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

public class ver_todos extends ActionBarActivity {

    HouseOperations dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_todos);

        dao = new HouseOperations(this);
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "De click en una casa para ver su información. Deje presionado una casa para eliminarla.", Toast.LENGTH_LONG).show();

        final ListView lista = (ListView) findViewById(R.id.listView);
        final HouseListAdapter houseListAdapter = new HouseListAdapter(getApplicationContext(), R.layout.activity_rowhouse, getApplianceListView());
        lista.setAdapter(houseListAdapter);

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent unoIntent = new Intent(ver_todos.this, ver_configuracion.class);
                unoIntent.putExtra("position", position);
                startActivity(unoIntent);
            }
        };
        lista.setOnItemClickListener(itemListener);

        AdapterView.OnItemLongClickListener itemLongListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ver_todos.this);
                alert.setTitle("Eliminar Casa");
                alert.setMessage("Desea borrar la Casa?");

                alert.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(getApplicationContext(), "Casa Borrada", Toast.LENGTH_SHORT).show();
                        House a = (House) lista.getItemAtPosition(position);
                        dao.deleteHouse(a.getName());
                        houseListAdapter.update(getApplianceListView());
                    }
                });
                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                return true;
            }
        };
        lista.setOnItemLongClickListener(itemLongListener);

    }

    public List<House> getApplianceListView() {
        List<House> listaAppliance = dao.getAll();
        return listaAppliance;
    }


    @Override
    protected void onResume() {
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

