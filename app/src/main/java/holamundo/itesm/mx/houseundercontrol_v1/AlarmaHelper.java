package holamundo.itesm.mx.houseundercontrol_v1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Toño on 21/04/2015.
 */
public class AlarmaHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "houseDB.db";
    private static final String TABLE_ALARMA = "alarmas";
    private static final String COLUMNALARMA_ID = "_id";
    private static final String COLUMNALARMA_FECHA = "fecha";
    private static final String COLUMNALARMA_IDHOUSE = "idHouse";

    public AlarmaHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0){
        String CREATE_TABLE = "CREATE TABLE "+TABLE_ALARMA+"(" + COLUMNALARMA_ID + " INTEGER PRIMARY KEY, " +
                COLUMNALARMA_FECHA + " TEXT, "+COLUMNALARMA_IDHOUSE+" INTEGER)";
        arg0.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2){
        Log.w(HouseHelper.class.getName(), "Upgrading database from version " +
                arg1 + " to " + arg2 + ", which will destroy all old data");
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARMA);
        onCreate(arg0);
    }
}
