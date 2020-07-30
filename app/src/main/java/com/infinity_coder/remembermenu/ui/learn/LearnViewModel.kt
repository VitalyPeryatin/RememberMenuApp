package com.infinity_coder.remembermenu.ui.learn

import androidx.lifecycle.viewModelScope
import com.infinity_coder.remembermenu.domain.interactors.dishes.DishesInteractor
import com.infinity_coder.remembermenu.ui.common.BaseMviViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LearnViewModel : BaseMviViewModel<LearnState, LearnEffect, LearnIntent>() {

    private val dishesInteractor = DishesInteractor()

    override fun handleIntents(intent: LearnIntent) {
        when(intent) {
            is LearnIntent.FetchRandomDish -> fetchRandomDish()
            is LearnIntent.NextCard -> nextCard()
            is LearnIntent.ChangeCardMode -> changeCardMode()
        }
    }

    private fun fetchRandomDish() {
        dishesInteractor.getRandomDish()
            .onEach { dish ->
                updateState {
                    val oldState = it ?: LearnState()
                    oldState.copy(dish = dish, isShowAnswer = false)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun nextCard() {
        fetchRandomDish()
    }

    private fun changeCardMode() {
        viewModelScope.launch {
            updateState {
                val oldState = it ?: LearnState()
                oldState.copy(isShowAnswer = !oldState.isShowAnswer)
            }
        }
    }

}