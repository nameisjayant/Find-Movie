package com.movie.findmovie.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.movie.findmovie.R
import com.movie.findmovie.data.network.ApiService
import com.movie.findmovie.ui.theme.FindMovieTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindMovieTheme() {
                androidx.compose.material.Surface {
                    val bundle = intent.extras
                    val image = bundle?.getString("image")
                    val name = bundle?.getString("title")
                    val rating = bundle?.getString("rating")
                    val description = bundle?.getString("description")
                    MovieDetails(name!!, image!!, rating!!, description!!)
                }
            }
        }
    }
}

@Composable
fun MovieDetails(name: String, image: String, rating: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
    ) {
        Image(
            painter = rememberImagePainter(
                data = image,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_foreground)
                }
            ),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(text = name, style = MaterialTheme.typography.h6)
        Text(text = rating, style = MaterialTheme.typography.caption)
        Text(
            text = description,
            style = MaterialTheme.typography.caption
        )

    }
}