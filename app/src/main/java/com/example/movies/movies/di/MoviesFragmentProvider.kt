package com.example.movies.movies.di

import com.example.movies.movies.presentation.MoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MoviesFragmentProvider {

    @ContributesAndroidInjector(modules =[(MoviesFragmentModule::class),(MovieSourceModule::class),])
    internal abstract fun provideMainFragmentFactory(): MoviesFragment

}