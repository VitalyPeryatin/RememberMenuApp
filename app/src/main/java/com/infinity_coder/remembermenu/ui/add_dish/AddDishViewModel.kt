package com.infinity_coder.remembermenu.ui.add_dish

import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.lifecycle.viewModelScope
import com.infinity_coder.remembermenu.BasicApp
import com.infinity_coder.remembermenu.domain.entities.dishes.DishEntity
import com.infinity_coder.remembermenu.domain.interactors.dishes.DishesInteractor
import com.infinity_coder.remembermenu.ui.common.BaseMviViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddDishViewModel : BaseMviViewModel<AddDishState, AddDishEffect, AddDishIntent>() {

    private val dishesInteractor = DishesInteractor()

    init {
        _state.value = AddDishState()
    }

    override fun handleIntents(intent: AddDishIntent) {
        when(intent) {
            is AddDishIntent.AddLastDish -> addLastDish(intent.dish)
            is AddDishIntent.AddNewDish -> addNewDish(intent.dish)
            is AddDishIntent.PickPhotoFromGallery -> addPhotoToDish()
            is AddDishIntent.ExtractBitmapFromIntent -> extractBitmapFromIntent(intent.intent)
            is AddDishIntent.UpdateState -> updateScreenState(intent.dish)
        }
    }

    private fun addLastDish(dish: DishEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                saveDish(dish)
            }
            callEffect { AddDishEffect.FinishAddDishScreen }
        }
    }

    private suspend fun saveDish(dish: DishEntity): Boolean {
        if (dish.title.isEmpty()) return false

        dishesInteractor.addDish(dish)
        return true
    }

    private fun addNewDish(dish: DishEntity) {
        viewModelScope.launch {
            updateState { AddDishState() }
            withContext(Dispatchers.IO) {
                saveDish(dish)
            }
        }
    }

    private fun addPhotoToDish() {
        viewModelScope.launch {
            callEffect { AddDishEffect.OpenPhotoGalleryScreen }
        }
    }

    private fun extractBitmapFromIntent(intent: Intent) {
        viewModelScope.launch {
            val bitmap = provideBitmap(intent)
            updateState {
                val oldState = it ?: AddDishState()
                return@updateState oldState.copy(image = bitmap)
            }
        }
    }

    private fun provideBitmap(intent: Intent): Bitmap? {
        val picUri = intent.data
        return MediaStore.Images.Media.getBitmap(BasicApp.context.contentResolver, picUri)
    }

    private fun updateScreenState(dish: DishEntity) {
        viewModelScope.launch {
            updateState {
                with(dish) {
                    val oldState = it ?: AddDishState()
                    return@updateState oldState.copy(title = title, description = description, image = image)
                }
            }
        }
    }
}