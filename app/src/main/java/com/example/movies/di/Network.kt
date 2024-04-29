package com.example.movies.di

import com.example.movies.data.remote.MovieApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun createMovieApiService() : MovieApi {
    return Retrofit.Builder()
        .baseUrl(MovieApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieApi::class.java)
}