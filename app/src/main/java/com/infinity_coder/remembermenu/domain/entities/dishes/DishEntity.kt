package com.infinity_coder.remembermenu.domain.entities.dishes

import android.graphics.Bitmap

data class DishEntity(
    val title: String = "",
    val description: String = "",
    val image: Bitmap? = null
)