package com.example.employeetaskreg.domain.repository

import com.example.employeetaskreg.domain.model.CompanyWorker

interface DirectorRepository {
    suspend fun getDirectorById(id: Int):EmpTaskRegState<CompanyWorker.Director>
}