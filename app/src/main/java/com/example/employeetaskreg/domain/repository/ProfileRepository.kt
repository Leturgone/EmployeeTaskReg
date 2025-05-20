package com.example.employeetaskreg.domain.repository

import com.example.employeetaskreg.domain.model.CompanyWorker

interface ProfileRepository {

    suspend fun getProfile(authToken:String):Result<CompanyWorker>
}