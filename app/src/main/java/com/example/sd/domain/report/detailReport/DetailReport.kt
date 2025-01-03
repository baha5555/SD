package com.example.sd.domain.report.detailReport


import com.google.gson.annotations.SerializedName

data class DetailReport(
    @SerializedName("data")
    val `data`: List<Data>
) {
    data class Data(
        @SerializedName("code")
        val code: String,
        @SerializedName("data")
        val `data`: Data,
        @SerializedName("name")
        val name: String
    ) {
        data class Data(
            @SerializedName("canceled")
            val canceled: Int,
            @SerializedName("completed_all")
            val completedAll: Int,
            @SerializedName("completed_on_time")
            val completedOnTime: Int,
            @SerializedName("in_work")
            val inWork: Int,
            @SerializedName("not_completed_on_time")
            val notCompletedOnTime: Int,
            @SerializedName("prc_failure")
            val prcFailure: Double,
            @SerializedName("total")
            val total: Int
        )
    }
}