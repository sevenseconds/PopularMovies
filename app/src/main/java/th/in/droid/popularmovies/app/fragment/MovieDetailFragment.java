package th.in.droid.popularmovies.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import th.in.droid.popularmovies.app.R;
import th.in.droid.popularmovies.app.model.Movie;

public class MovieDetailFragment extends Fragment {

    private Movie mMovie;

    private TextView mTitle;
    private ImageView mPoster;
    private TextView mReleaseDate;
    private TextView mDuration;
    private TextView mRating;
    private TextView mOverview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        initComponents(rootView);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Movie.MOVIE)) {
            mMovie = intent.getParcelableExtra(Movie.MOVIE);
            getActivity().setTitle(mMovie.getTitle());
            mTitle.setText(mMovie.getTitle());
            mReleaseDate.setText(mMovie.getReleaseDate().substring(0, 4));
            mDuration.setText("Duration");
            if (mMovie.getVoteAverage() == null) {
                mRating.setText("N/A");
            } else {
                mRating.setText(String.format("%.2f / 10", mMovie.getVoteAverage()));
            }
            mOverview.setText(mMovie.getOverview());
            Glide.with(this)
                    .load(mMovie.getPosterPath())
                    .placeholder(R.mipmap.ic_launcher)
                    .fitCenter()
                    .into(mPoster);
        }
        return rootView;
    }

    private void initComponents(View parent) {
        mTitle = (TextView) parent.findViewById(R.id.movie_title);
        mPoster = (ImageView) parent.findViewById(R.id.movie_poster);
        mReleaseDate = (TextView) parent.findViewById(R.id.movie_release_date);
        mDuration = (TextView) parent.findViewById(R.id.movie_duration);
        mRating = (TextView) parent.findViewById(R.id.movie_rating);
        mOverview = (TextView) parent.findViewById(R.id.movie_overview);
    }
}
