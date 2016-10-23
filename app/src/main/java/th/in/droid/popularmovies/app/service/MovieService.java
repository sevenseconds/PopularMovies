package th.in.droid.popularmovies.app.service;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import th.in.droid.popularmovies.app.model.MovieData;

public interface MovieService {

    String POSTER_PATH = "http://image.tmdb.org/t/p/w185";

    @GET("movie/{displayType}")
    Observable<MovieData> getTopRatedMovies(@Path("displayType") String displayType);
}
