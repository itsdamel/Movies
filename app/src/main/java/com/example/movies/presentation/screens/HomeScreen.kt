package com.example.movies.presentation.screens

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movies.R
import com.example.movies.presentation.components.DisplayMovieList
import com.example.movies.presentation.components.MovieCard
import com.example.movies.presentation.uiState.UiState
import com.example.movies.presentation.viewModel.MoviesViewModel
import com.example.movies.ui.theme.MoviesTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MoviesViewModel
) {

    val movieListState = viewModel.popularMovies.collectAsState().value

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.home_app_bar_title),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("favoritesScreen")
                    viewModel.getFavoriteMovies()
                },
                modifier = Modifier.shadow(
                    5.dp,
                    clip = true,
                    shape = CircleShape,
                    spotColor = MaterialTheme.colorScheme.tertiaryContainer, //ADD TO COLORS
                ),
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation()

            ) {
                Icon(Icons.Outlined.FavoriteBorder, "Favorites")
            }
        },
        content = {

            when (movieListState) {

                is UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(50.dp),
                            strokeWidth = 5.dp,
                            trackColor = MaterialTheme.colorScheme.primaryContainer,

                        )
                    }

                }
                is UiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Error loading movies :( Check your internet connection and try again.",
                            textAlign = TextAlign.Center
                        )
                    }

                }

                is UiState.Success -> {
                   DisplayMovieList(

                       movieList = movieListState.data,
                       navController = navController,
                       viewModel = viewModel
                   )
                }

            }



        }
    )

}

