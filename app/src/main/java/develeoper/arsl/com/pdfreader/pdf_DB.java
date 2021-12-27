package develeoper.arsl.com.pdfreader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class pdf_DB extends SQLiteOpenHelper {
    public static final String DB_NAME = "note.db";
    public static final int DB_VERSION = 4;
    //user table
    public static final String TABLE_NOTE = "notes";
    public static final String ID = "id";
    public static final String PDF_PATH = "path";
    public static final String NAME = "name";


    public pdf_DB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query  = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTE + " ( " +
                ID + " INTEGER  , " +
                NAME + " TEXT , " +
                PDF_PATH + " TEXT PRIMARY KEY )";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NOTE ;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }
}