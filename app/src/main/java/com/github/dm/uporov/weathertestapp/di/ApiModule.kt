package com.github.dm.uporov.weathertestapp.di

import com.github.dm.uporov.weathertestapp.BuildConfig
import com.github.dm.uporov.weathertestapp.api.WeatherApi
import com.github.dm.uporov.weathertestapp.api.adapter.OpenWeatherApiTimestampAdapter
import com.github.dm.uporov.weathertestapp.api.exception.NetworkIsNotAvailableException
import com.github.dm.uporov.weathertestapp.domain.repository.IsInternetConnectedRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import java.sql.Timestamp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = "https://api.openweathermap.org/"
    private const val API_KEY_QUERY_KEY = "appid"

    @Singleton
    @Provides
    fun provideWeatherApi(
        client: OkHttpClient,
        gson: Gson
    ): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(URL(BASE_URL))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptors: Set<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        interceptors.forEach(builder::addInterceptor)

        // It would be overhead to extract optional dependency in a separate provide-method
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

    @Singleton
    @Provides
    @IntoSet
    fun provideApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl: HttpUrl = originalRequest.url

            val url = originalUrl.newBuilder()
                .addQueryParameter(API_KEY_QUERY_KEY, BuildConfig.API_KEY)
                .build()

            val request = originalRequest.newBuilder()
                .url(url)
                .build()

            chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    @IntoSet
    fun provideConnectivityCheckerInterceptor(
        repository: IsInternetConnectedRepository
    ): Interceptor {
        return Interceptor { chain ->
            if (!repository.isInternetConnected) throw NetworkIsNotAvailableException()

            chain.request()
                .newBuilder()
                .build()
                .let(chain::proceed)
        }
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(Timestamp::class.java, OpenWeatherApiTimestampAdapter())
            .create()
    }
}
