package com.example.movies.utils.network

sealed class ResultType<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResultType<T>()
    data class Error(val error: Exception) : ResultType<Nothing>()
}