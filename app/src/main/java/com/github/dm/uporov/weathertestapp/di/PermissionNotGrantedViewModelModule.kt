package com.github.dm.uporov.weathertestapp.di

import com.github.dm.uporov.weathertestapp.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface PermissionNotGrantedViewModelModule {

    @ViewModelScoped
    @Binds
    fun bindWeatherRepository(repository: PermissionDenialCountRepositoryImpl): PermissionDenialCountRepository

}