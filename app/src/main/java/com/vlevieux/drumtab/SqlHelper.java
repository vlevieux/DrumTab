package com.vlevieux.drumtab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class SqlHelper extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "drumTabs";

    // Books table name
    private static final String TABLE_DRUMTABS = "drumtabs";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ARTIST = "artist";
    private static final String KEY_SONG = "song";
    private static final String KEY_XML = "xml";
    private static final String KEY_TAB = "tab";

    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //SQL statement to create drumtabs table
        String CREATE_DRUMTABS_TABLE = "CREATE TABLE drumtabs ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "artist TEXT, "+
                "song TEXT, "+
                "xml TEXT, "+
                "tab TEXT )";

        //create drumtrabs table
        db.execSQL((CREATE_DRUMTABS_TABLE));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed

        db.execSQL("DROP TABLE IF EXISTS drumtabs");

        // create fresh books table
        this.onCreate(db);
    }

    public void addDrumTab(DrumTab drumTab){
        Log.d("addDrumTab", drumTab.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ARTIST, drumTab.getArtistName());
        values.put(KEY_SONG, drumTab.getSongName());
        values.put(KEY_XML, drumTab.getXml());
        values.put(KEY_TAB, drumTab.getTab());

        // 3. insert
        db.insert(TABLE_DRUMTABS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/values

        // close dbase
        db.close();
    }

    // Get All Books
    public List<DrumTab> getAllDrumTab() {
        List<DrumTab> drumTabs = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_DRUMTABS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        DrumTab drumTab = null;

        if (cursor.moveToFirst()) {
            do {
                drumTab = new DrumTab();
                drumTab.setDrumTabId((cursor.getString(0)));
                drumTab.setArtistName(cursor.getString(1));
                drumTab.setSongName(cursor.getString(2));
                drumTab.setXml(cursor.getString(3));
                drumTab.setTab(cursor.getString(4));

                // Add book to books
                drumTabs.add(drumTab);
            } while (cursor.moveToNext());
        }

        Log.d("getAllDrumTabs()", drumTabs.toString());

        return drumTabs; // return books
    }

    public void deleteDrumTab(DrumTab drumTab) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_DRUMTABS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(drumTab.getDrumTabId()) });

        // 3. close
        db.close();

        Log.d("deleteDrumTab", drumTab.toString());

    }
}
