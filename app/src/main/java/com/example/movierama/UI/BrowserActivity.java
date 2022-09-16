package com.example.movierama.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movierama.Api.RetrofitClient;
import com.example.movierama.Models.PopularMoviesReponse;
import com.example.movierama.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.movierama.Api.Api.API_KEY;

public class BrowserActivity extends AppCompatActivity {
    String LANGUAGE = "en-US";
    Integer CURRENT_PAGE = 0;

    MovieAdapter movieAdapter;

    boolean isLoading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        getSupportActionBar().hide();

        initRecyclerView();

        initSearch();
    }

    private void initRecyclerView(){

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swiperefresh);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_browser);

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);

        movieAdapter = new MovieAdapter(this);
        recyclerView.setAdapter(movieAdapter);

        getPopularMovies();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = linearLayout.getChildCount();
                    totalItemCount = linearLayout.getItemCount();
                    pastVisiblesItems = linearLayout.findFirstVisibleItemPosition();

                    if (isLoading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {//last item
                            isLoading = false;

                            getPopularMovies();

                            isLoading = true;
                        }
                    }
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                CURRENT_PAGE = 0;

                getPopularMovies();

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getPopularMovies(){

        CURRENT_PAGE++;

        Call<PopularMoviesReponse> call = RetrofitClient.getInstance().getMyApi().getMovies(API_KEY, LANGUAGE, CURRENT_PAGE);

        call.enqueue(new Callback<PopularMoviesReponse>() {
            @Override
            public void onResponse(Call<PopularMoviesReponse> call, Response<PopularMoviesReponse> response) {
                PopularMoviesReponse responseBody = response.body();

                movieAdapter.addToAdapter(responseBody.results);
            }

            @Override
            public void onFailure(Call<PopularMoviesReponse> call, Throwable t) {
                Toast.makeText(movieAdapter.activity, "An error has occured", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSearch(){
        EditText searchEditText  = findViewById(R.id.edittext_search);

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event){
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(!searchEditText.getText().toString().isEmpty() && searchEditText.getText().toString().length() > 1)
                        performSearch(searchEditText.getText().toString());

                    return true;
                }

                return false;
            }
        });
    }

    private void performSearch(String searchText){
        Call<PopularMoviesReponse> call = RetrofitClient.getInstance().getMyApi().getSearchMovie(API_KEY, searchText);

        call.enqueue(new Callback<PopularMoviesReponse>() {
            @Override
            public void onResponse(Call<PopularMoviesReponse> call, Response<PopularMoviesReponse> response) {
                PopularMoviesReponse responseBody = response.body();

                movieAdapter.updateAdapter(responseBody.results);
            }

            @Override
            public void onFailure(Call<PopularMoviesReponse> call, Throwable t) {
                Toast.makeText(movieAdapter.activity, "An error has occured", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Integer position = data.getIntExtra("position", -1);

        movieAdapter.notifyItemChanged(position);
    }
}