package holamundo.itesm.mx.houseundercontrol_v1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toño on 21/04/2015.
 */
public class AlarmaOperations {

    private SQLiteDatabase db;
    private AlarmaHelper dbHelper;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "houseDB.db";
    private static final String TABLE_ALARMA = "alarmas";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FECHA = "fecha";
    private static final String COLUMN_IDHOUSE = "idHouse";

    Context context1;
    public AlarmaOperations(Context context){
        dbHelper = new AlarmaHelper(context);
        //db = new SQLiteDatabase();
        context1 = context;
    }
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public void addAlarma(Alarma alarma) {

        ContentValues values = new ContentValues();

        values.put(COLUMN_FECHA, alarma.getFecha());
        values.put(COLUMN_IDHOUSE, alarma.getIdHouse());

        db.insert(TABLE_ALARMA, null, values);
    }

    public List<Alarma> getAll(){
        List<Alarma> listaAlarmas = new ArrayList<Alarma>();

        db = dbHelper.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_ALARMA;
        //Toast.makeText(context1, selectQuery, Toast.LENGTH_SHORT).show();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                Alarma alarma = new Alarma( cursor.getString(1), Integer.parseInt(cursor.getString(2)));
                //Toast.makeText(context1, cursor.getString(0), Toast.LENGTH_SHORT).show();
                listaAlarmas.add(alarma);

            } while(cursor.moveToNext());
        }
        cursor.close();
        return listaAlarmas;

    }

}
