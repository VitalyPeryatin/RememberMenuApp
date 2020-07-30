package com.infinity_coder.remembermenu.ui.dishes

import com.infinity_coder.remembermenu.domain.entities.dishes.DishEntity
import com.infinity_coder.remembermenu.ui.common.IIntent

sealed class DishesIntent: IIntent {
    object FetchDishes: DishesIntent()
    object AddDish: DishesIntent()
    class OpenOptionsDialog(val dish: DishEntity): DishesIntent()
}