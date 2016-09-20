package com.example.eren.movies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Eren on 20.09.2016.
 */
public class MovieQueryTask extends AsyncTask<MovieSortOrder, Void, Movie[]> {
    private final String LOG_TAG             = MovieQueryTask.class.getSimpleName();
    private final String MOVIES_API_BASE_URL = "http://api.themoviedb.org/3/discover/movie";
    private final String SORT_PARAM          = "sort_by";
    private final String API_KEY             = "api_key";

    private Movie[] movies = null;

    public MovieQueryTask() {

    }

    @Override
    protected Movie[] doInBackground(MovieSortOrder... params) {

        Movie[] movies = null;

        if (params.length == 0) {
            return null;
        }

        Uri builtUri = Uri.parse(MOVIES_API_BASE_URL).buildUpon()
                .appendQueryParameter(SORT_PARAM, params[0].getSortOrder())
                .appendQueryParameter(API_KEY, BuildConfig.MOVIES_API_KEY)
                .build();

        HttpURLConnection urlConnection = null;
        BufferedReader reader           = null;


        String jsonQueryResult          = null;

        try {

            URL url = new URL(builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            jsonQueryResult = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }


        try {
            JSONArray array = getMoviesJSONArray(jsonQueryResult);
            movies = Movie.createFromJSONArray(array);
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Error while parsing movies from query jason string." + ex);
        }

        return movies;
    }

    private JSONArray getMoviesJSONArray(String jsonqueryResult) throws JSONException {
        JSONObject jObject = new JSONObject(jsonqueryResult);
        return jObject.getJSONArray("results");
    }
}
