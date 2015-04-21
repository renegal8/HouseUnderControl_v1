package holamundo.itesm.mx.houseundercontrol_v1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;


public class ver_alarmas extends ActionBarActivity {

    HouseOperations dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_alarmas);

        dao = new HouseOperations(this);
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ListView lista = (ListView) findViewById(R.id.listView);
        final AlarmaListAdapter alarmaListAdapter = new AlarmaListAdapter(getApplicationContext(), R.layout.activity_rowalarma, getApplianceListView());
        lista.setAdapter(alarmaListAdapter);

    }

    public List<Alarma> getApplianceListView() {
        List<Alarma> listaAlarma = dao.getAllAlarma();
        return listaAlarma;
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
