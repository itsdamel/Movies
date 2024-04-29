package com.example.movies.domain

import com.example.movies.domain.model.Movie
import com.example.movies.data.remote.model.MovieListResponse


interface MovieRepository  {
    suspend fun getPopularMovies(
        apiKey: String,
        page: Int
    ) : Result<MovieListResponse>

    suspend fun getMovieById(
        apiKey: String,
        id: Int
    ) : Result<Movie>


}