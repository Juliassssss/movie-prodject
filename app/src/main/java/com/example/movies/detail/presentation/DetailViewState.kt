package com.example.movies.detail.presentation

import com.example.movies.detail.data.TrailerRemote

sealed class DetailViewState {
    object TrailersFetchedError : DetailViewState()
    class TrailersFetchedSuccess(val trailers:  List<TrailerRemote>) : DetailViewState()
    class LikeState(val isLiked: Boolean) : DetailViewState()
    class MessageRes(val resId: Int) : DetailViewState()
}
