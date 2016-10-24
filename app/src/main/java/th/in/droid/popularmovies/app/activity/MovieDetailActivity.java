package th.in.droid.popularmovies.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import th.in.droid.popularmovies.app.R;
import th.in.droid.popularmovies.app.fragment.MovieDetailFragment;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MovieDetailFragment())
                    .commit();
        }
    }
}
