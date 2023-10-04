package com.manage.shopkentaurkz.data.for_feeding

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.manage.shopkentaurkz.core.data_classes.FeedingCategories
import com.manage.shopkentaurkz.domain.for_feeding.ForFeedingRepository
import kotlinx.coroutines.tasks.await

class ForFeedingRepositoryImpl : ForFeedingRepository {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override suspend fun getFeeding(): List<FeedingCategories> {
        try {
            val snapshot = database.child("forFeedingHorse").get().await()

            val categoriesList = mutableListOf<FeedingCategories>()

            if (snapshot.exists()) {
                for (categorySnapshot in snapshot.children) {
                    val category = categorySnapshot.getValue(FeedingCategories::class.java)
                    category?.let {
                        categoriesList.add(it)
                    }
                }
            }
            return categoriesList
        } catch (e: Exception) {
            Log.e("shop", "Ошибка", e)
            return emptyList()
        }
    }

    override suspend fun getTags(): List<String> {
        try {
            val snapshot = database.child("forFeedingHorse").get().await()

            val categoriesList = mutableListOf<FeedingCategories>()

            if (snapshot.exists()) {
                for (categorySnapshot in snapshot.children) {
                    val category = categorySnapshot.getValue(FeedingCategories::class.java)
                    category?.let {
                        categoriesList.add(it)
                    }
                }
            }
            return categoriesList.flatMap { it.categories ?: emptyList() }
        } catch (e: Exception) {
            Log.e("shop", "Ошибка", e)
            return emptyList()
        }
    }
}