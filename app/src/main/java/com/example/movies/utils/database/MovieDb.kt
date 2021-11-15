package com.example.movies.utils.database

import androidx.room.*
import com.example.movies.movies.data.Movie
import javax.inject.Singleton

@Singleton
@Database(entities = [(Movie::class)], version = 1, exportSchema = false)
abstract class MovieDb : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}