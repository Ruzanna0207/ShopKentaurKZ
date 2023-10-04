package com.manage.shopkentaurkz.domain.for_rider

import com.manage.shopkentaurkz.core.data_classes.ForRiderProduct

interface ForRiderRepository {

    suspend fun getCategoriesForRider(): List<ForRiderProduct>

    suspend fun getTags(): List<String>
}