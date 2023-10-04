package com.manage.shopkentaurkz.domain.main_page

import com.manage.shopkentaurkz.core.data_classes.ProductCategories

interface MainPageRepository {

    suspend fun getCategories() : List<ProductCategories>

}