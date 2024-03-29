package com.example.movierama.UI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.movierama.Helpers.DownloadImageTask;
import com.example.movierama.Helpers.MovieFavoriteHelper;
import com.example.movierama.Models.Movie;
import com.example.movierama.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    List<Movie> dataSet = new ArrayList<>();

    Activity activity;

    MovieFavoriteHelper db;

    public MovieAdapter(Activity activity){
        this.activity = activity;

        db = new MovieFavoriteHelper(activity);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_movie1, parent, false);

        ImageView posterImageView = view.findViewById(R.id.imageview_poster);
        TextView titleTextView = view.findViewById(R.id.textview_title);
        TextView releaseDateTextView = view.findViewById(R.id.textview_releasedate);
        RatingBar ratingBar = view.findViewById(R.id.ratingbar);
        ImageButton favoriteButton = view.findViewById(R.id.imagebutton_favorite);

        return new MovieViewHolder(view, posterImageView, titleTextView, releaseDateTextView, ratingBar, favoriteButton);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, int position) {

        viewHolder.title.setText(dataSet.get(position).title);
        viewHolder.releaseDate.setText(dataSet.get(position).release_date);

        viewHolder.rating.setRating(dataSet.get(position).vote_average/2);

//        String url = "https://media.geeksforgeeks.org/wp-content/cdn-uploads/gfg_200x200-min.png";
//
//        new DownloadImageTask(viewHolder.poster).execute(url);
        int[] images = {R.drawable.poster_1, R.drawable.poster_2, R.drawable.poster_3};
        Random rand = new Random();
        viewHolder.poster.setImageResource(images[rand.nextInt(images.length)]);

        db.insertMovie(dataSet.get(position).id);

        final Boolean[] isFavorite = {db.getIsFavorite(dataSet.get(position).id)};

        if(isFavorite[0])
            viewHolder.favorite.setImageResource(R.drawable.icon_heart_full);
        else
            viewHolder.favorite.setImageResource(R.drawable.icon_heart);

        viewHolder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFavorite[0] = !isFavorite[0];

                db.updateMovie(dataSet.get(position).id, isFavorite[0]);

                if(isFavorite[0])
                    viewHolder.favorite.setImageResource(R.drawable.icon_heart_full);
                else
                    viewHolder.favorite.setImageResource(R.drawable.icon_heart);
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity.getBaseContext(), DetailedActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("movieId", dataSet.get(position).id);
                activity.startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(dataSet == null || dataSet.size() == 0)
            return 0;

        return dataSet.size();
    }

    public void addToAdapter(List<Movie> newList){
        dataSet.addAll(newList);

        notifyDataSetChanged();
    }

    public void updateAdapter(List<Movie> newList){
        dataSet.clear();

        dataSet.addAll(newList);

        notifyDataSetChanged();
    }
}
