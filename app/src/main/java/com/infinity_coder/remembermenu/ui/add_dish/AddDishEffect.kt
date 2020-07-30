package com.infinity_coder.remembermenu.ui.add_dish

import com.infinity_coder.remembermenu.ui.common.IEffect

sealed class AddDishEffect: IEffect {
    object FinishAddDishScreen: AddDishEffect()
    object OpenPhotoGalleryScreen: AddDishEffect()
}