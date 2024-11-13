package com.example.sd.data

import com.example.sd.data.remote.AppRepository
import com.example.sd.data.remote.ApplicationApi
import com.example.sd.domain.athorization.AuthResponse

import com.example.sd.domain.bits.GetBids
import com.example.sd.data.preference.CustomPreference
import com.example.sd.domain.aboutMe.AboutMe
import com.example.sd.domain.changePassword.ChangePassword
import com.example.sd.domain.dashboard.Dashboard


class AppRepositoryImpl(
    private val api: ApplicationApi,
    private val prefs: CustomPreference
) : AppRepository {
    override suspend fun authorization(name: String, password: String): AuthResponse =
        api.authorization(name, password)

    override suspend fun getBids(page: Int): GetBids = api.getBids(prefs.getAccessToken(), page)
    override suspend fun getDashboard(): Dashboard = api.getDashboard(prefs.getAccessToken())
    override suspend fun aboutMe(): AboutMe = api.aboutMe(prefs.getAccessToken())
    override suspend fun changePassword( userId: String,password: String,password_confirmation: String): ChangePassword  = api.changePassword(prefs.getAccessToken(), userId,password,password_confirmation)
}