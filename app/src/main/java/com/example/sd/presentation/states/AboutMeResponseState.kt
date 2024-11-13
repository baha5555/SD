package com.example.sd.presentation.states

import com.example.sd.domain.aboutMe.AboutMe

data class AboutMeResponseState (
    val isLoading: Boolean = false,
    var response: AboutMe? = null,
    val error: String = ""
)