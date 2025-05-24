package com.example.employeetaskreg.domain.repository

import android.content.Context
import android.net.Uri
import java.io.File

interface FileRepository {
    suspend fun uriToFile(context: Context,uri: Uri): Result<File>

    suspend fun byteArrayToFile(context: Context, byteArray: ByteArray, destinationFile: File): Result<File>

}