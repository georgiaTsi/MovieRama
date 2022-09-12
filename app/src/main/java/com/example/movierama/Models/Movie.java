package com.example.movierama.Models;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Movie {

    public Integer id;

    public String poster_path;

    Boolean adult;

    String overview;

    public String release_date;

    ArrayList<Integer> genre_ids;

    String original_title;

    String original_language;

    public String title;

    String backdrop_path;

    Number popularity;

    Integer vote_count;

    Boolean video;

    public Number vote_averange;

    public Movie(Integer id, String title, Number vote_averange, String release_date, String poster_path){
        this.id = id;
        this.title = title;
        this.vote_averange = vote_averange;
        this.release_date = release_date;
        this.poster_path = poster_path;
    }

}
