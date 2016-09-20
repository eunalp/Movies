package com.example.eren.movies;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Eren on 20.09.2016.
 */
public class Movie implements Parcelable {
    final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private String  title;
    private int     id;
    private double  voteAverage;
    private Date    releasDate;

    public Movie(Parcel in) {
        this.title        = in.readString();
        this.id           = in.readInt();
        this.voteAverage  = in.readDouble();
        try {
            this.releasDate   = dateFormat.parse(in.readString());
        }
        catch (ParseException ex) {
            Log.e("Movie", "Error while parsing release date. " + ex);
        }
    }

    public Movie(String title, int id, double voteAverage, Date releaseDate) {
        this.title       = title;
        this.id          = id;
        this.voteAverage = voteAverage;
        this.releasDate  = releaseDate;
    }

    public Movie(JSONObject jsonMovie) throws JSONException, ParseException {
        this.title       = jsonMovie.getString("title");
        this.id          = jsonMovie.getInt("id");
        this.voteAverage = jsonMovie.getDouble("vote_average");
        this.releasDate  = dateFormat.parse(jsonMovie.getString("release_date"));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.id);
        dest.writeDouble(this.voteAverage);
        dest.writeString(dateFormat.format(this.releasDate));
    }

    public final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

    public static Movie createFromJSOnObject(JSONObject obj) throws JSONException, ParseException {
        return new Movie(obj);
    }

    public static Movie[] createFromJSONArray(JSONArray array) throws JSONException, ParseException {
        Movie   movie       = null;
        Movie[] returnArray = new Movie[array.length()];

        for (int i = 0; i < array.length(); i++) {
            movie          = createFromJSOnObject(array.getJSONObject(i));
            returnArray[i] = movie;
        }
        return returnArray;
    }
}
