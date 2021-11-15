package com.example.movies.movies.domain

import com.example.movies.movies.data.Movie
import com.example.movies.movies.data.MovieFilterSource
import com.example.movies.movies.data.MoviePagingSource
import androidx.paging.PagingSource
import javax.inject.Inject

class MovieSourceFactory @Inject constructor() {

    @Inject
    lateinit var movieFilterSource: MovieFilterSource

    @Inject
    lateinit var moviePagingSource: MoviePagingSource

    fun getSource( filterText: String): PagingSource<Int, Movie> {
        return if (filterText.isBlank() || filterText.isEmpty()) moviePagingSource
        else movieFilterSource.apply { this.filterText = filterText }
    }
}
