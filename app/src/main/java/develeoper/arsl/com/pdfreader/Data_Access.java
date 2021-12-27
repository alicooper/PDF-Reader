package develeoper.arsl.com.pdfreader;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class Data_Access {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    public Data_Access(@Nullable Context context) {
        openHelper = new pdf_DB(context);
    }
    public void openDB(){
        database = openHelper.getWritableDatabase();
    }
    public void closeDB(){
        database.close();
    }

    public boolean addNewNote(select_pdf model){
        try {
            String Path = model.getPath_file();
            String Name = model.getName_file();
            ContentValues cv = new ContentValues();
            cv.put(pdf_DB.NAME,Name);
            cv.put(pdf_DB.PDF_PATH,Path);
            database.insert(pdf_DB.TABLE_NOTE,null,cv);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @SuppressLint("Range")
    public ArrayList<select_pdf> getall() {
        String PATH;
        String NAME;
        String ID;

        ArrayList<select_pdf> arrayList = new ArrayList<>();
        String query = "SELECT * FROM " + pdf_DB.TABLE_NOTE + " ORDER BY id DESC";
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                PATH = cursor.getString(cursor.getColumnIndex(pdf_DB.PDF_PATH));
                ID = cursor.getString(cursor.getColumnIndex(pdf_DB.ID));

                NAME = cursor.getString(cursor.getColumnIndex(pdf_DB.NAME));
                arrayList.add(new select_pdf(PATH, NAME, ID));

            }while (cursor.moveToNext());
        }
        return arrayList;
    }

    public boolean deleteBYid(String id) {
        try {
            database.delete(pdf_DB.TABLE_NOTE, "id = " + id, null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteBYADD(String add) {
        try {
            database.delete(pdf_DB.TABLE_NOTE, pdf_DB.NAME + " = '" + add + "'", null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
