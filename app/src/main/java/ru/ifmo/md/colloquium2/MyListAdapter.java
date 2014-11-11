package ru.ifmo.md.colloquium2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by sultan on 11.11.14.
 */
public class MyListAdapter extends BaseAdapter {

    private SQLiteDatabase database;
    private Cursor cursor;
    private MyDbHelper dbHelper;
    private Context mContext;
    private LayoutInflater mInflater;


    public MyListAdapter(Context context) {
        super();
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        init();
    }

    private Cursor getAllMans() {
        return  database.query(MyDbHelper.TABLE_MAN, MyDbHelper.COLUMNS_MAN, null, null, null, null, null);
    }

    private void init() {
        dbHelper = new MyDbHelper(mContext);
        database = dbHelper.getWritableDatabase();

        cursor = getAllMans();
    }

    private int getCountVotes() {
        Cursor cursor1 =  database.rawQuery("SELECT SUM( " + MyDbHelper.COLUMN_MAN_VOTES + " ) FROM " + MyDbHelper.TABLE_MAN, null);
        cursor1.moveToFirst();
        return  cursor1.getInt(0);
    }

    private void refresh() {
        cursor = getAllMans();
        notifyDataSetChanged();
    }

    public void addMan(String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDbHelper.COLUMN_MAN_NAME, name);
        contentValues.put(MyDbHelper.COLUMN_MAN_VOTES, 0);
        database.insert(MyDbHelper.TABLE_MAN, null, contentValues);
        refresh();
    }

    public void addVoteToMan(int i) {
        VoteMan man = (VoteMan) getItem(i);
        ContentValues cv = new ContentValues();
        cv.put(MyDbHelper.COLUMN_MAN_NAME, man.getName());
        cv.put(MyDbHelper.COLUMN_MAN_VOTES, man.getVotes() + 1);
        database.update(MyDbHelper.TABLE_MAN, cv, MyDbHelper.COLUMN_MAN_ID + " = ?",
                new String[] {String.valueOf(man.getId())});
        refresh();
    }

    public void clearDatabase() {
        database.execSQL("DROP TABLE IF EXISTS " + MyDbHelper.TABLE_MAN);
        database.execSQL(MyDbHelper.CREATE_TABLE_MAN);
        refresh();
    }

    public void onDestroy() {
        dbHelper.close();
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int i) {
        cursor.moveToPosition(i);
        VoteMan man = new VoteMan();
        man.setId(cursor.getLong(0));
        man.setName(cursor.getString(1));
        man.setVotes(cursor.getInt(2));
        return man;
    }

    @Override
    public long getItemId(int i) {
        VoteMan man = (VoteMan) getItem(i);
        return man.getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mInflater.inflate(R.layout.man_row, viewGroup, false);
        }

        VoteMan man = (VoteMan) getItem(i);
        TextView text = (TextView) view.findViewById(R.id.nameMan);
        int countVotes = getCountVotes();
        text.setText(man.getName() + " ( " + countVotes + ")("  + (man.getVotes()/(double)(countVotes == 0 ? 1 : countVotes))*100.0 + "%) ");
        return view;
    }
}
