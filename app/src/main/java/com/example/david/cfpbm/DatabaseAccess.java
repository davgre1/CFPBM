package com.example.david.cfpbm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by David on 12/21/2016.
 */

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public ArrayList<HashMap<String, String>> getQuotes() {
        //List<String> list = new ArrayList<>();
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> person;
        Cursor cursor = database.rawQuery("SELECT google_email, google_name FROM user", null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
////            Person person = new Person();
////            person.setEmail(cursor.getString(0));
////            person.setName(cursor.getString(1));
//            list.add(cursor.getString(0));
//            cursor.moveToNext();
//        }

        if (cursor.moveToFirst()) {
            do {
                person = new HashMap<String, String>();
//                person.put("google_email", cursor.getString(cursor.getColumnIndex(Person.KEY_EMAIL)));
//                person.put("google_name", cursor.getString(cursor.getColumnIndex(Person.KEY_NAME)));
                person.put("google_email", cursor.getString(0));
                person.put("google_name", cursor.getString(1));
                list.add(person);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }
}
