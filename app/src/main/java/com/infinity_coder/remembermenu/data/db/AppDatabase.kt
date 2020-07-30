package com.infinity_coder.remembermenu.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.infinity_coder.remembermenu.data.db.dao.DishesDao
import com.infinity_coder.remembermenu.data.db.models.DishDbModel

@Database(entities = [DishDbModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val dishesDao: DishesDao
}