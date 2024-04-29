package com.example.movies.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.movies.data.local.MovieDatabase
import com.example.movies.presentation.navigation.NavigationGraph
import com.example.movies.ui.theme.MoviesTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieDatabase = Room.databaseBuilder(
            applicationContext,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
        val movieDao = movieDatabase.movieDao()

        setContent {
            MoviesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    NavigationGraph(movieDao)
                }
            }
        }
    }
}


/*TODO
*
* CREATE COMMON COMPONENTS
* REFACTOR VIEWMODEL
* */
