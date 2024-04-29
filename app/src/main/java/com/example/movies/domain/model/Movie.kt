package com.example.movies.domain.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int = 0,
    val title: String = "dummy",
    val overview: String = "dummy",
    //Since I prefer using pascal case in my code, I will use the @SerializedName annotation to map the JSON key to the Kotlin property.
    @SerializedName("poster_path") val posterPath: String = "dummy",
    @SerializedName("backdrop_path") val backdropPath: String =  "dummy",
    @SerializedName("vote_average") val voteAverage: Double= 0.0,
    @SerializedName("release_date")val releaseDate: String = "dummy",
    var isFavorite: Boolean = false
)
