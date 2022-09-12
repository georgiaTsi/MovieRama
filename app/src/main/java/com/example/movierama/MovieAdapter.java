package com.example.movierama;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movierama.Models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    List<Movie> dataSet = new ArrayList<>();

    Activity activity;

    public MovieAdapter(Activity activity){
        this.activity = activity;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_movie1, parent, false);

        ImageView posterImageView = view.findViewById(R.id.imageview_poster);
        TextView titleTextView = view.findViewById(R.id.textview_title);
        TextView releaseDateTextView = view.findViewById(R.id.textview_releasedate);
        TextView ratingTextView = view.findViewById(R.id.textview_rating);
        ImageButton favoriteButton = view.findViewById(R.id.imagebutton_favorite);

        return new MovieViewHolder(view, posterImageView, titleTextView, releaseDateTextView, ratingTextView, favoriteButton);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, int position) {

        viewHolder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(activity.getBaseContext(), DetailedActivity.class);
//                intent.putExtra("place", dataSet.get(position+1).place);
//                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(dataSet == null || dataSet.size() == 0)
            return 0;

        return dataSet.size();
    }

    public void updateAdapter(List<Movie> newList){
        dataSet.clear();
        dataSet.addAll(newList);

        notifyDataSetChanged();
    }
}
