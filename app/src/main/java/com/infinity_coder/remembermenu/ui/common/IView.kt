package com.infinity_coder.remembermenu.ui.common

interface IView<STATE: IState, EFFECT: IEffect> {

    fun render(state: STATE)

    fun react(effect: EFFECT?)
}