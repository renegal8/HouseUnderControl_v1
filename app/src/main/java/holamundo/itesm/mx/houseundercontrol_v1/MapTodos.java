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


public class MapTodos extends ActionBarActivity {

    HouseOperations dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_todos);

        dao = new HouseOperations(this);
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ListView lista = (ListView) findViewById(R.id.listView);
        final HouseListAdapter houseListAdapter = new HouseListAdapter(getApplicationContext(), R.layout.activity_rowhouse, getApplianceListView());
        lista.setAdapter(houseListAdapter);

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent unoIntent = new Intent(MapTodos.this, MapActivity.class);
                unoIntent.putExtra("position", position);
                startActivity(unoIntent);
            }
        };

        lista.setOnItemClickListener(itemListener);


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
