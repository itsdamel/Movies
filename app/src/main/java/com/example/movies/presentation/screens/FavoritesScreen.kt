package com.example.movies.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movies.R
import com.example.movies.presentation.components.DisplayMovieList
import com.example.movies.presentation.components.MovieCard
import com.example.movies.presentation.viewModel.MoviesViewModel
import com.example.movies.ui.theme.MoviesTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: MoviesViewModel
) {
    val movieListState = viewModel.favoriteMovies.collectAsState()
    val movieList = movieListState.value

    MoviesTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .clickable { navController.popBackStack() },
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "return",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    ,
                    title = {
                        Text(
                            text = stringResource(R.string.favorite_screen_app_bar_title),
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    )
                )
            },

            content = {

                if(movieList.isEmpty()){
                    Box(modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                    Text(
                        text = stringResource(R.string.no_favorites_movies),
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                }}
               DisplayMovieList(
                   movieList = movieList,
                   navController = navController,
                   viewModel = viewModel
               )
            }

        )

    }





}
