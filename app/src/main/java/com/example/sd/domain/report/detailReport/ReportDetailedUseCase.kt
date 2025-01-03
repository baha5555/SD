package com.example.sd.domain.report.detailReport

import android.util.Log
import com.example.sd.data.remote.AppRepository
import com.example.sd.domain.changePassword.ChangePassword
import com.example.sd.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ReportDetailedUseCase @Inject constructor(private val repository: AppRepository) {

    operator fun invoke(firstDate:String,secondDate:String,depCode:String): Flow<Resource<DetailReport>> =
        flow {
            try {
                emit(Resource.Loading<DetailReport>())
                // Передаем map в метод authorization
                val response: DetailReport = repository.getReportDetailed(firstDate,secondDate,depCode)
                emit(Resource.Success<DetailReport>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<DetailReport>(
                        e.localizedMessage ?: "Произошла непредвиденная ошибка"
                    )
                )
                Log.d("ChangePassword33", "Trying to authorize22 with: $e")
            } catch (e: IOException) {
                Log.d("ChangePassword3333", "Trying to authori222ze with: $e")
                emit(Resource.Error<DetailReport>("Не удалось связаться с сервером. Проверьте подключение к Интернету."))
            } catch (e: Exception) {
                emit(Resource.Error<DetailReport>("${e.message}"))
            }
        }
}
