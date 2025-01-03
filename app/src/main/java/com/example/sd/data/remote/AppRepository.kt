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
import com.example.sd.domain.castas.Castas
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
import com.example.sd.domain.report.detailReport.DetailReport
import com.example.sd.domain.report.linesReport.ReportLines


interface AppRepository {
    suspend fun authorization(name: String, password: String): AuthResponse
    suspend fun getBids(page: Int): GetBids
    suspend fun getDashboard(): Dashboard
    suspend fun aboutMe(): AboutMe
    suspend fun changePassword(
        userId: String,
        password: String,
        password_confirmation: String
    ): ChangePassword

    suspend fun getFilteredData(filters: Map<String, String>): GetBids
    suspend fun getSearchContact(filters: Map<String, String>,page: Int): GetContacts
    suspend fun getBidCategories(): GetBidCategories
    suspend fun getBidStatus(): GetBidsStatus
    suspend fun getBidPriorities(): GetBidPriorities
    suspend fun getSupportLevels(): GetBidPriorities
    suspend fun getContacts(): GetContacts
    suspend fun getUUID(): String
    suspend fun getBidOrigins(): BidOrigins
    suspend fun getAccounts(filters: Map<String, String>): Accounts
    suspend fun getServicePacts(filters: Map<String, String>): ServicePacts
    suspend fun getServiceItems(page: Int): ServiceItems
    suspend fun createBid(
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
    ): BidStore

    suspend fun generateEntityNumber(entityType: String): EntityNumber

    suspend fun getContactTypes(): ContactType
    suspend fun getKnowledgeBaseTypes(): KnowledgeBasesType
    suspend fun getKnowledgeBases(filters: Map<String, String>): GetKnowledgeBases
    suspend fun getKnowledgeBasesDetail(knowledgeBaseId: String): KnowledgeBasesDetail
    suspend fun getCastas(): Castas
    suspend fun getReportDetailed(firstDate:String,secondDate:String,depCode:String): DetailReport
    suspend fun getReportByLine(firstDate:String,secondDate:String): ReportLines
}

