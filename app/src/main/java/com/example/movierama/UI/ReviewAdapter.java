package com.example.movierama.UI;

import android.app.Activity;
import android.content.Intent;
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
import com.example.movierama.Models.Review;
import com.example.movierama.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    List<Review> dataSet = new ArrayList<>();

    Activity activity;

    MovieFavoriteHelper db;

    public ReviewAdapter(Activity activity){
        this.activity = activity;

        db = new MovieFavoriteHelper(activity);
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_review, parent, false);

        TextView author = view.findViewById(R.id.tetxview_author);
        TextView review = view.findViewById(R.id.tetxview_review);

        return new ReviewViewHolder(view, author, review);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder viewHolder, int position) {

        viewHolder.author.setText(dataSet.get(position).author);
        viewHolder.review.setText(dataSet.get(position).content);
    }

    @Override
    public int getItemCount() {
        if(dataSet == null || dataSet.size() == 0)
            return 0;

        return dataSet.size();
    }

    public void addToAdapter(List<Review> newList){
        dataSet.addAll(newList);

        notifyDataSetChanged();
    }
}
