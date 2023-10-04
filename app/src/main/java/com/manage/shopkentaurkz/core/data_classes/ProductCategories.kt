package com.manage.shopkentaurkz.core.data_classes

data class ProductCategories(
    val imageUrl: String,
    val name: String,
    val description: String) {
    constructor() : this("", "", "")
}

