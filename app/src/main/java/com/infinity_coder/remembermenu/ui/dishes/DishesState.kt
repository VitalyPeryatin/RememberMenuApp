package com.infinity_coder.remembermenu.ui.dishes

import com.infinity_coder.remembermenu.domain.entities.dishes.DishEntity
import com.infinity_coder.remembermenu.ui.common.IState

data class DishesState(
    val dishes: List<DishEntity?> = emptyList(),
    val optionDialogDish: DishEntity? = null
): IState