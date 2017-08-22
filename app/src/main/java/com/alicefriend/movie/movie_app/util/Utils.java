package com.alicefriend.movie.movie_app.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.alicefriend.movie.movie_app.R;
import com.alicefriend.movie.movie_app.domain.Movie;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by choi on 2017. 8. 10..
 */

public class Utils {
    public static void posterImageLoad(ImageView imageView, Movie movie) {
        final String baseUrl = "http://image.tmdb.org/t/p/w185/";
        Glide.with(imageView.getContext())
                .load(baseUrl + movie.getPosterPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .into(imageView);
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
