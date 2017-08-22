package com.alicefriend.movie.movie_app.ui.detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import com.alicefriend.movie.movie_app.db.MovieDao;
import com.alicefriend.movie.movie_app.domain.Movie;
import com.alicefriend.movie.movie_app.domain.Review;
import com.alicefriend.movie.movie_app.domain.Trailer;

import java.util.List;

/**
 * Created by choi on 2017. 8. 5..
 */

public class DetailViewModel extends AndroidViewModel {

    private static final String TAG = DetailViewModel.class.getSimpleName();

    private DetailRepository repository;

    private Movie movie;
    private MutableLiveData<List<Review>> reviewsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Trailer>> trailersLiveData = new MutableLiveData<>();
    private ObservableField<Boolean> loadTrailersFailed = new ObservableField<>(false);
    private ObservableField<Boolean> loadReviewsFailed = new ObservableField<>(false);
    private ObservableField<Boolean> isFavorite = new ObservableField<>(false);

    public DetailViewModel(Application application, Movie movie) {
        super(application);
        this.movie = movie;
        repository = new DetailRepository(movie, MovieDao.getInstance(application));
        repository.reviews(reviewsLiveData, loadReviewsFailed);
        repository.trailers(trailersLiveData, loadTrailersFailed);
        isFavorite = MovieDao.getInstance(application).isFavorite(movie);
    }

    public void loadData() {
        repository.reviews(reviewsLiveData, loadReviewsFailed);
        repository.trailers(trailersLiveData, loadTrailersFailed);
    }

    public void addFavorite() {
        repository.addFavorite(movie);
        isFavorite.set(true);
    }

    public void deleteFavorite() {
        repository.deleteFavorite(movie);
        isFavorite.set(false);
    }

    public MutableLiveData<List<Review>> getReviewsLiveData() {
        return reviewsLiveData;
    }

    public MutableLiveData<List<Trailer>> getTrailersLiveData() {
        return trailersLiveData;
    }

    public ObservableField<Boolean> getLoadTrailersFailed() {
        return loadTrailersFailed;
    }

    public ObservableField<Boolean> getLoadReviewsFailed() {
        return loadReviewsFailed;
    }

    public ObservableField<Boolean> getIsFavorite() {
        return isFavorite;
    }
}
