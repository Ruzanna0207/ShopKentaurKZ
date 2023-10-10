package com.manage.shopkentaurkz.core.roomDataBase.main_page

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productCategories: ProductEntity)

    @Query("SELECT * FROM product_categories")
    fun getAllProducts(): List<ProductEntity>
}
