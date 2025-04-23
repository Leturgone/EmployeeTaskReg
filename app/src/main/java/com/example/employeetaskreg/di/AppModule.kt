package com.example.employeetaskreg.di

import android.content.Context
import com.example.employeetaskreg.data.api.CompanyWorkerDeserializer
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
import com.example.employeetaskreg.data.repsitory.DataStoreManager
import com.example.employeetaskreg.domain.model.CompanyWorker
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext


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
        val gson = GsonBuilder()
            .registerTypeAdapter(CompanyWorker::class.java, CompanyWorkerDeserializer())
            .create()
        return Retrofit
            .Builder().baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(EmployeeTaskRegApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }
    @Provides
    @Singleton
    fun provideRemoteRepository(api: EmployeeTaskRegApi,dataStoreManager: DataStoreManager): EmployeeTaskRegRepository {
        return EmployeeTaskRegRepositoryImpl(api,dataStoreManager)
    }
}