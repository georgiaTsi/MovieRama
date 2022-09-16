package com.example.movierama.UI;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    public TextView author;
    public TextView review;

    public ReviewViewHolder(View view, TextView author, TextView review) {
        super(view);

        this.author = author;
        this.review = review;
    }
}
