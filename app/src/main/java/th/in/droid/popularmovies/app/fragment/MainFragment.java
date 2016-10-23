package th.in.droid.popularmovies.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import th.in.droid.popularmovies.app.R;
import th.in.droid.popularmovies.app.adapter.MovieAdapter;
import th.in.droid.popularmovies.app.interceptor.ApiKeyInterceptor;
import th.in.droid.popularmovies.app.model.MovieData;
import th.in.droid.popularmovies.app.service.MovieService;


public class MainFragment extends Fragment {

    private static final String LOG_TAG = MainFragment.class.getSimpleName();

    private MovieService movieService;

    private GridView moviesGrid;

    private MovieAdapter movieAdapter;

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        movieAdapter = new MovieAdapter(null);

        super.onCreate(savedInstanceState);

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
        movieService = retrofit.create(MovieService.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchMovies();
    }

    private void fetchMovies() {
        movieService.getTopRatedMovies("top_rated")
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
                        movieAdapter.setMovieData(movieData);
                        movieAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        moviesGrid = (GridView) rootView.findViewById(R.id.movies_grid);
        moviesGrid.setAdapter(movieAdapter);
        fetchMovies();
        return rootView;
    }
}
