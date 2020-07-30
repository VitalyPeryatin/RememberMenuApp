package com.infinity_coder.remembermenu.ui.learn

import com.infinity_coder.remembermenu.ui.common.IIntent

sealed class LearnIntent: IIntent {
    object FetchRandomDish: LearnIntent()
    object NextCard: LearnIntent()
    object ChangeCardMode: LearnIntent()
}