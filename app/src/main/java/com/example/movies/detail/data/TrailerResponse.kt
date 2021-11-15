package com.example.movies.detail.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrailerResponse(
    var id: Int,
    var results: List<TrailerRemote>
) : Parcelable
