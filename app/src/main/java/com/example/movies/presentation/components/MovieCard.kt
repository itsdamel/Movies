package com.example.movies.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.movies.R
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.viewModel.MoviesViewModel

@Composable
fun MovieCard(
    movie: Movie,
    navController: NavController,
    viewModel: MoviesViewModel
) {

    val isFavorite = viewModel.isFavorite(movie.id).collectAsState(movie.isFavorite)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable {
                viewModel.getMovieById(movie.id)
                navController.navigate("detailScreen")
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.Start)

        ) {
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.TopStart
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w185${movie.posterPath}",
                contentDescription = "${movie.title} poster",
                modifier = Modifier.fillMaxHeight()
            )
            if(!isFavorite.value) {
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = RectangleShape
                        )
                        .width(38.dp)
                        .height(44.dp),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription ="Add to favorites",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            } else {
                Box(
                    modifier = Modifier
                        .width(38.dp)
                        .height(44.dp),
                    contentAlignment = Alignment.Center,
                ){
                    Image(
                        painter = painterResource(id = R.drawable.favoriteshape),
                        contentDescription = "Marked as favorite",
                        modifier = Modifier.fillMaxSize()
                    )
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription ="Add to favorites",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

        }

        MovieInfo(
            title = movie.title,
            releaseDate = viewModel.getYearOfRelease(movie.releaseDate),
            voteAverage = viewModel.formatRating(movie.voteAverage)
        )


    }
}