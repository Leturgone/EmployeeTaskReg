package com.example.employeetaskreg.di

import com.example.employeetaskreg.data.api.EmployeeTaskRegApi
import com.example.employeetaskreg.data.repsitory.EmployeeTaskRegRepositoryImpl
import com.example.employeetaskreg.domain.repository.EmployeeTaskRegRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideEmployeeTaskRegApi():EmployeeTaskRegApi{
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)

        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS) // Увеличение времени ожидания соединения
            .readTimeout(60, TimeUnit.SECONDS)    // Увеличение времени ожидания чтения данных
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
        return Retrofit
            .Builder().baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(EmployeeTaskRegApi::class.java)
    }
    @Provides
    @Singleton
    fun provideRemoteRepository(api: EmployeeTaskRegApi): EmployeeTaskRegRepository {
        return EmployeeTaskRegRepositoryImpl(api)
    }
}