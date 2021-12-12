package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    val page: Int,
    @SerializedName("results")
    var movies: MutableList<Movies>,
    val total_pages: Int,
    val total_results: Int,
    var categoryName:String
)

data class Movies(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Long,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)