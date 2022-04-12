package com.first.assignment1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class FDatabaseManager {
    public static final String DB_NAME = "PersonDatajack";
    public static final String DB_TABLE = "FriendInfo";
    public static final int DB_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (friend_id integer primary key autoincrement NOT NULL, friend_fname TEXT, friend_lname TEXT," +
            "gender TEXT, age INTEGER , address TEXT);";
    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;


    public FDatabaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public FDatabaseManager openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public boolean addRow(String fn, String ln, String gen, Integer age, String add) {

        synchronized(this.db) {
            ContentValues newFriend = new ContentValues();
            newFriend.put("friend_fname", fn);
            newFriend.put("friend_lname", ln);
            newFriend.put("gender", gen);
            newFriend.put("age", age);
            newFriend.put("address", add);

            try {
                db.insertOrThrow(DB_TABLE, null, newFriend);
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
        ArrayList<String> friendRows = new ArrayList<String>();
        String[] columns = new String[] {"friend_id","friend_fname", "friend_lname","gender","age","address"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            friendRows.add(Integer.toString(cursor.getInt(0)) + ", " +cursor.getString(1) + ", "+ cursor.getString(2) +
                    ", "+ cursor.getString(3) + ", " + Integer.toString(cursor.getInt(4)) + ", " + cursor.getString(5)) ;
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return friendRows;
    }


    public ArrayList<String> retFFriend() {
        ArrayList<String> friendRows = new ArrayList<String>();
        String[] columns = new String[] {"friend_fname", "friend_lname","friend_id"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            friendRows.add(cursor.getString(0) + ","+cursor.getString(1)+ ","+Integer.toString(cursor.getInt(2)));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return friendRows;

    }

    public ArrayList<String> retfull(int id) {
        ArrayList<String> friendRows = new ArrayList<String>();
        String[] columns = new String[] {"friend_id","friend_fname", "friend_lname","gender","age","address"};
        Cursor cursor = db.query(DB_TABLE, columns, "friend_id = ?",new String[]{Integer.toString(id)}, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            friendRows.add(Integer.toString(cursor.getInt(0))+","+cursor.getString(1)
                    + ","+cursor.getString(2)+ ","+cursor.getString(3)+ ","+Integer.toString(cursor.getInt(4))+ ","+cursor.getString(5));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return friendRows;

    }

    public boolean deleteRow(Integer i){
        synchronized (this.db){
            db.delete(DB_TABLE, "friend_id=?",new String[]{Integer.toString(i)});
        }
        return true;
    }

    public  boolean update(Integer id, String fn, String ln, String gen, Integer age, String add){

        synchronized (this.db){
            ContentValues updateFriend = new ContentValues();
            updateFriend.put("friend_fname", fn);
            updateFriend.put("friend_lname", ln);
            updateFriend.put("gender", gen);
            updateFriend.put("age", age);
            updateFriend.put("address", add);
            try {
                db.update("FriendInfo", updateFriend,"friend_id = ?",new String[]{Integer.toString(id)});
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
