package th.in.droid.popularmovies.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import th.in.droid.popularmovies.app.R;
import th.in.droid.popularmovies.app.model.Movie;
import th.in.droid.popularmovies.app.model.MovieData;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context mContext;
    private MovieData mMovieData;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_poster, parent, false);
        ViewHolder viewHolder = new ViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie currentItem = mMovieData.getMovies().get(position);
        Picasso.with(mContext)
                .load(currentItem.getPosterPath())
                .fit()
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        if (mMovieData == null) {
            return 0;
        }
        if (mMovieData.getMovies() == null && mMovieData.getMovies().size() == 0) {
            return 0;
        }
        return mMovieData.getMovies().size();
    }

    public void setMovieData(MovieData movieData) {
        this.mMovieData = movieData;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;

        public ViewHolder(View view) {
            super(view);
            poster = (ImageView) view.findViewById(R.id.poster);
        }
    }
}
