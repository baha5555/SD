package com.example.sd.domain.report.linesReport

import android.util.Log
import com.example.sd.data.remote.AppRepository
import com.example.sd.domain.changePassword.ChangePassword
import com.example.sd.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ReportByLineUseCase @Inject constructor(private val repository: AppRepository) {

    operator fun invoke(firstDate:String,secondDate:String): Flow<Resource<ReportLines>> =
        flow {
            try {
                emit(Resource.Loading<ReportLines>())
                val response: ReportLines = repository.getReportByLine(firstDate,secondDate)
                emit(Resource.Success<ReportLines>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<ReportLines>(
                        e.localizedMessage ?: "Произошла непредвиденная ошибка"
                    )
                )
                Log.d("ChangePassword33", "Trying to authorize22 with: $e")
            } catch (e: IOException) {
                Log.d("ChangePassword3333", "Trying to authori222ze with: $e")
                emit(Resource.Error<ReportLines>("Не удалось связаться с сервером. Проверьте подключение к Интернету."))
            } catch (e: Exception) {
                emit(Resource.Error<ReportLines>("${e.message}"))
            }
        }
}
