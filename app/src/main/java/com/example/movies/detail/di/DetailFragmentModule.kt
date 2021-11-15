package com.example.movies.detail.di

import com.example.movies.detail.presentation.DetailFragment
import com.example.movies.detail.presentation.TrailerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides

@Module
class DetailFragmentModule {

    @Provides
    internal fun provideLinearLayoutManager(fragment: DetailFragment): LinearLayoutManager {
        return LinearLayoutManager(fragment.activity)
    }

    @Provides
    internal fun provideTrailerAdapter(): TrailerAdapter {
        return TrailerAdapter(ArrayList())
    }

}