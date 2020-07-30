package com.infinity_coder.remembermenu.ui.dishes

import androidx.lifecycle.viewModelScope
import com.infinity_coder.remembermenu.domain.entities.dishes.DishEntity
import com.infinity_coder.remembermenu.domain.interactors.dishes.DishesInteractor
import com.infinity_coder.remembermenu.ui.common.BaseMviViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DishesViewModel : BaseMviViewModel<DishesState, DishesEffect, DishesIntent>() {

    private val dishesInteractor = DishesInteractor()

    override fun handleIntents(intent: DishesIntent) {
        when(intent) {
            is DishesIntent.FetchDishes -> fetchDishes()
            is DishesIntent.AddDish -> addDishes()
            is DishesIntent.OpenOptionsDialog -> openOptionsDialog(intent.dish)
        }
    }

    private fun fetchDishes() {
        viewModelScope.launch(Dispatchers.IO) {
            dishesInteractor.getDishes()
                .onEach { dishList ->
                    updateState {
                        DishesState(dishes = dishList)
                    }
                }
                .catch {
                    print(it.message)
                }
                .collect()
        }
    }

    private fun addDishes() {
        viewModelScope.launch {
            callEffect { DishesEffect.OpenAddDishScreen }
        }
    }

    private fun openOptionsDialog(dish: DishEntity) {
        viewModelScope.launch {
            updateState {
                val oldState = it ?: DishesState()
                return@updateState oldState.copy(optionDialogDish = dish)
            }
        }
    }

}