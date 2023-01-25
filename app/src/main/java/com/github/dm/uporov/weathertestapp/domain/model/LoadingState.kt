package com.github.dm.uporov.weathertestapp.domain.model

sealed class LoadingState<T : Any?> private constructor() {

    class Loading<T> : LoadingState<T>()

    data class Loaded<T>(val data: T) : LoadingState<T>()

    data class Error<T>(val error: Throwable) : LoadingState<T>()
}
