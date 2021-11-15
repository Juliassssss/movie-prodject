package com.example.movies.movies.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(
    var page: Int?,
    var total_results: Int?,
    var total_pages: Int,
    var results: ArrayList<Movie>
) : Parcelable

