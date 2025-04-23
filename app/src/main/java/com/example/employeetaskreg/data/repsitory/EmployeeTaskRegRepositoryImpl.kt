package com.example.employeetaskreg.data.repsitory

import android.util.Log
import com.example.employeetaskreg.data.api.EmployeeTaskRegApi
import com.example.employeetaskreg.data.api.dto.LoginRequest
import com.example.employeetaskreg.data.api.dto.RegistrationRequest
import com.example.employeetaskreg.domain.model.CompanyWorker
import com.example.employeetaskreg.domain.repository.EmpTaskRegState
import com.example.employeetaskreg.domain.repository.EmployeeTaskRegRepository
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import javax.inject.Inject

class EmployeeTaskRegRepositoryImpl @Inject constructor(private val api: EmployeeTaskRegApi,
                                                        private val dataStoreManager: DataStoreManager ) :EmployeeTaskRegRepository {
    override suspend fun getTokenFromDataStorage(): String {
        return dataStoreManager.tokenFlow.first().toString()
    }


    override suspend fun login(login: String, password: String): EmpTaskRegState<String> {
        val request = LoginRequest(login,password)
        return try {
            val response = api.login(request).token
            dataStoreManager.storeToken(response)
            Log.i("TOKEN",response)
            EmpTaskRegState.Success(response)
        }catch (e: HttpException) {
            EmpTaskRegState.Failure(Exception("${e.code()} - ${e.message()}"))
        }
        catch (e: Exception) {
            Log.i("TOKEN",e.toString())
            EmpTaskRegState.Failure(Exception("Error during login: Check your connection"))
        }
    }

    override suspend fun register(login: String, password: String, name: String, dirName: String): EmpTaskRegState<String> {
        val request = RegistrationRequest(login,password,name, dirName)
        return try {
            val response = api.register(request).token
            dataStoreManager.storeToken(response)
            Log.i("TOKEN",response)
            EmpTaskRegState.Success(response)
        }catch (e: HttpException) {
            EmpTaskRegState.Failure(Exception("${e.code()} - ${e.message()}"))
        }
        catch (e: Exception) {
            Log.i("TOKEN",e.toString())
            EmpTaskRegState.Failure(Exception("Error during login: Check your connection"))
        }
    }

    override suspend fun getProfile(): EmpTaskRegState<CompanyWorker> {
        return try{
            val token = getTokenFromDataStorage()
            if (token.isEmpty()) {
                return EmpTaskRegState.Failure(Exception("No token found. Please login first."))
            }
            val response = api.getProfile("Bearer $token")
            EmpTaskRegState.Success(response)
        }catch (e:HttpException){
            Log.e("BAR",e.toString())
            EmpTaskRegState.Failure(Exception("${e.code()} - ${e.message()}"))
        }catch(e:Exception){
            Log.i("PROFILE",e.toString())
            EmpTaskRegState.Failure(Exception("Error during login: Check your connection"))
        }
    }

    override suspend fun getDirectorNameById(id: Int): EmpTaskRegState<String> {
        return try {
            val token = getTokenFromDataStorage()
            if (token.isEmpty()) {
                return EmpTaskRegState.Failure(Exception("No token found. Please login first."))
            }
            val response = api.getDirectorById("Bearer $token",id.toString()).name
            EmpTaskRegState.Success(response)
        }catch (e:HttpException){
            Log.e("BAR",e.toString())
            EmpTaskRegState.Failure(Exception("${e.code()} - ${e.message()}"))
        }catch(e:Exception){
            Log.i("PROFILE",e.toString())
            EmpTaskRegState.Failure(Exception("Error during getting director: Check your connection"))
        }
    }

    override suspend fun getTaskCount(): EmpTaskRegState<Int> {
        return try {
            val token = getTokenFromDataStorage()
            if (token.isEmpty()) {
                return EmpTaskRegState.Failure(Exception("No token found. Please login first."))
            }
            val response = api.getTaskCount("Bearer $token")
            EmpTaskRegState.Success(response)
        }catch (e:HttpException){
            Log.e("BAR",e.toString())
            EmpTaskRegState.Failure(Exception("${e.code()} - ${e.message()}"))
        }catch(e:Exception){
            Log.i("PROFILE",e.toString())
            EmpTaskRegState.Failure(Exception("Error during getting task count: Check your connection"))
        }
    }


}