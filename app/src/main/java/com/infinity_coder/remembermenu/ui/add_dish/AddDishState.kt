package com.infinity_coder.remembermenu.ui.add_dish

import android.graphics.Bitmap
import com.infinity_coder.remembermenu.ui.common.IState

data class AddDishState(
    val title: String = "",
    val description: String = "",
    val image: Bitmap? = null
): IState