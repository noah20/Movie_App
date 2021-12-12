package com.example.movieapp.di.module

import com.example.movieapp.data.api.MovieRestApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideRetrofit():Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideClient())
            .build()
    }


    @Provides
    @Singleton
    fun provideMovieRestApi(retrofit: Retrofit) : MovieRestApi{
        return  retrofit.create(MovieRestApi::class.java)
    }

    @Provides
    fun provideClient() : OkHttpClient{

        val interceptor = HttpLoggingInterceptor()
        interceptor.level  =  HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
}