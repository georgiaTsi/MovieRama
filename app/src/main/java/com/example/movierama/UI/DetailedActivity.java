package com.example.movierama.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movierama.Api.Api;
import com.example.movierama.Api.RetrofitClient;
import com.example.movierama.Helpers.DownloadImageTask;
import com.example.movierama.Helpers.MovieFavoriteHelper;
import com.example.movierama.Models.Movie;
import com.example.movierama.Models.Review;
import com.example.movierama.Models.ReviewsResponse;
import com.example.movierama.Models.SimilarsResponse;
import com.example.movierama.R;
import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailedActivity extends AppCompatActivity {

    Integer position;

    ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        getSupportActionBar().hide();

        position = getIntent().getIntExtra("position", -1);
        Integer movieId = getIntent().getIntExtra("movieId", -1);

        if(movieId == -1) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT);

            return;
        }

        getDetails(movieId);

        getReviews(movieId);

        getSimilars(movieId);
    }

    @Override
    public void onBackPressed() {

        Intent resultIntent = new Intent();
        resultIntent.putExtra("position", position);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();

        super.onBackPressed();
    }

    private void getDetails(Integer movieId){

        Call<Movie> call = RetrofitClient.getInstance().getMyApi().getMovieDetails(movieId, Api.API_KEY);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                initUI(response.body());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e("Error", t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void getReviews(Integer movieId){

        TextView reviewsTextViewTitle = findViewById(R.id.tetxview_reviewtitle);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_reviews);

        reviewAdapter = new ReviewAdapter(this);
        recyclerView.setAdapter(reviewAdapter);

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);

        Call<ReviewsResponse> call = RetrofitClient.getInstance().getMyApi().getMovieReviews(movieId, Api.API_KEY);

        call.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                List<Review> reviews = response.body().results;

                if(reviews == null){
                    recyclerView.setVisibility(View.GONE);
                    reviewsTextViewTitle.setVisibility(View.GONE);

                    return;
                }

                reviewAdapter.addToAdapter(reviews);
            }

            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {
                Log.e("Error", t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void getSimilars(Integer movieId){

        TextView title = findViewById(R.id.tetxview_similartitle);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_similar);

        SimilarAdapter adapter = new SimilarAdapter(this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);

        Call<SimilarsResponse> call = RetrofitClient.getInstance().getMyApi().getSimilar(movieId, Api.API_KEY);

        call.enqueue(new Callback<SimilarsResponse>() {
            @Override
            public void onResponse(Call<SimilarsResponse> call, Response<SimilarsResponse> response) {
                List<LinkedTreeMap> similars = response.body().results;

                if(similars == null){
                    recyclerView.setVisibility(View.GONE);
                    title.setVisibility(View.GONE);

                    return;
                }

                adapter.addToAdapter(similars);//TODO show similars
            }

            @Override
            public void onFailure(Call<SimilarsResponse> call, Throwable t) {
                Log.e("Error", t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void initUI(Movie movie){
        ImageView posterImageView = findViewById(R.id.imageview_poster);
        TextView titleTextView = findViewById(R.id.textview_title);
        TextView genreTextView = findViewById(R.id.textview_genre);
        RatingBar ratingBar = findViewById(R.id.ratingbar);
        ImageButton favoriteButton = findViewById(R.id.imagebutton_favorite);
        TextView descriptionTextView = findViewById(R.id.tetxview_description);
//        TextView directorTextView = findViewById(R.id.tetxview_director);

        //Image
//        String url = "https://media.geeksforgeeks.org/wp-content/cdn-uploads/gfg_200x200-min.png";
//        new DownloadImageTask(posterImageView).execute(url);//TODO get real image
        int[] images = {R.drawable.poster_1, R.drawable.poster_2, R.drawable.poster_3};
        Random rand = new Random();
        posterImageView.setImageResource(images[rand.nextInt(images.length)]);

        //Title
        titleTextView.setText(movie.title);

        //Genre
        String genreString = "";
        for(LinkedTreeMap genre : movie.genres){
            genreString = genreString + genre.get("name") + ", ";
        }
        genreTextView.setText(genreString.subSequence(0, genreString.length()-2));

        //Rating
        ratingBar.setRating(movie.vote_average/2);

        //Favorite
        MovieFavoriteHelper db = new MovieFavoriteHelper(this);

        final Boolean[] isFavorite = {db.getIsFavorite(movie.id)};

        if(isFavorite[0])
            favoriteButton.setImageResource(R.drawable.icon_heart_full);
        else
            favoriteButton.setImageResource(R.drawable.icon_heart);

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFavorite[0] = !isFavorite[0];

                db.updateMovie(movie.id, isFavorite[0]);

                if(isFavorite[0])
                    favoriteButton.setImageResource(R.drawable.icon_heart_full);
                else
                    favoriteButton.setImageResource(R.drawable.icon_heart);
            }
        });

        //Description
        descriptionTextView.setText(movie.overview);

        //Director
        //directorTextView.setText(movie.director);
    }
}
