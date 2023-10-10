package com.manage.shopkentaurkz.core.roomDataBase.main_page

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.manage.shopkentaurkz.core.data_classes.ProductCategories

@Entity(tableName = "product_categories")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val imageUrl: String,
    val name: String,
    val description: String
) {
    @Ignore
    fun toProductCategories(): ProductCategories {
        return ProductCategories(imageUrl, name, description)
    }
}
