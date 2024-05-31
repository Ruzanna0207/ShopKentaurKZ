package com.manage.shopkentaurkz.core.data_classes

import androidx.room.Ignore
import com.manage.shopkentaurkz.core.roomDataBase.main_page.ProductEntity

data class ProductCategories(
    val imageUrl: String,
    val name: String,
    val description: String) {
    constructor() : this("", "", "")

    @Ignore
    fun toProductEntity(): ProductEntity {
        return ProductEntity(0, imageUrl, name, description)
    }
}