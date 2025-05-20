package com.example.employeetaskreg.data.repsitory

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.employeetaskreg.domain.repository.FileRepository
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class FileRepositoryImpl(): FileRepository {
    override fun uriToFile(context: Context, uri: Uri): File? {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val file: File? = createTempFile(context)
        return try {
            inputStream?.use { input ->
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
            file
        } catch (e: Exception) {
            Log.e("FileRepository", "Error converting URI to file: ${e.message}")
            file?.delete()
            null
        } finally {
            inputStream?.close()
        }
    }

    private fun createTempFile(context: Context): File? {
        //  val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val storageDir: File? = context.cacheDir
        return try {
            File.createTempFile("pdf_", ".pdf", storageDir)
        }catch (e:java.io.IOException){
            null
        }
    }
}