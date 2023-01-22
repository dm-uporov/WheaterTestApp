package com.github.dm.uporov.weathertestapp.di

import com.github.dm.uporov.weathertestapp.repository.*
import com.github.dm.uporov.weathertestapp.repository.converter.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface MainViewModelModule {

    @ViewModelScoped
    @Binds
    fun bindWeatherRepository(repository: ForecastRepositoryImpl) : ForecastRepository

    @ViewModelScoped
    @Binds
    fun bindItemsConverter(converter: ForecastItemsConverterImpl): ForecastItemsConverter

    @ViewModelScoped
    @Binds
    fun bindDateConverter(converter: DateConverterImpl): DateConverter

    @ViewModelScoped
    @Binds
    fun bindOpenWeatherIconUrlHelper(helper: OpenWeatherIconUrlHelperImpl): OpenWeatherIconUrlHelper

    @ViewModelScoped
    @Binds
    fun bindTemperatureConverter(converter: TemperatureConverterImpl):  TemperatureConverter

}