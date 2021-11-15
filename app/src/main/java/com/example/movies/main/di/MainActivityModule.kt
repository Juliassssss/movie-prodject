package com.example.movies.main.di

import androidx.lifecycle.ViewModelProvider
import com.example.movies.movies.data.MovieRepoImp
import com.example.movies.movies.domain.MovieSourceFactory
import com.example.movies.movies.presentation.MoviesViewModel
import com.example.movies.utils.commons.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    internal fun provideMoviesViewModel(
        movieRepoImp: MovieRepoImp,
        sourceFactory: MovieSourceFactory
    ): MoviesViewModel {
        return MoviesViewModel(movieRepoImp, sourceFactory)
    }

    @Provides
    internal fun provideMoviesViewModelFactory(moviesViewModel: MoviesViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(moviesViewModel)
    }

}