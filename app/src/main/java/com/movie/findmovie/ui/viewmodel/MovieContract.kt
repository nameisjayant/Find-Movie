package com.movie.findmovie.ui.viewmodel

import com.movie.findmovie.data.model.Movies

class MovieContract {

    data class MovieState(
        val data:List<Movies.Results> = emptyList(),
        val error:String = "",
        val isLoading:Boolean = true
    )

}