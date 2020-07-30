package com.infinity_coder.remembermenu.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DishDbModel.TABLE_NAME)
data class DishDbModel(
    @ColumnInfo(name = COLUMN_TITLE)
    val title: String,

    @ColumnInfo(name = COLUMN_DESCRIPTION)
    val description: String = "",

    @ColumnInfo(name = COLUMN_PHOTO_URL) var photoURL: String? = null
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id: Int = 0

    companion object {
        const val TABLE_NAME = "Dish"

        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_PHOTO_URL = "photo_url"
    }
}