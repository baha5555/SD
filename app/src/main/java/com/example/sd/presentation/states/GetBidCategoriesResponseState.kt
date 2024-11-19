package com.example.sd.presentation.states

import com.example.sd.domain.aboutMe.AboutMe
import com.example.sd.domain.bits.bidCategories.GetBidCategories

data class GetBidCategoriesResponseState (
    val isLoading: Boolean = false,
    var response: GetBidCategories? = null,
    val error: String = ""
)