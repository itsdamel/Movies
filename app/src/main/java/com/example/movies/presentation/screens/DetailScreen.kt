package com.example.movies.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.components.MovieInfo
import com.example.movies.presentation.viewModel.MoviesViewModel

@Composable
fun DetailScreen(
    viewModel: MoviesViewModel,
    navController: NavController
) {

    val movieState = viewModel.selectedMovie.collectAsState()
    val movie: Movie = movieState.value

   var isFavorite by remember {
       mutableStateOf(movie.isFavorite)    //A better approach would be to use flow and collectAsState
   }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ){
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()

        ){

            AsyncImage(
                model = "https://image.tmdb.org/t/p/w185${movie.backdropPath}",
                contentDescription = "${movie.title} poster",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth
            )
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "arrow back",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)
                    .clickable { navController.popBackStack() }
            )
        }
        //MOVIE DETAILS
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = "https://image.tmdb.org/t/p/w185${movie.posterPath}",
                contentDescription = "${movie.title} poster",
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight()
            )

            MovieInfo(
                title = movie.title,
                releaseDate = viewModel.getYearOfRelease(movie.releaseDate),
                voteAverage = viewModel.formatRating(movie.voteAverage)
            )
        }

        //MOVIE DESCRIPTION
       Text(text = movie.overview,
           color = MaterialTheme.colorScheme.onBackground,
           textAlign = TextAlign.Justify,
           lineHeight = 26.sp,
           modifier = Modifier.padding(horizontal = 10.dp))
         Spacer(modifier = Modifier.weight(1f))

        //ADD TO FAVORITES BUTTON
        Button(
            onClick = {
                         viewModel.updateIsFavorite(movie)
                            isFavorite = !isFavorite

                      },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(bottom = 40.dp, start = 20.dp, end = 20.dp)


        ) {
            Icon(
                imageVector= if(!isFavorite) Icons.Default.Add else Icons.Default.Clear,
                contentDescription= "add symbol",
               )
            Text(
                text = if (!isFavorite)"Añadir a favoritos" else "Quitar de favoritos",
            )
        }
    }


}