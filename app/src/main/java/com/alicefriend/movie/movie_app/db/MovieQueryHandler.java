package com.alicefriend.movie.movie_app.db;

import android.arch.lifecycle.MutableLiveData;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.databinding.ObservableField;

import com.alicefriend.movie.movie_app.domain.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by choi on 2017. 8. 22..
 */

public class MovieQueryHandler extends AsyncQueryHandler{

    public MovieQueryHandler(ContentResolver cr) {
        super(cr);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        List<Movie> movieList = new ArrayList<>();
        while (cursor.moveToNext()) {
            //debug
            String[] names = cursor.getColumnNames();
            int idx = cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_MOVIE_ID);
            cursor.getString(idx);

            String id = cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_MOVIE_ID));
            String title = cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_TITLE));
            String voteAverage = cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_VOTE_AVERAGE));
            String releaseDate = cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_RELEASE_DATE));
            String posterPath = cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_POSTER_PATH));
            String overview = cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_OVERVIEW));
            movieList.add(new Movie(id, posterPath, overview, releaseDate, title, voteAverage));
        }
        if(cookie instanceof MutableLiveData) {
            ((MutableLiveData<List<Movie>>)cookie).postValue(movieList);
        } else if (cookie instanceof ObservableField) {
            ((ObservableField<Boolean>)cookie).set(movieList.size() > 0);
        }


    }
}
