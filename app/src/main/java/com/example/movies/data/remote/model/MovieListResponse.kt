package com.example.movies.data.remote.model

import com.example.movies.domain.model.Movie

data class MovieListResponse(
    val page: Int?,
    val results: List<Movie>
)
