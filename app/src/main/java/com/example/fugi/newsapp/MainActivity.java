package com.example.fugi.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.net.URL;
import java.util.ArrayList;

import models.NewsItem;
import util.NetworkUtils;
import util.NewsAdapter;
import util.OpenNewsJsonUtils;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    private RecyclerView rv;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int item_id = item.getItemId();

        if(item_id == R.id.app_bar_search){
            getNews task = new getNews();
            task.execute();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rv = (RecyclerView)findViewById(R.id.recyclerView);

        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    private class getNews extends AsyncTask<Object, Object, ArrayList<NewsItem>> {

        @Override
        protected ArrayList<NewsItem> doInBackground(Object... params) {

            URL newsRequestUrl = NetworkUtils.buildUrl();
            ArrayList<NewsItem> result = null;

            try {

                String jsonResult = NetworkUtils.getResponseFromHttpUrl(newsRequestUrl);

                result = OpenNewsJsonUtils.getSimpleNewsStringsFromJson(jsonResult);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsItem> data) {
            super.onPostExecute(data);
            progressBar.setVisibility(GONE);
            if(data != null) {
                NewsAdapter adapter = new NewsAdapter(data, new NewsAdapter.ItemClickListener(){

                    @Override
                    public void onItemClick(int clickedItemIndex) {

                    }
                });

                rv.setAdapter(adapter);
            }
        }
    }
}