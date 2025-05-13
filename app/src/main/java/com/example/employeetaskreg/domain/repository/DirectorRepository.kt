package com.example.employeetaskreg.domain.repository

import com.example.employeetaskreg.domain.model.CompanyWorker

interface DirectorRepository {
    suspend fun getDirectorById(id: Int, authToken:String):Result<CompanyWorker.Director>
}