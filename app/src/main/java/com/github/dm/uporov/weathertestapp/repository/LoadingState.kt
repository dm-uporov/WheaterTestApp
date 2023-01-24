package com.github.dm.uporov.weathertestapp.repository

sealed class LoadingState<T : Any?> private constructor() {

    class Loading<T> : LoadingState<T>()

    data class Loaded<T>(val data: T) : LoadingState<T>()

    data class Error<T>(val message: String) : LoadingState<T>()
}
