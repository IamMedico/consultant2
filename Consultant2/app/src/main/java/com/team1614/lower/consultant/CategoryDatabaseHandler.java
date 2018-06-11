package com.team1614.lower.consultant;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CategoryDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    String DB_PATH = "/data/data/com.team1614.lower.consultant/databases/";
    private static final String DATABASE_NAME = "Consultant.db";

    // Contacts table name
    private static final String TABLE_CONTACTS = "BarTharYay";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_RATE = "rate";
    private static final String KEY_FAVOURITE = "favourite";


    public CategoryDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_QUESTION + " TEXT," + KEY_ANSWER + " TEXT,"
                + KEY_RATE + " REAL," + KEY_FAVOURITE + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }


    //Get list with Table name
    public List<QueAns> getAllContact(String tablename) {
        List<QueAns> contactList = new ArrayList<QueAns>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + tablename;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.i("DATA : ", "" + cursor.getString(1));
                QueAns contact = new QueAns();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setQue(cursor.getString(1));
                contact.setAns(cursor.getString(2));
                contact.setRate(Double.parseDouble(cursor.getString(3)));
                contact.setFavourate(cursor.getString(4));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        cursor.close();
        // return contact list
        return contactList;
    }


    //Get All table of list in Search Activity
    public List<QueAns> getAll(String query) {
        List<QueAns> contactList = new ArrayList<QueAns>();
        // Select All Query
        String selectQuery = "SELECT * FROM (SELECT * FROM BarTharYay UNION ALL SELECT * FROM Beauty UNION ALL SELECT * FROM Business UNION ALL SELECT * FROM Education" +
                " UNION ALL SELECT * FROM Health UNION ALL SELECT * FROM Love UNION ALL SELECT * FROM Other UNION ALL SELECT * FROM Sport" +
                " UNION ALL SELECT * FROM Technology) tbl WHERE " + KEY_QUESTION + " LIKE \'" + query + "%\'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.i("DATA : ", "" + cursor.getString(1));
                QueAns contact = new QueAns();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setQue(cursor.getString(1));
                contact.setAns(cursor.getString(2));
                contact.setRate(Double.parseDouble(cursor.getString(3)));
                contact.setFavourate(cursor.getString(4));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        cursor.close();
        // return contact list
        return contactList;
    }

    //Set Or unset  Favourite with Two Argumunt
    public int updateFav(QueAns food, String tb) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update " + tb + " set " + KEY_FAVOURITE + "='" + food.getFavourate() + "' where " + KEY_ID + "=" + food.getId());
        return 1;
    }


    //Set Or unset  Favourite with One Argumunt
    public int updateFav(Album food) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update " + TABLE_CONTACTS + " set " + KEY_FAVOURITE + "='" + food.getFavourate() + "' where " + KEY_ID + "=" + food.getId());
        return 1;
    }


    //Get Favourite list
    public List<Album> getFav(String favourite) {
        List<Album> foodList = new ArrayList<Album>();
        String selectQuery = "SELECT * FROM (SELECT * FROM BarTharYay UNION ALL SELECT * FROM Beauty UNION ALL SELECT * FROM Business UNION ALL SELECT * FROM Education" +
                " UNION ALL SELECT * FROM Health UNION ALL SELECT * FROM Love UNION ALL SELECT * FROM Other UNION ALL SELECT * FROM Sport" +
                " UNION ALL SELECT * FROM Technology) tbl WHERE " + KEY_FAVOURITE + " = '" + favourite + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            do {
                Log.i("DATA : ", "" + cursor.getString(1));
                Album contact = new Album();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setQuestion(cursor.getString(1));
                contact.setAnswer(cursor.getString(2));
                contact.setRate(Double.parseDouble(cursor.getString(3)));
                contact.setFavourate(cursor.getString(4));

                // Adding contact to list
                foodList.add(contact);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return foodList;
    }


    // Getting All Contact
    public List<Album> getAllMainContact() {
        List<Album> contactList = new ArrayList<Album>();
        // Select All Query
        String selectQuery = "SELECT * FROM (SELECT * FROM Love UNION ALL SELECT * FROM Beauty UNION ALL SELECT * FROM Business UNION ALL SELECT * FROM Education" +
                " UNION ALL SELECT * FROM Health UNION ALL SELECT * FROM BarTharYay UNION ALL SELECT * FROM Other UNION ALL SELECT * FROM Sport" +
                " UNION ALL SELECT * FROM Technology) tbl ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.i("CategoryHandler", " Cursor count " + cursor.getCount());

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.i("DATA : ", "" + cursor.getString(1));

                Album contact = new Album();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setQuestion(cursor.getString(1));
                contact.setAnswer(cursor.getString(2));
                contact.setRate(Double.parseDouble(cursor.getString(3)));
                contact.setFavourate(cursor.getString(4));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        cursor.close();
        // return contact list
        return contactList;
    }
}
