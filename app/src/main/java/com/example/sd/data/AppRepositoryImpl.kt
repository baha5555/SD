package com.example.sd.data

import com.example.sd.data.remote.AppRepository
import com.example.sd.data.remote.ApplicationApi
import com.example.sd.domain.athorization.AuthResponse

import com.example.sd.domain.bits.GetBids
import com.example.sd.data.preference.CustomPreference
import com.example.sd.domain.aboutMe.AboutMe
import com.example.sd.domain.accounts.Accounts
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
import com.example.sd.domain.contacts.contactType.ContactType
import com.example.sd.domain.knowledgeBases.GetKnowledgeBases
import com.example.sd.domain.knowledgeBases.knowledgeBasesDetail.KnowledgeBasesDetail
import com.example.sd.domain.knowledgeBases.knowledgeBasesType.KnowledgeBasesType


class AppRepositoryImpl(
    private val api: ApplicationApi,
    private val prefs: CustomPreference
) : AppRepository {
    override suspend fun authorization(name: String, password: String): AuthResponse =
        api.authorization(name, password)

    override suspend fun getBids(page: Int): GetBids = api.getBids(prefs.getAccessToken(), page)
    override suspend fun getDashboard(): Dashboard = api.getDashboard(prefs.getAccessToken())
    override suspend fun aboutMe(): AboutMe = api.aboutMe(prefs.getAccessToken())
    override suspend fun changePassword(
        userId: String,
        password: String,
        password_confirmation: String
    ): ChangePassword =
        api.changePassword(prefs.getAccessToken(), userId, password, password_confirmation)

    override suspend fun getFilteredData(filters: Map<String, String>): GetBids =
        api.getFilteredData(prefs.getAccessToken(), filters)

    override suspend fun getSearchContact(filters: Map<String, String>): GetContacts =
        api.getSearchContact(prefs.getAccessToken(), filters)

    override suspend fun getBidCategories(): GetBidCategories =
        api.getBidCategories(prefs.getAccessToken())

    override suspend fun getBidStatus(): GetBidsStatus = api.getBidStatus(prefs.getAccessToken())
    override suspend fun getBidPriorities(): GetBidPriorities =
        api.getBidPriorities(prefs.getAccessToken())

    override suspend fun getSupportLevels(): GetBidPriorities =
        api.getSupportLevels(prefs.getAccessToken())

    override suspend fun getContacts(): GetContacts = api.getContacts(prefs.getAccessToken())
    override suspend fun getUUID(): String = api.getUUID(prefs.getAccessToken())

    override suspend fun getBidOrigins(): BidOrigins = api.getBidOrigins(prefs.getAccessToken())
    override suspend fun getAccounts(filters: Map<String, String>): Accounts =
        api.getAccounts(prefs.getAccessToken(), filters)

    override suspend fun getServicePacts(filters: Map<String, String>): ServicePacts =
        api.getServicePacts(prefs.getAccessToken(), filters)

    override suspend fun getServiceItems(filters: Map<String, String>): ServiceItems =
        api.getServiceItems(prefs.getAccessToken(), filters)

    override suspend fun createBid(
        id: String?,
        name: String,
        number: String,
        symptoms: String?,
        ownerId: String?,
        responseDate: String?,
        solutionDate: String?,
        bidStatusId: String,
        bidPriorityId: String,
        bidOriginId: String,
        accountId: String,
        contactId: String,
        solution: String?,
        satisfactionLevelId: String?,
        bidCategoryId: String,
        responseOverdue: String?,
        solutionOverdue: String?,
        satisfactionComment: String?,
        reasonDelay: String?,
        solutionRemains: String?,
        servicePactId: String,
        serviceItemId: String,
        supportLevelId: String,
        parentId: String?,
        holderId: String?,
        problemId: String?,
        departmentId: String?,
        fileCollection: String,
        feedCollection: String
    ): BidStore = api.createBid(
        prefs.getAccessToken(),
        id,
        name,
        number,
        symptoms,
        ownerId,
        responseDate,
        solutionDate,
        bidStatusId,
        bidPriorityId,
        bidOriginId,
        accountId,
        contactId,
        solution,
        satisfactionLevelId,
        bidCategoryId,
        responseOverdue,
        solutionOverdue,
        satisfactionComment,
        reasonDelay,
        solutionRemains,
        servicePactId,
        serviceItemId,
        supportLevelId,
        parentId,
        holderId,
        problemId,
        departmentId,
        fileCollection,
        feedCollection
    )

    override suspend fun generateEntityNumber(entityType: String): EntityNumber  = api.generateEntityNumber(prefs.getAccessToken(), entityType)
    override suspend fun getContactTypes(): ContactType = api.getContactTypes(prefs.getAccessToken())
    override suspend fun getKnowledgeBaseTypes(): KnowledgeBasesType = api.getKnowledgeBaseTypes(prefs.getAccessToken())

    override suspend fun getKnowledgeBases(filters: Map<String, String>): GetKnowledgeBases  = api.getKnowledgeBases(prefs.getAccessToken(),filters)
    override suspend fun getKnowledgeBasesDetail(knowledgeBaseId: String): KnowledgeBasesDetail  = api.getKnowledgeBasesDetail(prefs.getAccessToken(),knowledgeBaseId)}