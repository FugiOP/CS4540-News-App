package com.example.fugi.newsapp;

import android.content.pm.PackageManager;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import models.NewsItem;
import util.Contract;
import util.DBHelper;
import util.DatabaseUtils;
import util.JobDispatcher;
import util.NetworkUtils;
import util.NewsAdapter;
import util.OpenNewsJsonUtils;

public class MainActivity extends AppCompatActivity implements NewsAdapter.ItemClickListener,LoaderManager.LoaderCallbacks<Void> {

    ProgressBar progressBar;
    private RecyclerView rv;
    NewsAdapter adapter;
    Cursor cursor;
    SQLiteDatabase db;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rv = (RecyclerView)findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));

        //bolean used to check if the app is instaleld or not
        boolean isAppInstalled = checkAppInstalled("com.example.fugi.newsapp");
        //if it hasnt been installed, updateDB gets called and popultes the table with data from json file
        if(!(isAppInstalled)){
            updateDB(this);
        }
        //setting the refresh to be every 1 minutes
        JobDispatcher.refresh(this);

        //binding news artlicles to recycler view
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        adapter = new NewsAdapter(cursor,this);
        rv.setAdapter(adapter);

        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(1,null,MainActivity.this).forceLoad();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader(this) {
            @Override
            //when loading starts, set progress bar to visible
            protected void onStartLoading() {
                super.onStartLoading();
                progressBar.setVisibility(View.VISIBLE);
            }

            //populate/update db in background
            @Override
            public Object loadInBackground() {
                updateDB(MainActivity.this);
                return null;
            }
        };
    }

    @Override
    //set progress bar to gone when loading finishes and bind data to recyclerview
    public void onLoadFinished(Loader<Void> loader, Void data) {
        progressBar.setVisibility(View.GONE);
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        adapter = new NewsAdapter(cursor,this);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }

    @Override
    public void onItemClick(Cursor cursor, int clickedItemIndex) {
        //moved onPostExecute onItemClick code from previous hw to here
        cursor.moveToPosition(clickedItemIndex);
        Uri uri = Uri.parse(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_URL)));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public static void updateDB(Context context){
        ArrayList<NewsItem> result = null;
        URL url = NetworkUtils.buildUrl();

        SQLiteDatabase db = new DBHelper(context).getReadableDatabase();

        try{
            //Removing all data from table to populate with newer data
            DatabaseUtils.removeAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            result = OpenNewsJsonUtils.getSimpleNewsStringsFromJson(json);

            //call addAll method that will insert all articles retrieved
            DatabaseUtils.addAll(db,result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //method that check if the app has been installed or not by checking the package info with the package name
    public boolean checkAppInstalled(String uri){
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }
}