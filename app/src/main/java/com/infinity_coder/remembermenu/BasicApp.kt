package com.infinity_coder.remembermenu

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context


class BasicApp : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        context = this
        super.onCreate()
    }
}