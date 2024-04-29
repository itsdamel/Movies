package com.example.movies.domain.mappers

import com.example.movies.domain.model.Movie
import com.example.movies.data.local.MovieEntity

class MoviesMapper {

    fun movieToMovieEntity(movie: Movie): MovieEntity {
        return MovieEntity(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            posterPath = movie.posterPath,
            backdropPath = movie.backdropPath,
            voteAverage = movie.voteAverage,
            releaseDate = movie.releaseDate,
            isFavorite = movie.isFavorite

        )
    }

    fun movieEntityToMovie(movieEntity: MovieEntity): Movie {
        return Movie(
            id = movieEntity.id,
            title = movieEntity.title,
            overview = movieEntity.overview,
            posterPath = movieEntity.posterPath,
            backdropPath = movieEntity.backdropPath,
            voteAverage = movieEntity.voteAverage,
            releaseDate = movieEntity.releaseDate,
            isFavorite = movieEntity.isFavorite
        )
    }
}