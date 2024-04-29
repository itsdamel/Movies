package com.example.movies.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MovieInfo(
    title: String,
    releaseDate: String,
    voteAverage: String,

) {
    Column(
        modifier = Modifier
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    )

    {
        Text(
            text = title
        )

        Row() {
            Icon(
                Icons.Filled.Star,
                contentDescription = "Star icon",
                tint = MaterialTheme.colorScheme.secondary //ADD TO COLORS
            )
            Text(text = "${voteAverage} - ${releaseDate}")
        }

    }
}