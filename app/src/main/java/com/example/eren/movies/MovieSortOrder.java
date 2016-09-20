package com.example.eren.movies;

/**
 * Created by Eren on 20.09.2016.
 */
public enum  MovieSortOrder {
    NONE("NONE"),
    POPULAR("popularity.desc"),
    TOP_RATED("top_rated.desc");

    private String sortQuery;

    MovieSortOrder(String sortQuery) {
        this.sortQuery = sortQuery;
    }

    public String getSortOrder() {
        return this.sortQuery;
    }
}
