package com.example.movies.data.remote


import com.example.movies.domain.model.Movie
import com.example.movies.data.remote.model.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<MovieListResponse>

    @GET("movie/")
    suspend fun getMovieById(
        @Query("api_key") apiKey: String,
        @Query("movie_id") id: Int
    ): Response<Movie>

    companion object {

        val API_KEY: String = "60333fe76f8b9de19cc5752f48b60ee0" //Should be hidden

        const val BASE_URL = "https://api.themoviedb.org/3/"

    }
}

