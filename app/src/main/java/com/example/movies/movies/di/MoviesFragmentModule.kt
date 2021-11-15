package com.example.movies.movies.di

import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.movies.presentation.MovieAdapter
import com.example.movies.movies.presentation.MoviesFragment
import com.example.movies.utils.commons.GridSpacingItemDecoration
import dagger.Module
import dagger.Provides

@Module
class MoviesFragmentModule {

    @Provides
    internal fun provideGridLayoutManager(fragment: MoviesFragment): GridLayoutManager {
        return GridLayoutManager(fragment.requireContext(), 2)
    }

    @Provides
    internal fun provideGridSpacingItemDecoration(): GridSpacingItemDecoration {
        return GridSpacingItemDecoration(2, 5, true)
    }

    @Provides
    internal fun provideMovieAdapter(): MovieAdapter {
        return MovieAdapter()
    }

}