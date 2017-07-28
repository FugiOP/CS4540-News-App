package util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Fugi on 7/27/2017.
 */

public class DBHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "news.db";

    public DBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Added image column tp create statement
        String query = "CREATE TABLE "+ Contract.TABLE_ARTICLES.TABLE_NAME +" ("+
                Contract.TABLE_ARTICLES.COLUMN_TITLE+" TEXT NOT NULL, "+
                Contract.TABLE_ARTICLES.COLUMN_AUTHOR+" TEXT, "+
                Contract.TABLE_ARTICLES.COLUMN_DATE+" TEXT, "+
                Contract.TABLE_ARTICLES.COLUMN_DESC+" TEXT, "+
                Contract.TABLE_ARTICLES.COLUMN_IMAGE+" TEXT, "+
                Contract.TABLE_ARTICLES.COLUMN_URL+" TEXT"+
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //add later
    }
}
