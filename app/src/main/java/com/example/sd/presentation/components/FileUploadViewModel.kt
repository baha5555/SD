package com.example.sd.presentation.components

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.AndroidViewModel
import com.example.sd.data.remote.ApplicationApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class FileUploadViewModel @Inject constructor(
    application: Application,
    private val applicationApi: ApplicationApi,
) : AndroidViewModel(application) {

    private val _selectedFile = MutableStateFlow<File?>(null)
    val selectedFile: StateFlow<File?> = _selectedFile

    private val _fileDescription = MutableStateFlow("")
    val fileDescription: StateFlow<String> = _fileDescription

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading

    private val _uploadMessage = MutableStateFlow<String?>(null)
    val uploadMessage: StateFlow<String?> = _uploadMessage

    // Метод для выбора файла
    fun onFileSelected(uri: Uri, contentResolver: ContentResolver) {
        // Преобразование URI в файл и получение его имени и размера
        val file = getFileFromUri(uri, contentResolver)
        if (file != null) {
            if (file.length() > 500 * 1024) { // Максимальный размер 500 КБ
                _uploadMessage.value = "Файл слишком большой (макс. 500 КБ)"
            } else {
                _selectedFile.value = file
                _uploadMessage.value = null
            }
        } else {
            _uploadMessage.value = "Ошибка при чтении файла"
        }
    }

    // Получение имени и размера файла из URI
    private fun getFileFromUri(uri: Uri, contentResolver: ContentResolver): File? {
        return try {
            val fileName = getFileName(uri, contentResolver)
            val tempFile = File.createTempFile("upload_", fileName)

            // Копируем содержимое из URI в временный файл
            contentResolver.openInputStream(uri)?.use { inputStream ->
                tempFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            tempFile
        } catch (e: Exception) {
            null
        }
    }

    // Получение имени файла по URI
    private fun getFileName(uri: Uri, contentResolver: ContentResolver): String {
        var name = "unknown"
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1 && cursor.moveToFirst()) {
                name = cursor.getString(nameIndex)
            }
        }
        return name
    }


    suspend fun uploadFile() {
        val file = _selectedFile.value ?: return
        val description = _fileDescription.value

        _isUploading.value = true
        _uploadMessage.value = null

        try {
            val filePart = MultipartBody.Part.createFormData(
                "file", file.name, RequestBody.create("application/octet-stream".toMediaTypeOrNull(), file)
            )
            val collectionId = RequestBody.create("text/plain".toMediaTypeOrNull(), UUID.randomUUID().toString())
            val entityType = RequestBody.create("text/plain".toMediaTypeOrNull(), "document")

            // Отправка файла на сервер
            val response = applicationApi.uploadFile(filePart, collectionId, entityType)

            // Сообщение о успешной загрузке
            _uploadMessage.value = "Файл успешно загружен: ${response.data}"

            // Сбрасываем состояние, чтобы подготовиться к следующей загрузке
            _selectedFile.value = null
        } catch (e: Exception) {
            _uploadMessage.value = "Ошибка загрузки файла: ${e.message}"
        } finally {
            _isUploading.value = false
        }
    }
}