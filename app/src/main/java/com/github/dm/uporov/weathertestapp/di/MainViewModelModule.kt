package com.github.dm.uporov.weathertestapp.di

import com.github.dm.uporov.weathertestapp.domain.converter.*
import com.github.dm.uporov.weathertestapp.domain.repository.ForecastRepository
import com.github.dm.uporov.weathertestapp.domain.repository.ForecastRepositoryImpl
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
    fun bindWeatherRepository(repository: ForecastRepositoryImpl): ForecastRepository

    @ViewModelScoped
    @Binds
    fun bindItemsConverter(converter: ForecastItemsConverterImpl): ForecastItemsConverter

    @ViewModelScoped
    @Binds
    fun bindDateConverter(formatter: DateFormatterImpl): DateFormatter

    @ViewModelScoped
    @Binds
    fun bindOpenWeatherIconUrlHelper(formatter: OpenWeatherIconUrlFormatterImpl): OpenWeatherIconUrlFormatter

    @ViewModelScoped
    @Binds
    fun bindPressureFormatter(formatter: PressureFormatterImpl): PressureFormatter

    @ViewModelScoped
    @Binds
    fun bindHumidityFormatter(formatter: HumidityFormatterImpl): HumidityFormatter

    @ViewModelScoped
    @Binds
    fun bindVisibilityFormatter(formatter: VisibilityFormatterImpl): VisibilityFormatter

    @ViewModelScoped
    @Binds
    fun bindProbabilityOfPrecipitationFormatter(formatter: ProbabilityOfPrecipitationFormatterImpl): ProbabilityOfPrecipitationFormatter

}