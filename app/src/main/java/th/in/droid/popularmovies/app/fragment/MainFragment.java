package th.in.droid.popularmovies.app.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import th.in.droid.popularmovies.app.R;
import th.in.droid.popularmovies.app.activity.MovieDetailActivity;
import th.in.droid.popularmovies.app.adapter.MovieAdapter;
import th.in.droid.popularmovies.app.interceptor.ApiKeyInterceptor;
import th.in.droid.popularmovies.app.model.Movie;
import th.in.droid.popularmovies.app.model.MovieData;
import th.in.droid.popularmovies.app.service.MovieService;


public class MainFragment extends Fragment {

    private static final String LOG_TAG = MainFragment.class.getSimpleName();

    private MovieService mMovieService;
    private RecyclerView mMoviesGrid;
    private MovieAdapter mMovieAdapter;
    private SharedPreferences mSharedPrefs;
    private String mDisplayType;

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMovieAdapter = new MovieAdapter();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        ApiKeyInterceptor apiKeyInterceptor = new ApiKeyInterceptor();

        httpClient.addInterceptor(apiKeyInterceptor);
        httpClient.addInterceptor(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        mMovieService = retrofit.create(MovieService.class);
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mDisplayType = getDisplayTypeValue();
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchMovies();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mMoviesGrid = (RecyclerView) rootView.findViewById(R.id.movies_grid);
        mMoviesGrid.setAdapter(mMovieAdapter);
        mMoviesGrid.setHasFixedSize(true);

        fetchMovies();

        mMovieAdapter.getMovieClickedPosition()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG_TAG, "Error click event: " + e.getMessage(), e);
                    }

                    @Override
                    public void onNext(Movie movie) {
                        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                        intent.putExtra(Movie.MOVIE, movie);
                        startActivity(intent);
                    }
                });

        return rootView;
    }

    private void fetchMovies() {
        if (mMovieAdapter.getItemCount() > 0 && mDisplayType.equals(getDisplayTypeValue())) {
            return;
        }

        mMovieService.getMoviesByDisplayType(getDisplayTypeValue())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG_TAG, "Connection error: " + e.getMessage(), e);
                    }

                    @Override
                    public void onNext(MovieData movieData) {
                        mMovieAdapter.setMovieData(movieData);
                        mMovieAdapter.notifyDataSetChanged();
                        mDisplayType = getDisplayTypeValue();
                    }
                });
    }

    private String getDisplayTypeValue() {
        return mSharedPrefs.getString(
                getString(R.string.pref_display_type_key),
                getString(R.string.pref_display_type_popular));
    }
}
