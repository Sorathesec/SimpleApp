package com.netaphous.testapplication.ui

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Joke(
    @SerialName("icon_url")
    val iconUrl: String,
    val id: String,
    val url: String,
    val value: String,
)