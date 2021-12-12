package com.example.movieapp.data.repository

import com.example.movieapp.data.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getCategory(category:String) = apiHelper.getCategory(category)
    suspend fun loadMoreMoviesForCategory(category:String, page:Int) = apiHelper.loadMoreMoviesForCategory(category,page)
    suspend fun getMovieDetails(id:Long) = apiHelper.getMovieDetails(id)
}