package com.example.submission_intermediete_dicoding.util.helper

import android.content.Context
import android.os.Environment
import java.io.File

fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "JPEG_${System.currentTimeMillis()}_",
        ".jpg",
        storageDir
    )
}