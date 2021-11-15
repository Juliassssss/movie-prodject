package com.example.movies.utils.di.builder

import com.example.movies.detail.di.DetailFragmentProvider
import com.example.movies.main.di.MainActivityModule
import com.example.movies.main.presentation.MainActivity
import com.example.movies.movies.di.MoviesFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(MainActivityModule::class), (MoviesFragmentProvider::class), (DetailFragmentProvider::class)])
    internal abstract fun bindMainActivity(): MainActivity

}