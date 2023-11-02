package com.netaphous.testapplication.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class PrimerDTO(
    val screens: List<Screen>,
    val events: List<Event>,
)

@Serializable
data class Event(
    val type: String,
    val actions: List<Action>
)

@Serializable
data class Action(
    val type: String,
    val data: JsonObject,
)

@Serializable
data class Screen(
    val id: String,
    val components: List<Component>
)

@Serializable
sealed interface Component

@Serializable
@SerialName("Container")
data class Container(
    val components: List<Component>
): Component

@Serializable
@SerialName("Text")
data class Text(
    val text: String
): Component

@Serializable
@SerialName("Input")
data class Input(
    val id: String,
    val hint: String,
): Component

@Serializable
@SerialName("Button")
data class Button(
    val text: String,
    val onClick: String
): Component