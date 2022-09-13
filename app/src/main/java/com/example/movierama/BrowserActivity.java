package com.example.movierama;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.movierama.Models.Movie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowserActivity extends AppCompatActivity {

    List<Movie> movies = new ArrayList<>();

    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        initRecyclerView();

        initSearch();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_toolbar_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    private void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.recyclerview_browser);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieAdapter = new MovieAdapter(this);
        recyclerView.setAdapter(movieAdapter);

        getPopularMovies();
    }

    private void getPopularMovies(){
//        MovieFavoriteHelper db = new MovieFavoriteHelper(this);
//
//        for(int i = 0; i < 10; i++) {
//            db.insertMovie(i);
//            movies.add(new Movie(i, "Movie " + i, 4 / 5, "12/9/2022", "path"));
//        }
//
//        movieAdapter.updateAdapter(movies);

        Call<List<Movie>> call = RetrofitClient.getInstance().getMyApi().getMovies();

        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                List<Movie> popularMoviesList = response.body();

                movieAdapter.updateAdapter(popularMoviesList);
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Toast.makeText(movieAdapter.activity, "An error has occured", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSearch(){
        ImageButton searchButton  = findViewById(R.id.imagebutton_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrowserActivity.this, SearchActivity.class);
                BrowserActivity.this.startActivity(intent);

            }
        });
    }

}