package com.example.movierama.Models;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

public class SimilarsResponse {

    public Integer page;

    public List<LinkedTreeMap> results;

    public Integer total_pages;

    public Integer total_results;
}
