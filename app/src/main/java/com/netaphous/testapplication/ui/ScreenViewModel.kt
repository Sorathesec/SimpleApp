package com.netaphous.testapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.netaphous.testapplication.data.Event
import com.netaphous.testapplication.data.PrimerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenViewModel @Inject constructor(
    private val primerProvider: PrimerProvider,
) : ViewModel() {
    private val _primer = MutableStateFlow(ScreenStateP(showLoading = true))
    val primer: StateFlow<ScreenStateP> = _primer

    private var events: List<Event> = emptyList()
    private val inputs: MutableMap<String, String> = mutableMapOf()

    fun getJoke() {
        viewModelScope.launch {
            val dto = primerProvider.getScreenData()
            events = dto.events
            _primer.emit(ScreenStateP(dto.screens.firstOrNull()))
        }
    }

    fun onEvent(receivedEvent: String) {
        events.firstOrNull { it.type == receivedEvent }?.let { realEvent ->
            realEvent.actions.forEach { action ->
                when (action.type) {
                    "log" -> {
                        val output = action.data.toString()
                        inputs.forEach { (key, value) ->
                            output.replace("{{$key}}", value)
                        }
                        println(output)
                    }
                }
            }
        }
    }

    fun onInput(id: String, value: String) {
        inputs[id] = value
    }
}