package ru.ifmo.md.colloquium2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by sultan on 11.11.14.
 */
public class MyDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mysuperdb.db";
    private static final Integer VERSION = 1;

    public static final String TABLE_MAN = "man";
    public static final String COLUMN_MAN_ID = "_id";
    public static final String COLUMN_MAN_NAME = "name";
    public static final String COLUMN_MAN_VOTES = "votes";
    public static final String[] COLUMNS_MAN = {
            COLUMN_MAN_ID, COLUMN_MAN_NAME, COLUMN_MAN_VOTES
    };

    public static final String CREATE_TABLE_MAN =
            "create table " + TABLE_MAN + " ("
                    + COLUMN_MAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_MAN_NAME + " TEXT NOT NULL, "
                    + COLUMN_MAN_VOTES + " INTEGER "
                    + " );";

    private SQLiteDatabase database;


    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_MAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MAN);
        onCreate(sqLiteDatabase);
    }

    public void addMan(VoteMan man) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MAN_NAME, man.getName());
        contentValues.put(COLUMN_MAN_VOTES, man.getVotes());
        man.setId(database.insert(TABLE_MAN, null, contentValues));
    }

    public void addVoteToMan(long id) {

    }

    public ArrayList<VoteMan> getAllMans() {
        ArrayList<VoteMan> mans = new ArrayList<VoteMan>();

        Cursor cursor = database.query(TABLE_MAN, COLUMNS_MAN, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            VoteMan man = new VoteMan();
            man.setId(cursor.getLong(0));
            man.setName(cursor.getString(1));
            man.setVotes(cursor.getInt(2));
            mans.add(man);
            cursor.moveToNext();
        }
        cursor.close();

        return mans;
    }

}
