package th.in.droid.popularmovies.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import th.in.droid.popularmovies.app.R;
import th.in.droid.popularmovies.app.model.Movie;
import th.in.droid.popularmovies.app.model.MovieData;


public class MovieAdapter extends BaseAdapter {


    private MovieData movieData;

    public MovieAdapter(MovieData movieData) {
        this.movieData = movieData;
    }

    @Override
    public int getCount() {
        if (movieData == null) {
            return 0;
        }
        if (movieData.getMovies() == null || movieData.getMovies().size() == 0) {
            return 0;
        }
        return movieData.getMovies().size();
    }

    @Override
    public Object getItem(int position) {
        return movieData.getMovies().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder = new ViewHolder();
//        if (convertView == null) {
//            convertView = parent.findViewById(R.id.poster);
//            viewHolder.poster = (ImageView) convertView;
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//            convertView = viewHolder.poster;
//        }
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_poster, parent, false);
        ImageView posterView = (ImageView) convertView.findViewById(R.id.poster);
        Movie movie = (Movie) getItem(position);
        Picasso.with(parent.getContext())
                .load(movie.getPosterPath())
                .into(posterView);
        return convertView;
    }

    public void setMovieData(MovieData movieData) {
        this.movieData = movieData;
    }

    class ViewHolder {
        ImageView poster;
    }
}
