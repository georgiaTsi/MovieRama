package com.example.movierama.UI;

import android.media.Rating;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MovieViewHolder extends RecyclerView.ViewHolder {
    public ImageView poster;
    public TextView title;
    public TextView releaseDate;
    public RatingBar rating;
    public ImageButton favorite;

    public MovieViewHolder(View view, ImageView poster, TextView title, TextView releaseDate, RatingBar rating, ImageButton favorite) {
        super(view);

        this.poster = poster;
        this.releaseDate = releaseDate;
        this.title = title;
        this.rating = rating;
        this.favorite = favorite;
    }
}
