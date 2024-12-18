package com.example.sd.presentation.authorization

import android.app.Application
import android.content.Context
import android.os.Vibrator
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.sd.utils.Resource
import com.example.sd.domain.athorization.AuthResponse
import com.example.sd.domain.athorization.AuthUseCase
import com.example.sd.domain.bits.BidsUseCase
import com.example.sd.data.preference.CustomPreference
import com.example.sd.domain.aboutMe.AboutMe
import com.example.sd.domain.aboutMe.AboutMeUseCase
import com.example.sd.domain.bits.Data
import com.example.sd.domain.changePassword.ChangePassword
import com.example.sd.domain.changePassword.ChangePasswordUseCase
import com.example.sd.presentation.states.AboutMeResponseState
import com.example.sd.presentation.states.AuthResponseState
import com.example.sd.presentation.states.BidsResponseState
import com.example.sd.presentation.states.ChangePasswordResponseState
import com.example.sd.presentation.states.GetContactsResponseState
import com.example.sd.utils.Values.DEPARTAMENT
import com.example.sd.utils.Values.DEPARTAMENTID
import com.example.sd.utils.Values.FIO
import com.example.sd.utils.Values.ROLES
import com.example.sd.utils.Values.USERID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    application: Application,
    private val authUseCase: AuthUseCase,
    private val bidsUseCase: BidsUseCase,
    private val aboutMeUseCase: AboutMeUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val prefs: CustomPreference,

) : AndroidViewModel(application) {


    private val _selectedOrder = mutableStateOf(Data())
    val selectedOrder: State<Data> get() = _selectedOrder


    val context get() = getApplication<Application>()
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    private val _stateAuth = mutableStateOf(AuthResponseState())
    val stateAuth: State<AuthResponseState> = _stateAuth

    private val _stateChangePassword = mutableStateOf(ChangePasswordResponseState())
    val stateChangePassword: State<ChangePasswordResponseState> = _stateChangePassword

    private val _stateAboutMe = mutableStateOf(AboutMeResponseState())
    val stateAboutMe: State<AboutMeResponseState> = _stateAboutMe



    private val _stateContacts = mutableStateOf(GetContactsResponseState())
    val stateContacts: State<GetContactsResponseState> = _stateContacts

    private val _stateBids = mutableStateOf(BidsResponseState())
    val stateBids: State<BidsResponseState> = _stateBids


    fun paging(pageSize: Int): Flow<PagingData<Data>> {
        return try {

            bidsUseCase.invoke(pageSize)
                .cachedIn(viewModelScope)
        } catch (e: Exception) {
            throw Exception("Ошибка при загрузке данных: ${e.message}")
        }
    }

    fun authorization(name: String, password: String, success: (Boolean, String?) -> Unit) {

        authUseCase.invoke(name, password).onEach { result: Resource<AuthResponse> ->
            when (result) {
                is Resource.Success -> {
                    try {
                        val response: AuthResponse? = result.data
                        _stateAuth.value = AuthResponseState(response = response)
                        Log.d("TariffsResponse", "AuthResponse->\n ${_stateAuth.value}")
                        prefs.setAccessToken("Bearer ${response?.access_token}")
                        success.invoke(true, null)


                    } catch (e: Exception) {
                        Log.d("Exception", "${e.message} Exception")
                        success.invoke(false, e.message)

                    }
                }

                is Resource.Error -> {
                    Log.d("TariffsResponse", "AuthResponseError->\n ${result.message}")
                    _stateAuth.value = AuthResponseState(error = "${result.message}")

                    // Вернуть ошибку авторизации
                    success.invoke(false, "Неверный логин или пароль")
                }

                is Resource.Loading -> {
                    _stateAuth.value = AuthResponseState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun changePassword(
        userId: String,
        password: String,
        password_confirmation: String,
        success: (Boolean, String?) -> Unit
    ) {
        changePasswordUseCase.invoke(userId, password, password_confirmation)
            .onEach { result: Resource<ChangePassword> ->
                when (result) {
                    is Resource.Success -> {
                        try {
                            val response: ChangePassword? = result.data
                            _stateChangePassword.value =
                                ChangePasswordResponseState(response = response)
                            Log.d(
                                "ChangePasswordResponse",
                                "ChangePasswordResponse->\n ${_stateAuth.value}"
                            )
                            success.invoke(true, null)
                        } catch (e: Exception) {
                            Log.d("Exception", "${e.message} Exception")
                            success.invoke(false, e.message)
                        }
                    }

                    is Resource.Error -> {
                        Log.d(
                            "ChangePasswordResponse",
                            "ChangePasswordResponseError->\n ${result.message}"
                        )
                        _stateChangePassword.value =
                            ChangePasswordResponseState(error = "${result.message}")
                        success.invoke(false, "${result.message}")
                    }

                    is Resource.Loading -> {
                        _stateChangePassword.value = ChangePasswordResponseState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun getAboutMe() {

        aboutMeUseCase.invoke().onEach { result: Resource<AboutMe> ->
            when (result) {
                is Resource.Success -> {
                    try {
                        val response: AboutMe? = result.data
                        _stateAboutMe.value = AboutMeResponseState(response = response)
                        USERID.value = _stateAboutMe.value.response?.id.toString()
                        _stateAboutMe.value.response?.roles!!.forEach { ROLES.value =it.name}
                        DEPARTAMENT.value = _stateAboutMe.value.response?.department_id?.name.toString()
                        DEPARTAMENTID.value = _stateAboutMe.value.response?.department_id?.id.toString()
                        FIO.value = _stateAboutMe.value.response?.fio.toString()

                        Log.d("AboutMeResponse22223", "AboutMeResponse->\n ${_stateAboutMe.value}")
                        Log.d("AboutMeResponse22223", "AboutMeResponse->\n ${_stateAboutMe.value.response!!.department_id.name}")
                    } catch (e: Exception) {
                        Log.d("Exception", "${e.message} Exception")
                    }
                }

                is Resource.Error -> {
                    Log.e("AboutMeResponse", "AboutMeResponseError->\n ${result.message}")
                    _stateAboutMe.value = AboutMeResponseState(error = "${result.message}")
                }

                is Resource.Loading -> {
                    _stateAboutMe.value = AboutMeResponseState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }



    fun updateSelectedOrder(order: Data) {
        Log.e("it22222", "it22222->\n ${order}")
        _selectedOrder.value = order
        Log.e("it22222222", "it22222222->\n ${_selectedOrder.value}")
        Log.e("it22222222", "it2222222222222222222->\n ${selectedOrder.value}")
    }
}