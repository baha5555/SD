package com.example.sd.data.remote

import com.example.sd.domain.aboutMe.AboutMe
import com.example.sd.domain.athorization.AuthResponse

import com.example.sd.domain.bits.GetBids
import com.example.sd.domain.changePassword.ChangePassword
import com.example.sd.domain.dashboard.Dashboard
import com.example.sd.domain.filterResponse.FilterResponse


interface AppRepository {
    suspend fun authorization(name: String, password: String): AuthResponse
    suspend fun getBids(page: Int): GetBids
    suspend fun getDashboard(): Dashboard
    suspend fun aboutMe():AboutMe
    suspend fun changePassword( userId: String,password: String,password_confirmation: String): ChangePassword

    suspend fun getFilteredData(filters:  Map<String, String>): FilterResponse

}

