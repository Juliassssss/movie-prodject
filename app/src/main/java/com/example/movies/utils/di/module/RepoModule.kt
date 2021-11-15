package com.example.movies.utils.di.module

import com.example.movies.detail.data.TrailerApi
import com.example.movies.movies.data.MovieApi
import com.example.movies.movies.data.MovieRepo
import com.example.movies.movies.data.MovieRepoImp
import com.example.movies.utils.database.MovieDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides
    @Singleton
    internal fun provideMovieRepository(movieDao: MovieDao,
                                        movieApi: MovieApi,
                                        trailerApi: TrailerApi
    ): MovieRepo {
        return MovieRepoImp(movieDao, movieApi, trailerApi)
    }

}