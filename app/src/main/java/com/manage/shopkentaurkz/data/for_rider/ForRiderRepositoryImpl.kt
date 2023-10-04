package com.manage.shopkentaurkz.data.for_rider

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.manage.shopkentaurkz.core.data_classes.ForRiderProduct
import com.manage.shopkentaurkz.domain.for_rider.ForRiderRepository
import kotlinx.coroutines.tasks.await

class ForRiderRepositoryImpl : ForRiderRepository {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override suspend fun getCategoriesForRider(): List<ForRiderProduct> {

        try {
            val snapshot = database.child("forRider").get().await()

            val categoriesList = mutableListOf<ForRiderProduct>()

            if (snapshot.exists()) {
                for (categorySnapshot in snapshot.children) {
                    val category = categorySnapshot.getValue(ForRiderProduct::class.java)
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
            val snapshot = database.child("forRider").get().await()

            val categoriesList = mutableListOf<ForRiderProduct>()

            if (snapshot.exists()) {
                for (categorySnapshot in snapshot.children) {
                    val category = categorySnapshot.getValue(ForRiderProduct::class.java)
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
