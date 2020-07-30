package com.infinity_coder.remembermenu.ui.add_dish

import android.content.Intent
import com.infinity_coder.remembermenu.domain.entities.dishes.DishEntity
import com.infinity_coder.remembermenu.ui.common.IIntent

sealed class AddDishIntent: IIntent {
    class AddLastDish(val dish: DishEntity): AddDishIntent()
    class AddNewDish(val dish: DishEntity): AddDishIntent()
    object PickPhotoFromGallery: AddDishIntent()
    class ExtractBitmapFromIntent(val intent: Intent): AddDishIntent()
    class UpdateState(val dish: DishEntity): AddDishIntent()
}