package com.infinity_coder.remembermenu.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class BaseMviViewModel<STATE: IState, EFFECT: IEffect, INTENT: IIntent>: ViewModel() {

    private val intents: Channel<INTENT> = Channel(Channel.UNLIMITED)

    private val _effects: LiveEvent<EFFECT> = LiveEvent()
    val effects: LiveEvent<EFFECT> = _effects

    protected val _state: MutableLiveData<STATE> = MutableLiveData()
    val state: LiveData<STATE> = _state

    init {
        _handleIntents()
    }

    private fun _handleIntents() {
        intents.consumeAsFlow()
            .onEach { handleIntents(it) }
            .launchIn(viewModelScope)
    }

    abstract fun handleIntents(intent: INTENT)

    fun sendIntent(intent: INTENT) {
        viewModelScope.launch {
            intents.send(intent)
        }
    }

    protected suspend fun updateState(handler: suspend (oldState: STATE?) -> STATE) {
        _state.postValue(handler(state.value))
    }

    protected suspend fun callEffect(handler: suspend () -> EFFECT) {
        _effects.postValue(handler())
    }
}