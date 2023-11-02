package com.netaphous.testapplication.data

import android.content.Context
import com.netaphous.testapplication.ui.Joke
import com.netaphous.testapplication.ui.JokesDTO
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

@OptIn(ExperimentalSerializationApi::class)
class JokeProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun getJokesFromJson(): List<Joke> = withContext(Dispatchers.IO) {
        val inputStream = context.assets.open("json/jokes.json").buffered()
        val dto = Json.decodeFromStream<JokesDTO>(inputStream)
        delay(3000)
        return@withContext dto.jokes
    }
}