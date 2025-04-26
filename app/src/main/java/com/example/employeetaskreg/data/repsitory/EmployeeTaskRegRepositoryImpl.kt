package com.example.employeetaskreg.data.repsitory

import android.util.Log
import com.example.employeetaskreg.data.api.EmployeeTaskRegApi
import com.example.employeetaskreg.data.api.dto.LoginRequest
import com.example.employeetaskreg.data.api.dto.RegistrationRequest
import com.example.employeetaskreg.domain.model.CompanyWorker
import com.example.employeetaskreg.domain.model.Report
import com.example.employeetaskreg.domain.model.Task
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

    override suspend fun logout() {
        dataStoreManager.clearToken()
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

    override suspend fun getDirectorById(id: Int): EmpTaskRegState<CompanyWorker.Director> {
        return try {
            val token = getTokenFromDataStorage()
            if (token.isEmpty()) {
                return EmpTaskRegState.Failure(Exception("No token found. Please login first."))
            }
            val response = api.getDirectorById("Bearer $token",id.toString())
            EmpTaskRegState.Success(response)
        }catch (e:HttpException){
            Log.e("BAR",e.toString())
            EmpTaskRegState.Failure(Exception("${e.code()} - ${e.message()}"))
        }catch(e:Exception){
            Log.i("PROFILE",e.toString())
            EmpTaskRegState.Failure(Exception("Error during getting director: Check your connection"))
        }
    }

    override suspend fun getEmployeeById(id: Int): EmpTaskRegState<CompanyWorker.Employee> {
        return try {
            val token = getTokenFromDataStorage()
            if (token.isEmpty()) {
                return EmpTaskRegState.Failure(Exception("No token found. Please login first."))
            }
            val response = api.getEmployeeById("Bearer $token",id.toString())
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

    override suspend fun getTaskList(): EmpTaskRegState<List<Task>> {
        return try {
            val token = getTokenFromDataStorage()
            if (token.isEmpty()) {
                return EmpTaskRegState.Failure(Exception("No token found. Please login first."))
            }
            val response = api.getTasks("Bearer $token").sortedBy { it.id }
            EmpTaskRegState.Success(response)
        }catch (e:HttpException){
            Log.e("BAR",e.toString())
            EmpTaskRegState.Failure(Exception("${e.code()} - ${e.message()}"))
        }catch(e:Exception){
            Log.i("PROFILE",e.toString())
            EmpTaskRegState.Failure(Exception("Error during getting tasks: Check your connection"))
        }
    }

    override suspend fun getReportList(): EmpTaskRegState<List<Report>> {
        return try {
            val token = getTokenFromDataStorage()
            if (token.isEmpty()) {
                return EmpTaskRegState.Failure(Exception("No token found. Please login first."))
            }
            val response = api.getReports("Bearer $token").sortedBy { it.id }
            EmpTaskRegState.Success(response)
        }catch (e:HttpException){
            Log.e("BAR",e.toString())
            EmpTaskRegState.Failure(Exception("${e.code()} - ${e.message()}"))
        }catch(e:Exception){
            Log.i("PROFILE",e.toString())
            EmpTaskRegState.Failure(Exception("Error during getting reports: Check your connection"))
        }
    }

    override suspend fun getEmployeesList(): EmpTaskRegState<List<CompanyWorker.Employee>> {
        return try {
            val token = getTokenFromDataStorage()
            if (token.isEmpty()) {
                return EmpTaskRegState.Failure(Exception("No token found. Please login first."))
            }
            val response = api.getEmployees("Bearer $token").sortedBy { it.id }
            EmpTaskRegState.Success(response)
        }catch (e:HttpException){
            Log.e("BAR",e.toString())
            EmpTaskRegState.Failure(Exception("${e.code()} - ${e.message()}"))
        }catch(e:Exception){
            Log.i("PROFILE",e.toString())
            EmpTaskRegState.Failure(Exception("Error during getting employees: Check your connection"))
        }
    }


}