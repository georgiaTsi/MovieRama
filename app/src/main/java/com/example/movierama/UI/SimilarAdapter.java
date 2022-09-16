package com.example.movierama.UI;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.movierama.Helpers.MovieFavoriteHelper;
import com.example.movierama.Models.Review;
import com.example.movierama.R;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimilarAdapter extends RecyclerView.Adapter<SimilarViewHolder> {
    List<LinkedTreeMap> dataSet = new ArrayList<>();

    Activity activity;

    public SimilarAdapter(Activity activity){
        this.activity = activity;
    }

    @Override
    public SimilarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_similar, parent, false);

        ImageView poster = view.findViewById(R.id.imageview_poster);

        return new SimilarViewHolder(view, poster);
    }

    @Override
    public void onBindViewHolder(SimilarViewHolder viewHolder, int position) {
        int[] images = {R.drawable.poster_1, R.drawable.poster_2, R.drawable.poster_3};
        Random rand = new Random();
        viewHolder.poster.setImageResource(images[rand.nextInt(images.length)]);
    }

    @Override
    public int getItemCount() {
        if(dataSet == null || dataSet.size() == 0)
            return 0;

        return dataSet.size();
    }

    public void addToAdapter(List<LinkedTreeMap> newList){
        dataSet.addAll(newList);

        notifyDataSetChanged();
    }
}
