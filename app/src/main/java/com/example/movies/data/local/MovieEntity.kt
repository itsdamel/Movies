package com.example.movies.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import dagger.multibindings.IntoMap

@Entity(tableName = "movies")
data class MovieEntity (

    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val voteAverage: Double,
    val releaseDate: String,
    val isFavorite: Boolean = false
)