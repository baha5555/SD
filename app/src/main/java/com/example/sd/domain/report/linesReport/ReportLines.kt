package com.example.sd.domain.report.linesReport


import com.google.gson.annotations.SerializedName

class ReportLines : ArrayList<ReportLines.ReportLinesItem>(){
    data class ReportLinesItem(
        @SerializedName("all_done")
        val allDone: Int,
        @SerializedName("delay")
        val delay: Int,
        @SerializedName("done_after_time")
        val doneAfterTime: Int,
        @SerializedName("done_in_time")
        val doneInTime: Int,
        @SerializedName("level")
        val level: String,
        @SerializedName("total_bids")
        val totalBids: Int,
        @SerializedName("undone")
        val undone: Int,
        @SerializedName("undone_percent")
        val undonePercent: String
    )
}