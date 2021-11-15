package com.example.movies.movies.di

import com.example.movies.movies.data.MovieFilterSource
import com.example.movies.movies.data.MoviePagingSource
import com.example.movies.movies.data.MovieRepoImp
import com.example.movies.movies.domain.MovieSourceFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MovieSourceModule {
    @Provides
    internal fun provideMoviesSource(): MovieSourceFactory {
        return MovieSourceFactory()
    }

    @Provides
    @Singleton
    internal fun provideMoviesPagingSource(movieRepoImp: MovieRepoImp): MoviePagingSource {
        return MoviePagingSource(movieRepoImp)
    }

    @Provides
    @Singleton
    internal fun provideMoviesFilterSource(movieRepoImp: MovieRepoImp): MovieFilterSource {
        return MovieFilterSource(movieRepoImp)
    }
}