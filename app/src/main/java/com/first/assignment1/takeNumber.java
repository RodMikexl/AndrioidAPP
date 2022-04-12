package com.first.assignment1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class takeNumber {
    public static final String DB_NAME = "PersonData";
    public static final String DB_TABLE = "takeNumber";
    public static final int DB_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (id INTERGER, number INTEGER);";
    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public takeNumber(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public takeNumber openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public boolean addRow(Integer id,Integer number) {

        synchronized(this.db) {
            ContentValues newNumber = new ContentValues();
            newNumber.put("id", id);
            newNumber.put("number", number);
            try {
                db.insertOrThrow(DB_TABLE, null, newNumber);
            } catch (Exception e) {
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }
            //db.close();
            return true;
        }
    }

    public ArrayList<String> retrieveRows() {
        ArrayList<String> productRows = new ArrayList<String>();
        String[] columns = new String[] {"number"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            productRows.add(Integer.toString(cursor.getInt(0))+ Integer.toString(cursor.getInt(1))) ;
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return productRows;
    }



    public  boolean update(Integer id,Integer number){

        synchronized (this.db){
            ContentValues updateNumber = new ContentValues();
            updateNumber.put("number", number);
            try {
                db.update("takeNumber", updateNumber,"id = ?",new String[]{Integer.toString(id)});
            } catch (Exception e) {
                Log.e("Error in update", e.toString());
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper (Context c) {
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Products table", "Upgrading database i.e. dropping table and re-creating it");
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
        }
    }
}
