package com.example.movies.movies.presentation

import com.example.movies.movies.data.Movie
import androidx.paging.PagingData

sealed class MoviesViewState {
    class FetchingMoviesError(val errorMessage: String?) : MoviesViewState()
    class FetchingMoviesSuccess(val movies: PagingData<Movie>) : MoviesViewState()
}
