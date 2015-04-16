package holamundo.itesm.mx.houseundercontrol_v1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by renegalvez on 4/15/15.
 */
public class HouseOperations {
    private SQLiteDatabase db;
    private HouseHelper dbHelper;

    private static final String TABLE_PRODUCT = "houses";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CANT = "cantCuartos";
    private static final String COLUMN_FOTO = "foto";
    private static final String COLUMN_ID_FOTO = "idFoto";
    private static final String COLUMN_FECHA = "fecha";

    Context context1;

    public HouseOperations(Context context){
        dbHelper = new HouseHelper(context);
        //db = new SQLiteDatabase();
        context1 = context;
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public void addHouse(House house) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, house.getName());
        values.put(COLUMN_CANT, house.getCantCuartos());
        values.put(COLUMN_FOTO, house.getFoto());
        values.put(COLUMN_ID_FOTO, house.getIdFoto());
        values.put(COLUMN_FECHA, house.getFecha());

        db.insert(TABLE_PRODUCT, null, values);
    }

    public House findProduct(String name) {
        String query = "SELECT * FROM " + TABLE_PRODUCT + " WHERE " + COLUMN_NAME + " = \"" + name + "\"";

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        House product;

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            product = new House(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    Integer.parseInt(cursor.getString(2)),
                    cursor.getBlob(3),
                    Integer.parseInt(cursor.getString(4)),
                    cursor.getString(5));
            cursor.close();
        } else {
            product =  null;
        }

        return product;

    }

    public List<House> getAllProducts(){
        List<House> listaProducts = new ArrayList<House>();

        db = dbHelper.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_PRODUCT;
        //Toast.makeText(context1, selectQuery, Toast.LENGTH_SHORT).show();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                House product = new House(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        Integer.parseInt(cursor.getString(2)),
                        cursor.getBlob(3),
                        Integer.parseInt(cursor.getString(4)),
                        cursor.getString(5));
                //Toast.makeText(context1, cursor.getString(0), Toast.LENGTH_SHORT).show();
                listaProducts.add(product);

            } while(cursor.moveToNext());
        }

        //Toast.makeText(context1, (CharSequence) listaProducts, Toast.LENGTH_SHORT).show();

        cursor.close();
        return listaProducts;

    }

    public List<House> getHouse(){
        List<House> listaHouses = new ArrayList<House>();

        db = dbHelper.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_PRODUCT;
        //Toast.makeText(context1, selectQuery, Toast.LENGTH_SHORT).show();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                House product = new House(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        Integer.parseInt(cursor.getString(2)),
                        cursor.getBlob(3),
                        Integer.parseInt(cursor.getString(4)),
                        cursor.getString(5));
                //Toast.makeText(context1, cursor.getString(0), Toast.LENGTH_SHORT).show();
                listaHouses.add(product);

            } while(cursor.moveToNext());
        }


        cursor.close();
        return listaHouses;

    }
}
