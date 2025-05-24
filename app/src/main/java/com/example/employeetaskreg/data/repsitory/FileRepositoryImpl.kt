package com.example.employeetaskreg.data.repsitory

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.employeetaskreg.domain.repository.FileRepository
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class FileRepositoryImpl(): FileRepository {
    override suspend fun uriToFile(context: Context, uri: Uri): Result<File> {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        if (inputStream == null) {
            Log.e("uriToFile", "Error: Could not open InputStream for URI: $uri")
            return Result.failure(Exception())
        }
        val file: File = createTempFile(context)?: return Result.failure(Exception())
        return try {
            inputStream.use { input ->
                FileOutputStream(file).use { output ->
                    val buffer = ByteArray(4 * 1024)
                    while (true) {
                        val bytes = input.read(buffer)
                        if (bytes < 0) break
                        output.write(buffer, 0, bytes)
                    }
                    output.flush()
                }
            }
            Result.success(file)

        } catch (e: Exception) {
            Log.e("uriToFile", "Error converting URI to file: ${e.message}")
            file.delete()
            Result.failure(e)
        } finally {
            inputStream.close()
        }
    }

    override suspend fun byteArrayToFile(context: Context, byteArray: ByteArray, destinationFile: File): Result<File> {
        return try {
            val outputStream = FileOutputStream(destinationFile)

            outputStream.use { outputStream ->
                outputStream.write(byteArray)
                outputStream.flush()
                Result.success(destinationFile)
            }
        }catch (e: IOException) {
            Log.e("byteArrayToFile", "Error saving ByteArray to file: ${e.message}")
            Result.failure(e)
        }
    }

    private fun createTempFile(context: Context): File? {
        val storageDir: File? = context.cacheDir
        return try {
            File.createTempFile("pdf_", ".pdf", storageDir)
        }catch (e: IOException){
            Log.e("createTempFile", "Error while creating temp file: ${e.message}")
            null
        }
    }
}