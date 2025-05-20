package com.example.employeetaskreg.domain.repository

import android.content.Context
import android.net.Uri
import java.io.File

interface FileRepository {
    fun uriToFile(context: Context,uri: Uri): File?
}