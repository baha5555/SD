package com.example.sd.data.remote

import com.example.sd.domain.aboutMe.AboutMe
import com.example.sd.domain.accounts.Accounts
import com.example.sd.domain.athorization.AuthResponse
import com.example.sd.domain.bits.GetBids
import com.example.sd.domain.bits.bidCategories.GetBidCategories
import com.example.sd.domain.bits.bidOrigins.BidOrigins
import com.example.sd.domain.bits.bidPriorities.GetBidPriorities
import com.example.sd.domain.bits.bidStatus.GetBidsStatus
import com.example.sd.domain.bits.bidStore.BidStore
import com.example.sd.domain.changePassword.ChangePassword
import com.example.sd.domain.contacts.GetContacts
import com.example.sd.domain.dashboard.Dashboard
import com.example.sd.domain.entityNumber.EntityNumber
import com.example.sd.domain.service.serviceItems.ServiceItems
import com.example.sd.domain.service.servicePacts.ServicePacts
import com.example.sd.domain.uploadFile.UploadFile
import com.example.sd.utils.Values
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.util.UUID


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
    ): GetBids

    @GET("contacts/search")
    suspend fun getSearchContact(
        @Header("Authorization") token: String,
        @QueryMap(encoded = true) filters: Map<String, String>
    ): GetContacts

    @GET("accounts")
    suspend fun getAccounts(
        @Header("Authorization") token: String,
        @QueryMap(encoded = true) filters: Map<String, String>
    ): Accounts

    @GET("servicePacts")
    suspend fun getServicePacts(
        @Header("Authorization") token: String,
        @QueryMap(encoded = true) filters: Map<String, String>
    ): ServicePacts

    @GET("serviceItems")
    suspend fun getServiceItems(
        @Header("Authorization") token: String,
        @QueryMap(encoded = true) filters: Map<String, String>
    ): ServiceItems

    @GET("bidCategories")
    suspend fun getBidCategories(
        @Header("Authorization") token: String,
    ): GetBidCategories

    @GET("bidOrigins")
    suspend fun getBidOrigins(
        @Header("Authorization") token: String,
    ): BidOrigins

    @GET("bidStatuses")
    suspend fun getBidStatus(
        @Header("Authorization") token: String,
    ): GetBidsStatus

    @GET("bidPriorities")
    suspend fun getBidPriorities(
        @Header("Authorization") token: String,
    ): GetBidPriorities

    @GET("supportLevels")
    suspend fun getSupportLevels(
        @Header("Authorization") token: String,
    ): GetBidPriorities

    @GET("contacts")
    suspend fun getContacts(
        @Header("Authorization") token: String,
    ): GetContacts

    @GET("uuids")
    suspend fun getUUID(
        @Header("Authorization") token: String,
    ): String


    @Multipart
    @POST("fileCollections")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part,
        @Part("collection_id") collectionId: RequestBody,
        @Part("entity_type") entityType: RequestBody
    ): UploadFile

    @FormUrlEncoded
    @POST("bids")
    suspend fun createBid(
        @Header("Authorization") token: String,
        @Field("id") id: String?,
        @Field("name") name: String,
        @Field("number") number: String,
        @Field("symptoms") symptoms: String?,
        @Field("owner_id") ownerId: String?,
        @Field("response_date") responseDate: String?,
        @Field("solution_date") solutionDate: String?,
        @Field("bid_status_id") bidStatusId: String,
        @Field("bid_priority_id") bidPriorityId: String,
        @Field("bid_origin_id") bidOriginId: String,
        @Field("account_id") accountId: String,
        @Field("contact_id") contactId: String,
        @Field("solution") solution: String?,
        @Field("satisfaction_level_id") satisfactionLevelId: String?,
        @Field("bid_category_id") bidCategoryId: String,
        @Field("response_overdue") responseOverdue: String?,
        @Field("solution_overdue") solutionOverdue: String?,
        @Field("satisfaction_comment") satisfactionComment: String?,
        @Field("reason_delay") reasonDelay: String?,
        @Field("solution_remains") solutionRemains: String?,
        @Field("service_pact_id") servicePactId: String,
        @Field("service_item_id") serviceItemId: String,
        @Field("support_level_id") supportLevelId: String,
        @Field("parent_id") parentId: String?,
        @Field("holder_id") holderId: String?,
        @Field("problem_id") problemId: String?,
        @Field("department_id") departmentId: String?,
        @Field("file_collection") fileCollection: String,
        @Field("feed_collection") feedCollection: String
    ): BidStore

    @FormUrlEncoded
    @POST("entityNumbers/generate")
    suspend fun generateEntityNumber(
        @Header("Authorization") token: String,
        @Field("entity_type") entityType: String
    ): EntityNumber
}