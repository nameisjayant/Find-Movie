package com.movie.findmovie.repository

import com.movie.findmovie.data.model.Movies

object TestFindMovieData {

    fun getFindMovieData(): Movies {

        return Movies(
            page = 1,
            results = listOf(
                Movies.Results(
                    id = 752623,
                    original_title = "The Lost City",
                    overview = "A reclusive romance novelist who was sure nothing could be worse than getting stuck on a book tour with her cover model, until a kidnapping attempt sweeps them both into a cutthroat jungle adventure, proving life can be so much stranger, and more romantic, than any of her paperback fictions.",
                    poster_path = "/neMZH82Stu91d3iqvLdNQfqPPyl.jpg\"",
                    vote_average = "6.8"
                )
            )
        )
    }
}