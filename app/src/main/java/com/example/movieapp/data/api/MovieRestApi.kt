package com.example.movieapp.data.api

import com.example.movieapp.data.model.CategoryResponse
import com.example.movieapp.data.model.MoviesDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieRestApi {

    @GET("3/movie/{path}?api_key=1c97b180c014ce1db5ce1d1c86cb46bb")
    suspend fun getCategory(@Path("path") path:String) : CategoryResponse?

    @GET("3/movie/{path}?api_key=1c97b180c014ce1db5ce1d1c86cb46bb")
    suspend fun loadMoreMovies(@Path("path") path:String, @Query("page") page:Int) : CategoryResponse?

    @GET("3/movie/{path}?api_key=1c97b180c014ce1db5ce1d1c86cb46bb")
    suspend fun getMoviesDetails(@Path("path") path: Long) :MoviesDetailsResponse?



}