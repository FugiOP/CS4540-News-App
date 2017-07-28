package util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import models.NewsItem;

import static util.Contract.TABLE_ARTICLES.COLUMN_TITLE;
import static util.Contract.TABLE_ARTICLES.TABLE_NAME;

/**
 * Created by Fugi on 7/27/2017.
 */

public class DatabaseUtils {
    public static Cursor getAll(SQLiteDatabase db){
        Cursor cursor = db.query(
            TABLE_NAME,null,null,null,null,null,COLUMN_TITLE + " DESC"
        );
        return cursor;
    }

    public static void addNews(SQLiteDatabase db, String title, String author, String date, String desc, String image, String url) {
        //Added image data to content values
        ContentValues cv = new ContentValues();
        cv.put(Contract.TABLE_ARTICLES.COLUMN_TITLE, title);
        cv.put(Contract.TABLE_ARTICLES.COLUMN_AUTHOR, author);
        cv.put(Contract.TABLE_ARTICLES.COLUMN_DATE, date);
        cv.put(Contract.TABLE_ARTICLES.COLUMN_DESC, desc);
        cv.put(Contract.TABLE_ARTICLES.COLUMN_IMAGE, image);
        cv.put(Contract.TABLE_ARTICLES.COLUMN_URL, url);

        db.insert(Contract.TABLE_ARTICLES.TABLE_NAME, null, cv);
    }

    //method that calls addNews for each news articles in arraylist of news
    public static void addAll(SQLiteDatabase db, ArrayList<NewsItem> list){
        db.beginTransaction();
        try {
            for (int i = 0; i < list.size(); i++) {
                addNews(db, list.get(i).getTitle(), list.get(i).getAuthor(), list.get(i).getDate(), list.get(i).getDesc(), list.get(i).getImg(), list.get(i).getUrl());
            }
            db.setTransactionSuccessful();
        }finally{
            db.endTransaction();
            db.close();
        }
    }

    //method used to clean out the table to later populate with newer news results
    public static void removeAll(SQLiteDatabase db){
        db.delete(Contract.TABLE_ARTICLES.TABLE_NAME,null,null);
    }
}
