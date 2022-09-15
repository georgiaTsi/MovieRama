package com.example.movierama;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movierama.Models.Movie;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

        String url = "https://media.geeksforgeeks.org/wp-content/cdn-uploads/gfg_200x200-min.png";

        new DownloadImageTask(viewHolder.poster).execute(url);

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

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
