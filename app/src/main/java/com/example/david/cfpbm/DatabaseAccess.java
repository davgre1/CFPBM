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
    public DatabaseAccess(Context context) {
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
//    public ArrayList<HashMap<String, String>> getQuotes() {
//        //List<String> list = new ArrayList<>();
//        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//        HashMap<String, String> person;
//        Cursor cursor = database.rawQuery("SELECT google_email, google_name, income, transportation, " +
//                "diet, utilities, health, extras FROM user", null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                person = new HashMap<String, String>();
//                person.put("google_email", cursor.getString(0));
//                person.put("google_name", cursor.getString(1));
//                person.put("income", cursor.getString(2));
//                person.put("transportation", cursor.getString(3));
//                person.put("diet", cursor.getString(4));
//                person.put("utilities", cursor.getString(5));
//                person.put("health", cursor.getString(6));
//                person.put("extras", cursor.getString(7));
//                list.add(person);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        return list;
//    }

//    public List<Person> getQuotes() {
//        List<Person> list = new ArrayList<>();
//        Cursor cursor = database.rawQuery("SELECT google_email, googel_name FROM user", null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            Person person = new Person();
//            person.setEmail(cursor.getString(0));
//            person.setName(cursor.getString(1));
//            list.add(person);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return list;
//    }


    public ArrayList<HashMap<String, String>>  getQuotes() {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String selectQuery =  "SELECT  *"  + " FROM user";

        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        Cursor cursor = database.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> person = new HashMap<String, String>();
                person.put("google_email", cursor.getString(cursor.getColumnIndex(Person.KEY_EMAIL)));
                person.put("google_name", cursor.getString(cursor.getColumnIndex(Person.KEY_NAME)));
                list.add(person);

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return list;
    }

    public Person getByEmail(String gmail){
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String selectQuery =  "SELECT  *" + " FROM user"
                + " WHERE " + Person.KEY_EMAIL + "=" + gmail;

        Person person = new Person();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                person.name = cursor.getString(cursor.getColumnIndex(Person.KEY_NAME));
                person.gmail = cursor.getString(cursor.getColumnIndex(Person.KEY_EMAIL));
                person.income = cursor.getString(cursor.getColumnIndex(Person.KEY_INCOME));
                person.transportation = cursor.getString(cursor.getColumnIndex(Person.KEY_TRANS));
                person.diet = cursor.getString(cursor.getColumnIndex(Person.KEY_DIET));
                person.utilities = cursor.getString(cursor.getColumnIndex(Person.KEY_UTILITIES));
                person.health = cursor.getString(cursor.getColumnIndex(Person.KEY_HEALTH));
                person.extras = cursor.getString(cursor.getColumnIndex(Person.KEY_EXTRAS));
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return person;
    }
}
