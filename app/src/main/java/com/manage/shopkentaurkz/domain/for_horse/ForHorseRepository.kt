package com.manage.shopkentaurkz.domain.for_horse

import com.manage.shopkentaurkz.core.data_classes.ForHorseProduct

interface ForHorseRepository {

    suspend fun getCategoriesForHorse(): List<ForHorseProduct>

    suspend fun getTags(): List<String>
}