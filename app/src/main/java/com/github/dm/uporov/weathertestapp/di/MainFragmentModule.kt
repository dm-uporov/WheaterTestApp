package com.github.dm.uporov.weathertestapp.di

import androidx.fragment.app.Fragment
import com.github.dm.uporov.weathertestapp.ui.items.OnForecastItemClickListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object MainFragmentModule {

    @FragmentScoped
    @Provides
    fun provideOnForecastItemClickListener(fragment: Fragment) : OnForecastItemClickListener {
        if (fragment is OnForecastItemClickListener) return fragment

        throw RuntimeException("Fragment ${fragment.javaClass.canonicalName} should implement OnForecastItemClickListener")
    }
}