package util;


import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by Fugi on 6/19/2017.
 */

public class NetworkUtils {
    private static final String base_url = "https://newsapi.org/v1/articles";
    private static final String query_source = "source";
    private static final String query_sort="sortBy";
    private static final String query_apikey="apiKey";

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(base_url).buildUpon()
                .appendQueryParameter(query_source,"the-next-web")
                .appendQueryParameter(query_sort,"latest")
                .appendQueryParameter(query_apikey,"INSERT APIKEY HERE")
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
