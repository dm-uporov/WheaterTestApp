package com.github.dm.uporov.weathertestapp.di

import com.github.dm.uporov.weathertestapp.repository.ForecastItemConverter
import com.github.dm.uporov.weathertestapp.repository.ForecastItemConverterImpl
import com.github.dm.uporov.weathertestapp.repository.ForecastRepository
import com.github.dm.uporov.weathertestapp.repository.ForecastRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface MainModule {

    @ViewModelScoped
    @Binds
    fun bindWeatherRepository(repositoryImpl: ForecastRepositoryImpl) : ForecastRepository

    @ViewModelScoped
    @Binds
    fun bindConverter(converterImpl: ForecastItemConverterImpl): ForecastItemConverter
}