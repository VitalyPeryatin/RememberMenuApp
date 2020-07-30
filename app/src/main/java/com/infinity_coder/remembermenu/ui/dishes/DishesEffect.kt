package com.infinity_coder.remembermenu.ui.dishes

import com.infinity_coder.remembermenu.ui.common.IEffect

sealed class DishesEffect: IEffect {
    object OpenAddDishScreen: DishesEffect()
}