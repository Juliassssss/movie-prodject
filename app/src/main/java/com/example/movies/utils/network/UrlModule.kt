package com.example.movies.utils.network

import com.example.movies.utils.commons.AppConstants.Companion.BASE_URL_KEY
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class UrlModule {
    @Provides
    @Singleton
    @Named(BASE_URL_KEY)
    fun provideBaseUrl(): String {
        return "https://api.themoviedb.org/3/"
    }
}