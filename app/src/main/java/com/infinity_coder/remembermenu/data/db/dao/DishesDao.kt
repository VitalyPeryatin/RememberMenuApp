package com.infinity_coder.remembermenu.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.infinity_coder.remembermenu.data.db.models.DishDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DishesDao {
    @Query("SELECT * FROM ${DishDbModel.TABLE_NAME}")
    fun getDishes(): Flow<List<DishDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDish(dish: DishDbModel)

    @Query("SELECT * FROM ${DishDbModel.TABLE_NAME} ORDER BY RANDOM() LIMIT 1")
    fun getRandomDish(): Flow<DishDbModel?>
}