package com.movie.findmovie.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movie.findmovie.data.repository.MovieRepository
import com.movie.findmovie.utils.ApiResponse
import com.movie.findmovie.utils.ApiStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject
constructor(private val movieRepository: MovieRepository) : ViewModel() {
    val response: MutableState<ApiStates> = mutableStateOf(ApiStates.Empty)

    init {
        getPost()
    }

    private fun getPost() = viewModelScope.launch {
        movieRepository.getMovieList()
            .onStart {
                response.value = ApiStates.Loading
            }.catch { e ->
                response.value = ApiStates.Failure(e)
            }.collect { data ->
                response.value = ApiStates.Success(data)
            }
    }
}