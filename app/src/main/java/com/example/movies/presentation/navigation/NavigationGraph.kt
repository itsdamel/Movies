package com.example.movies.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movies.data.local.MovieDao
import com.example.movies.data.remote.MovieRepositoryImpl
import com.example.movies.di.createMovieApiService
import com.example.movies.presentation.screens.DetailScreen
import com.example.movies.presentation.screens.FavoritesScreen
import com.example.movies.presentation.screens.HomeScreen
import com.example.movies.presentation.viewModel.MoviesViewModel

@Composable
fun NavigationGraph(
    movieDao: MovieDao
) {
    val repository = MovieRepositoryImpl(createMovieApiService(),movieDao)
    val viewModel = MoviesViewModel(repository, movieDao)
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "homeScreen"
    ) {
        composable("homeScreen") {
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable("detailScreen") {
            DetailScreen(navController = navController, viewModel = viewModel)
        }
        composable("favoritesScreen") {
            FavoritesScreen(navController = navController, viewModel = viewModel)
        }
    }
}