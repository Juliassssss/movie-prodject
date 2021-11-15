package com.example.movies.utils.database

import androidx.room.*
import com.example.movies.movies.data.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Query("SELECT id FROM movies")
    fun fetchFavouriteMovies(): List<Int?>

    @Delete()
    fun removeMovie(movie: Movie)

}