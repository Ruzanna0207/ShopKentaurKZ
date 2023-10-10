package com.manage.shopkentaurkz.core.roomDataBase.main_page

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.manage.shopkentaurkz.core.roomDataBase.ListStringConverter

@Database(entities = [ProductEntity::class], version = 1)
@TypeConverters(ListStringConverter::class)
abstract class ProductsDatabase : RoomDatabase() {
    abstract fun productCategoriesDao(): ProductsDao
}

