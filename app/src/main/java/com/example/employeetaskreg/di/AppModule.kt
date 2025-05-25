package com.example.employeetaskreg.di

import android.content.Context
import com.example.employeetaskreg.data.api.CompanyWorkerDeserializer
import com.example.employeetaskreg.data.api.EmployeeTaskRegApi
import com.example.employeetaskreg.data.repsitory.AuthRepositoryImpl
import com.example.employeetaskreg.data.repsitory.DataStoreManager
import com.example.employeetaskreg.data.repsitory.DirectorRepositoryImpl
import com.example.employeetaskreg.data.repsitory.EmployeeRepositoryImpl
import com.example.employeetaskreg.data.repsitory.FileRepositoryImpl
import com.example.employeetaskreg.data.repsitory.ProfileRepositoryImpl
import com.example.employeetaskreg.data.repsitory.ReportRepositoryImpl
import com.example.employeetaskreg.data.repsitory.SearchHistoryRepositoryImpl
import com.example.employeetaskreg.data.repsitory.TaskRepositoryImpl
import com.example.employeetaskreg.data.repsitory.ThemeRepositoryImpl
import com.example.employeetaskreg.domain.model.CompanyWorker
import com.example.employeetaskreg.domain.repository.AuthRepository
import com.example.employeetaskreg.domain.repository.DirectorRepository
import com.example.employeetaskreg.domain.repository.EmployeeRepository
import com.example.employeetaskreg.domain.repository.FileRepository
import com.example.employeetaskreg.domain.repository.ProfileRepository
import com.example.employeetaskreg.domain.repository.ReportRepository
import com.example.employeetaskreg.domain.repository.SearchHistoryRepository
import com.example.employeetaskreg.domain.repository.TaskRepository
import com.example.employeetaskreg.domain.repository.ThemeRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideAuthRepository(api: EmployeeTaskRegApi,dataStoreManager: DataStoreManager):AuthRepository{
        return AuthRepositoryImpl(api, dataStoreManager)
    }

    @Provides
    @Singleton
    fun provideDirectorRepository(api: EmployeeTaskRegApi):DirectorRepository{
        return DirectorRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideEmployeeRepository(api: EmployeeTaskRegApi): EmployeeRepository {
        return EmployeeRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(api: EmployeeTaskRegApi): ProfileRepository {
        return ProfileRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(api: EmployeeTaskRegApi): TaskRepository {
        return TaskRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideReportRepository(api: EmployeeTaskRegApi): ReportRepository {
        return ReportRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideFileRepository(): FileRepository {
        return FileRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideSearchHistoryRepository(dataStoreManager: DataStoreManager): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(dataStoreManager)
    }

    @Provides
    @Singleton
    fun provideThemeRepository(dataStoreManager: DataStoreManager):ThemeRepository{
        return ThemeRepositoryImpl(dataStoreManager)
    }
}