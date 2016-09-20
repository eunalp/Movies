package com.example.eren.movies;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        getMovies();
    }

    private void getMovies() {
        MovieQueryTask task = new MovieQueryTask();
        task.execute(getPrefferedOrder());
    }

    private MovieSortOrder getPrefferedOrder() {
        MovieSortOrder    order = MovieSortOrder.NONE;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //TODO
        order = MovieSortOrder.POPULAR;
        return order;
    }
}
