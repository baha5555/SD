package com.example.sd.domain.uploadFile

import com.example.sd.data.remote.ApplicationApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun UploadFileUseCase(
    filePath: String,
    collectionId: String,
    entityType: String,
    apiService: ApplicationApi,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val file = File(filePath)
    val fileRequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
    val filePart = MultipartBody.Part.createFormData("file", file.name, fileRequestBody)

    val collectionIdPart = collectionId.toRequestBody("text/plain".toMediaTypeOrNull())
    val entityTypePart = entityType.toRequestBody("text/plain".toMediaTypeOrNull())

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = apiService.uploadFile(filePart, collectionIdPart, entityTypePart)
            if (response!= null) {
                onSuccess()
            } else {
                onError("Ошибка: ${response}")
            }
        } catch (e: Exception) {
            onError("Ошибка: ${e.message}")
        }
    }
}