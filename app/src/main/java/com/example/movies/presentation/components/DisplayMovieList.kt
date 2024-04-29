package com.example.movies.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.viewModel.MoviesViewModel

@Composable
fun DisplayMovieList(
    movieList: List<Movie>,
    navController: NavController,
    viewModel: MoviesViewModel
) {
    LazyColumn(
        modifier = Modifier.padding(top= 60.dp),
        content = {
            items(movieList) {movie ->
                MovieCard(movie = movie, navController = navController, viewModel = viewModel)
            }
        },
        verticalArrangement = Arrangement.spacedBy(20.dp)
    )
}