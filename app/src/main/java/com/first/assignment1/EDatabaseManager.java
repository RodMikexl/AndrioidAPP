package com.first.assignment1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;


public class EDatabaseManager {
    public static final String DB_NAME = "PersonDatajack2";
    public static final String DB_TABLE = "EventInfojack";
    public static final int DB_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (event_id integer primary key autoincrement NOT NULL, task_name TEXT, location TEXT," +
            " status INTEGER , date TEXT, time TEXT);";
    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public EDatabaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public EDatabaseManager openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public boolean addRow(String tn, String loc, Integer sta, String date, String time) {
        synchronized(this.db) {
            ContentValues newEvent = new ContentValues();
            newEvent.put("task_name", tn);
            newEvent.put("location", loc);
            newEvent.put("status", sta);
            newEvent.put("date", date);
            newEvent.put("time", time);
            try {
                db.insertOrThrow(DB_TABLE, null, newEvent);
            } catch (Exception e) {
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }
            //db.close();
            return true;
        }
    }

    public ArrayList<String> retPast(int currTime) {
        ArrayList<String> eventPast = new ArrayList<String>();
        String[] columns = new String[] {"task_name","time","date","status"};
        Cursor cursor = db.query(DB_TABLE, columns, "date < ?",new String[]{Integer.toString(currTime)}, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            eventPast.add(cursor.getString(0) + ","+ Integer.toString(cursor.getInt(1))+ ","+ Integer.toString(cursor.getInt(2))+ ","+ Integer.toString(cursor.getInt(3))) ;
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return eventPast;
    }

    public ArrayList<String> retNow(int currTime) {
        ArrayList<String> eventNow = new ArrayList<String>();
        String[] columns = new String[] {"task_name","time","date","status"};
        Cursor cursor = db.query(DB_TABLE, columns, "date >= ?",new String[]{Integer.toString(currTime)}, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            eventNow.add(cursor.getString(0) + ","+ Integer.toString(cursor.getInt(1))+ ","+ Integer.toString(cursor.getInt(2))+ ","+ Integer.toString(cursor.getInt(3))) ;
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return eventNow;
    }


    public ArrayList<String> retComplete(int com) {
        ArrayList<String> eventComplete = new ArrayList<String>();
        String[] columns = new String[] {"event_id","task_name","location","status","date","time"};
        Cursor cursor = db.query(DB_TABLE, columns, "status = ?", new String[]{Integer.toString(com)}, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            eventComplete.add(Integer.toString(cursor.getInt(0))+","+cursor.getString(1)+","+cursor.getString(2)+","+Integer.toString(cursor.getInt(3))+","+cursor.getString(4)+","+cursor.getString(5)) ;
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return eventComplete;
    }

    public ArrayList<String> retfirstpage() {
        ArrayList<String> eventComplete = new ArrayList<String>();
        String[] columns = new String[] {"event_id","task_name","location","status","date","time"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            eventComplete.add(Integer.toString(cursor.getInt(0))+","+cursor.getString(1)+","+cursor.getString(2)+","+Integer.toString(cursor.getInt(3))+","+cursor.getString(4)+","+cursor.getString(5)) ;
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return eventComplete;
    }

    public ArrayList<String> retall(int dataId) {
        ArrayList<String> eventComplete = new ArrayList<String>();
        String[] columns = new String[] {"event_id","task_name","location","status","date","time"};
        Cursor cursor = db.query(DB_TABLE, columns, "event_id = ?",new String[]{Integer.toString(dataId)}, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            eventComplete.add(Integer.toString(cursor.getInt(0))+","+cursor.getString(1)+","+cursor.getString(2)+","+Integer.toString(cursor.getInt(3))+","+cursor.getString(4)+","+cursor.getString(5)) ;
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return eventComplete;
    }

    public boolean deleteRow(Integer i){
        synchronized (this.db){
            db.delete(DB_TABLE, "event_id=?",new String[]{Integer.toString(i)});
        }
        return true;
    }

    public  boolean update(Integer id, String tn, String loc, Integer sta, String date,String time){

        synchronized (this.db){
            ContentValues updateProduct = new ContentValues();
            updateProduct.put("task_name", tn);
            updateProduct.put("location", loc);
            updateProduct.put("status", sta);
            updateProduct.put("date", date);
            updateProduct.put("time", time);
        try {
            db.update(DB_TABLE, updateProduct,"event_id = ?",new String[]{Integer.toString(id)});
        } catch (Exception e) {
            Log.e("Error in update", e.toString());
            e.printStackTrace();
            return false;
        }
    }
        return true;
    }


    public void clearRecords()
    {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE, null, null);
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
