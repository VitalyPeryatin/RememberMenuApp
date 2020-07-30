package com.infinity_coder.remembermenu.data.db

import androidx.room.Room
import com.infinity_coder.remembermenu.BasicApp

object RememberMenuDatabase {
    val roomDatabase: AppDatabase by lazy {
        Room.databaseBuilder(BasicApp.context, AppDatabase::class.java, "remember-menu-database")
            .fallbackToDestructiveMigration()
            .build()
    }
}