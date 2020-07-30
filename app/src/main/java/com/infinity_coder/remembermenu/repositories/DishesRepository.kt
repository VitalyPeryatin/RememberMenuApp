package com.infinity_coder.remembermenu.repositories

import android.graphics.Bitmap
import com.infinity_coder.remembermenu.BasicApp
import com.infinity_coder.remembermenu.data.db.RememberMenuDatabase
import com.infinity_coder.remembermenu.data.db.models.DishDbModel
import com.infinity_coder.remembermenu.domain.entities.dishes.DishEntity
import com.infinity_coder.remembermenu.ui.common.getImageFromFile
import com.infinity_coder.remembermenu.ui.common.saveImageToFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.File
import java.util.*


object DishesRepository {

    private val dishesDao = RememberMenuDatabase.roomDatabase.dishesDao

    private val IMAGE_PATH = "${BasicApp.context.filesDir}/images"

    init {
        val file = File(IMAGE_PATH)
        file.mkdirs()
    }

    fun getDishes(): Flow<List<DishEntity>> = dishesDao.getDishes()
        .map { mapToDishEntityList(it) }

    fun getRandomDish(): Flow<DishEntity?> = dishesDao.getRandomDish()
        .map {
            if (it == null) {
                return@map null
            }
            return@map mapToDishEntity(it)
        }

    suspend fun addDish(dish: DishEntity) {
        val dishDbModel = mapToDishDbModel(dish)
        dishesDao.addDish(dishDbModel)
    }

    private fun mapToDishDbModel(dish: DishEntity): DishDbModel {
        return DishDbModel(
            title = dish.title,
            description = dish.description,
            photoURL = saveDishImageToFile(dish.image)
        )
    }

    private fun saveDishImageToFile(dishImage: Bitmap?): String? {
        if (dishImage == null) return null

        val filePath = "$IMAGE_PATH/${UUID.randomUUID()}.jpeg"
        saveImageToFile(dishImage, filePath)
        return filePath
    }

    private fun mapToDishEntityList(dishDbModels: List<DishDbModel>): List<DishEntity> {
        return dishDbModels.map { mapToDishEntity(it) }
    }

    private fun mapToDishEntity(dishDbModel: DishDbModel): DishEntity {
        return DishEntity(
            title = dishDbModel.title,
            description = dishDbModel.description,
            image = getImageFromFile(dishDbModel.photoURL)
        )
    }

}