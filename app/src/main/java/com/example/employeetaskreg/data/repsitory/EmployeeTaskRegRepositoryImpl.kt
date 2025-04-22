package com.example.employeetaskreg.data.repsitory

import android.util.Log
import com.example.employeetaskreg.data.api.EmployeeTaskRegApi
import com.example.employeetaskreg.data.api.dto.LoginRequest
import com.example.employeetaskreg.data.api.dto.RegistrationRequest
import com.example.employeetaskreg.domain.model.CompanyWorker
import com.example.employeetaskreg.domain.repository.EmpTaskRegState
import com.example.employeetaskreg.domain.repository.EmployeeTaskRegRepository
import retrofit2.HttpException
import javax.inject.Inject

class EmployeeTaskRegRepositoryImpl @Inject constructor(private val api: EmployeeTaskRegApi) :EmployeeTaskRegRepository {
    override suspend fun login(login: String, password: String): EmpTaskRegState<String> {
        val request = LoginRequest(login,password)
        return try {
            val response = api.login(request).token
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
        val token = "" //Токен из бд
        return try{
            val response = api.getProfile(token)
            EmpTaskRegState.Success(response)
        }catch (e:HttpException){
            EmpTaskRegState.Failure(Exception("${e.code()} - ${e.message()}"))
        }catch(e:Exception){
            Log.i("PROFILE",e.toString())
            EmpTaskRegState.Failure(Exception("Error during login: Check your connection"))
        }
    }
}