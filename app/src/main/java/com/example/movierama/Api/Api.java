package com.example.movierama.Api;

import com.example.movierama.Models.Movie;
import com.example.movierama.Models.PopularMoviesReponse;
import com.example.movierama.Models.Review;
import com.example.movierama.Models.ReviewsResponse;
import com.example.movierama.Models.SimilarsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    String API_KEY = "30842f7c80f80bb3ad8a2fb98195544d";

    String BASE_URL = "https://api.themoviedb.org/3/";

    @GET("movie/popular")
    Call<PopularMoviesReponse> getMovies(@Query(value = "api_key") String api_key, @Query("language")String language, @Query("page")Integer page);

    @GET("search/movie")
    Call<PopularMoviesReponse> getSearchMovie(@Query(value = "api_key") String api_key, @Query("query")String query);

    @GET("movie/{movie_id}")
    Call<Movie> getMovieDetails(@Path("movie_id")Integer movie_id, @Query(value = "api_key") String api_key);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewsResponse> getMovieReviews(@Path("movie_id")Integer movie_id, @Query(value = "api_key") String api_key);

    @GET("movie/{movie_id}/similar")
    Call<SimilarsResponse> getSimilar(@Path("movie_id")Integer movie_id, @Query(value = "api_key") String api_key);
}
