package holamundo.itesm.mx.houseundercontrol_v1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by renegalvez on 4/15/15.
 */
public class HouseHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "houseDB.db";
    private static final String TABLE_PRODUCT = "houses";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CANT = "cantCuartos";
    private static final String COLUMN_FOTO = "foto";
    private static final String COLUMN_ID_FOTO = "idFoto";
    private static final String COLUMN_FECHA = "fecha";
    private static final String COLUMN_ADDRESS = "address";

    public HouseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0){
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE "+TABLE_PRODUCT+"(" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " + COLUMN_CANT + " INTEGER," + COLUMN_FOTO+" BLOB, "
                + COLUMN_ID_FOTO + " INTEGER, "+COLUMN_FECHA+" TEXT, "+COLUMN_ADDRESS+" TEXT)";
        arg0.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2){
        Log.w(HouseHelper.class.getName(), "Upgrading database from version " +
                arg1 + " to " + arg2 + ", which will destroy all old data");
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        onCreate(arg0);
    }
}
