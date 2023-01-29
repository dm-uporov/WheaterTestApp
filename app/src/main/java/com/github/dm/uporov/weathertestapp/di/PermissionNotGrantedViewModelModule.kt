package com.github.dm.uporov.weathertestapp.di

import com.github.dm.uporov.weathertestapp.domain.repository.PermissionDenialsCountRepository
import com.github.dm.uporov.weathertestapp.domain.repository.PermissionDenialsCountRepositoryImpl
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
    fun bindWeatherRepository(repository: PermissionDenialsCountRepositoryImpl): PermissionDenialsCountRepository

}