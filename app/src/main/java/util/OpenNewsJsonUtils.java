package util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import models.NewsItem;

/**
 * Created by Fugi on 6/22/2017.
 */

public class OpenNewsJsonUtils {


    public static ArrayList<NewsItem> getSimpleNewsStringsFromJson(String newsJsonStr) throws JSONException {
        JSONObject newsJson = new JSONObject(newsJsonStr);

        JSONArray newsArray = newsJson.getJSONArray("articles");
        ArrayList<NewsItem> newsArrayList = new ArrayList<>();

        for(int i = 0; i<newsArray.length(); i++){

            JSONObject article = newsArray.getJSONObject(i);

            String title = article.getString("title");
            String desc = article.getString("description");
            String author = article.getString("author");
            String url = article.getString("url");
            String date = article.getString("publishedAt");

            NewsItem newItem = new NewsItem(title, author, url, desc, date);
            newsArrayList.add(newItem);
        }

        return newsArrayList;
    }
}
