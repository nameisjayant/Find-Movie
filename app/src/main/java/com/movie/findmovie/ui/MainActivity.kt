package com.movie.findmovie.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.movie.findmovie.R
import com.movie.findmovie.data.model.Movies
import com.movie.findmovie.data.network.ApiService
import com.movie.findmovie.ui.MovieDetailActivity
import com.movie.findmovie.ui.viewmodel.MovieViewModel
import com.movie.findmovie.ui.theme.FindMovieTheme
import com.movie.findmovie.utils.ApiStates
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MovieViewModel by viewModels()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindMovieTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Text(
                            text = "Top Movies",
                            style = typography.h4,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(10.dp)
                        )
                        GETData(viewModel = viewModel, this@MainActivity)
                    }

                }
            }
        }
    }


}

@ExperimentalMaterialApi
@Composable
fun GETData(viewModel: MovieViewModel, context: Context) {

    if(viewModel.response.value.data.isNotEmpty())

        LazyColumn {
            items(viewModel.response.value.data) { response ->
                MovieList(response) {
                    onGettingClick(response, context)
                }
            }
        }

    if(viewModel.response.value.error.isNotEmpty())
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = viewModel.response.value.error)
        }

    if(viewModel.response.value.isLoading)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }


}


@ExperimentalMaterialApi
@Composable
fun MovieList(results: Movies.Results, onGettingClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = androidx.compose.foundation.shape.CornerSize(16.dp)),
        onClick = { onGettingClick() }

    ) {
        Row {
            Image(
                painter = rememberImagePainter(
                    data = "${ApiService.BASE_POSTER_URL}${results.poster_path}",
                    builder = {
                        transformations(CircleCropTransformation())
                        crossfade(true)
                        placeholder(R.drawable.ic_launcher_foreground)
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterVertically)
                    .padding(start = 5.dp)
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = results.original_title!!, style = typography.h6)
                Text(text = results.overview!!, style = typography.caption)

            }
        }
    }
}


private fun onGettingClick(results: Movies.Results, context: Context) {
    val intent = Intent(context, MovieDetailActivity::class.java).apply {
        putExtra("image", "${ApiService.BASE_POSTER_URL}${results.poster_path}")
        putExtra("title", results.original_title)
        putExtra("rating", results.vote_average)
        putExtra("description", results.overview)
    }
    context.startActivity(intent)
}