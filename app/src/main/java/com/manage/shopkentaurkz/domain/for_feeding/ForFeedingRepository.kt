package com.manage.shopkentaurkz.domain.for_feeding

import com.manage.shopkentaurkz.core.data_classes.FeedingCategories

interface ForFeedingRepository {

    suspend fun getFeeding(): List<FeedingCategories>

    suspend fun getTags(): List<String>
}