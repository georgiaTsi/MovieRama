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

public class BrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        initRecyclerView();

        initSearch();

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_toolbar_search)
                                                     .getActionView();
        searchView.setSearchableInfo(searchManager
                                             .getSearchableInfo(getComponentName()));

        return true;
    }

    private void initRecyclerView(){
        populateList();

        RecyclerView recyclerView = findViewById(R.id.recyclerview_browser);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MovieAdapter movieAdapter = new MovieAdapter(this);
        recyclerView.setAdapter(movieAdapter);
    }

    private void populateList(){

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