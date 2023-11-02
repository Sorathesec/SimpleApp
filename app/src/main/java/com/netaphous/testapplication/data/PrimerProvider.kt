package com.netaphous.testapplication.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import javax.inject.Inject

val module = SerializersModule {
    polymorphic(Component::class) {
        subclass(Container::class)
        subclass(Button::class)
        subclass(Text::class)
        subclass(Input::class)
    }
}
@OptIn(ExperimentalSerializationApi::class)
class PrimerProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val json = Json {
        serializersModule = module
    }

    suspend fun getScreenData(): PrimerDTO = withContext(Dispatchers.IO) {
        val inputStream = context.assets.open("json/primer.json").buffered()
        val dto = json.decodeFromStream<PrimerDTO>(inputStream)
        delay(3000)
        return@withContext dto
    }
}

