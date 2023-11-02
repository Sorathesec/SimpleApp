package com.netaphous.testapplication.ui

import kotlinx.serialization.Serializable

@Serializable
data class JokesDTO(
    val jokes: List<Joke>
)