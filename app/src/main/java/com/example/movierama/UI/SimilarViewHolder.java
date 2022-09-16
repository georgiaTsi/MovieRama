package com.example.movierama.UI;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

public class SimilarViewHolder extends RecyclerView.ViewHolder {

    public ImageView poster;

    public SimilarViewHolder(View view, ImageView poster){
        super(view);

        this.poster = poster;
    }
}
