package com.infinity_coder.remembermenu.ui.learn

import com.infinity_coder.remembermenu.domain.entities.dishes.DishEntity
import com.infinity_coder.remembermenu.ui.common.IState

data class LearnState(
    val dish: DishEntity? = null,
    val isShowAnswer: Boolean = false
): IState