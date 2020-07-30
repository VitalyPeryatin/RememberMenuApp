package com.infinity_coder.remembermenu.domain.interactors.dishes

import com.infinity_coder.remembermenu.domain.entities.dishes.DishEntity
import com.infinity_coder.remembermenu.repositories.DishesRepository
import kotlinx.coroutines.flow.Flow

class DishesInteractor {

    fun getDishes(): Flow<List<DishEntity>> {
        return DishesRepository.getDishes()
    }

    suspend fun addDish(dish: DishEntity) {
        DishesRepository.addDish(dish)
    }

    fun getRandomDish(): Flow<DishEntity?> {
        return DishesRepository.getRandomDish()
    }

}