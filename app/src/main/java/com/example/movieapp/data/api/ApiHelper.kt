package com.example.movieapp.data.api

import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: MovieRestApi) {

    suspend fun getCategory(category:String) = apiService.getCategory(category)
    suspend fun loadMoreMoviesForCategory(category:String,page: Int) = apiService.loadMoreMovies(category,page)
    suspend fun getMovieDetails(id: Long) = apiService.getMoviesDetails(id)

}