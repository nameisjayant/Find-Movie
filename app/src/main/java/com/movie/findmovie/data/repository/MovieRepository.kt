package com.movie.findmovie.data.repository

import com.movie.findmovie.data.model.Movies
import com.movie.findmovie.data.network.ApiService
import com.movie.findmovie.utils.toResultFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: ApiService) {

    fun getMovieList() = toResultFlow {
        apiService.getMoviesList()
    }
}