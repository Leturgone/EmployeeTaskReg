package com.example.employeetaskreg.data.repsitory

import android.util.Log
import com.example.employeetaskreg.data.api.EmployeeTaskRegApi
import com.example.employeetaskreg.data.api.dto.LoginRequest
import com.example.employeetaskreg.data.api.dto.RegistrationRequest
import com.example.employeetaskreg.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val api: EmployeeTaskRegApi,
                                             private val dataStoreManager: DataStoreManager ): AuthRepository {
    override suspend fun getTokenFromDataStorage(): Result<String> {
        return try {
            val token = dataStoreManager.tokenFlow.first().toString()
            if (token.isEmpty()){
                Result.failure<Exception>(Exception())
            }
            Result.success(token)
        }catch (e:Exception){
            Result.failure(e)
        }

    }

    override suspend fun login(login: String, password: String): Result<String> {
        val request = LoginRequest(login,password)
        return try {
            val response = api.login(request).token
            dataStoreManager.storeToken(response)
            Log.i("login",response)
            Result.success(response)
        }catch (e: HttpException) {
            Result.failure(e)
        }
        catch (e: Exception) {
            Log.i("login",e.toString())
            Result.failure(e)
        }
    }

    override suspend fun register(login: String, password: String, name: String, dirName: String): Result<String> {
        val request = RegistrationRequest(login,password,name, dirName)
        return try {
            val response = api.register(request).token
            dataStoreManager.storeToken(response)
            Log.i("register",response)
            Result.success(response)
        }catch (e: HttpException) {
            Result.failure(e)
        }
        catch (e: Exception) {
            Log.i("register",e.toString())
            Result.failure(e)
        }
    }

    override suspend fun logout():Result<Unit> {
        return try {
            val res = dataStoreManager.clearToken()
            Result.success(res)
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}