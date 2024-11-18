package com.example.sd.data.remote

import com.example.sd.domain.aboutMe.AboutMe
import com.example.sd.domain.athorization.AuthResponse

import com.example.sd.domain.bits.GetBids
import com.example.sd.domain.changePassword.ChangePassword
import com.example.sd.domain.dashboard.Dashboard
import com.example.sd.domain.filterResponse.FilterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap


interface ApplicationApi {
    @FormUrlEncoded
    @POST("login")
    suspend fun authorization(
        @Field("name") name: String,
        @Field("password") password: String
    ): AuthResponse


    @GET("bids")
    suspend fun getBids(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ): GetBids

    @GET("reports/dashboard")
    suspend fun getDashboard(
        @Header("Authorization") token: String,
    ): Dashboard

    @POST("me")
    suspend fun aboutMe(
        @Header("Authorization") token: String,
    ): AboutMe

    @FormUrlEncoded
    @POST("users/{userId}/changePassword")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String,
    ): ChangePassword


    @GET("bids/search")
    suspend fun getFilteredData(
        @Header("Authorization") token: String,
        @QueryMap(encoded = true) filters: Map<String, String>
    ): FilterResponse
}