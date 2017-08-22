package com.alicefriend.movie.movie_app.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.ContentValues;
import android.content.Context;
import android.databinding.ObservableField;
import android.net.Uri;
import com.alicefriend.movie.movie_app.db.MovieDbContract.MovieEntry;

import com.alicefriend.movie.movie_app.domain.Movie;

import java.util.List;

/**
 * Created by choi on 2017. 8. 21..
 */

public class MovieDao {

    private static MovieDao instance;
    private MutableLiveData<List<Movie>> favoriteLiveData;
    private MutableLiveData<List<Movie>> popularLiveData;
    private MutableLiveData<List<Movie>> topRatedLiveData;

    private MovieQueryHandler movieQueryHandler;

    private MovieDao(Context context) {
        favoriteLiveData = new MutableLiveData<>();
        popularLiveData = new MutableLiveData<>();
        topRatedLiveData = new MutableLiveData<>();
        movieQueryHandler = new MovieQueryHandler(context.getContentResolver());
    }

    public static MovieDao getInstance(Context context) {
        if(instance == null)
            instance = new MovieDao(context);
        return instance;
    }

    private ContentValues createMovieContentValue(Movie movie) {
        ContentValues cv = new ContentValues();
        cv.put(MovieEntry.COLUMN_MOVIE_ID, movie.getMovieId());
        cv.put(MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        cv.put(MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        cv.put(MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        cv.put(MovieEntry.COLUMN_TITLE, movie.getTitle());
        cv.put(MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        return cv;
    }

    public void addFavorite(Movie movie){
        ContentValues cv = createMovieContentValue(movie);
        cv.put(MovieEntry.COLUMN_TAG, "favorite");
        movieQueryHandler.startInsert(1, null, MovieEntry.CONTENT_URI, cv);
        getFavoriteMovies();
    }

    public void addPopular(Movie movie) {
        ContentValues cv = createMovieContentValue(movie);
        cv.put(MovieEntry.COLUMN_TAG, "popular");
        movieQueryHandler.startInsert(1, null, MovieEntry.CONTENT_URI, cv);
    }

    public void addTopRated(Movie movie) {
        ContentValues cv = createMovieContentValue(movie);
        cv.put(MovieEntry.COLUMN_TAG, "topRated");
        movieQueryHandler.startInsert(1, null, MovieEntry.CONTENT_URI, cv);
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        movieQueryHandler.startQuery(1, favoriteLiveData,
                MovieEntry.CONTENT_URI,
                null,
                MovieEntry.COLUMN_TAG+ "=?",
                new String[]{"favorite"},
                null);
        return favoriteLiveData;
    }

    public LiveData<List<Movie>> getPopularMovies() {
        movieQueryHandler.startQuery(1, popularLiveData,
                MovieEntry.CONTENT_URI,
                null,
                MovieEntry.COLUMN_TAG+ "=?",
                new String[]{"popular"},
                null);
        return popularLiveData;
    }

    public LiveData<List<Movie>> getTopRatedMovies() {
        movieQueryHandler.startQuery(1, topRatedLiveData,
                MovieEntry.CONTENT_URI,
                null,
                MovieEntry.COLUMN_TAG+ "=?",
                new String[]{"topRated"},
                null);
        return topRatedLiveData;
    }

    public void deleteFavorite(Movie movie) {
        Uri baseUri = MovieEntry.CONTENT_URI;
        Uri uri = baseUri.buildUpon().appendPath(movie.getMovieId()).build();
        movieQueryHandler.startDelete(1, null, uri, null, null);
        getFavoriteMovies();
    }

    public ObservableField<Boolean> isFavorite(Movie movie) {
        ObservableField isFavorite = new ObservableField(false);
        movieQueryHandler.startQuery(1,
                isFavorite,
                MovieEntry.CONTENT_URI,
                null,
                MovieEntry.COLUMN_MOVIE_ID + "=? and " + MovieEntry.COLUMN_TAG + "=?",
                new String[]{movie.getMovieId(), "favorite"},
                null);
        return isFavorite;
    }
}
