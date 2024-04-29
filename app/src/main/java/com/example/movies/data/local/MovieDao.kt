package com.example.movies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity?

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE isFavorite = true")
    suspend fun getFavoriteMovies(): List<MovieEntity>

    @Query("UPDATE movies SET isFavorite = :isFavorite WHERE id = :movieId")
    suspend fun updateFavoriteMovieStatus(movieId: Int, isFavorite: Boolean)

    @Query("SELECT isFavorite FROM movies WHERE id = :movieId")
    suspend fun isMovieFavorite(movieId: Int): Boolean

}