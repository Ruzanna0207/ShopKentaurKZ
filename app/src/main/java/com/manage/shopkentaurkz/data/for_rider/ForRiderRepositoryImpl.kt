package com.manage.shopkentaurkz.data.for_rider

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.manage.shopkentaurkz.core.data_classes.ForRiderProduct
import com.manage.shopkentaurkz.domain.for_rider.ForRiderRepository
import kotlinx.coroutines.tasks.await

class ForRiderRepositoryImpl : ForRiderRepository {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    //получ-е товаров категории
    override suspend fun getCategoriesForRider(): List<ForRiderProduct> {
        return try {
            getInfoFromFirebase()
        } catch (e: Exception) {
            Log.e("shop", "Ошибка", e)
            emptyList()
        }
    }

    //получ-е тегов
    override suspend fun getTags(): List<String> {
        return try {
            getInfoFromFirebase().flatMap { it.categories ?: emptyList() }
        } catch (e: Exception) {
            Log.e("shop", "Ошибка", e)
            emptyList()
        }
    }

    //функция обращ-ся к Firebase и получает данные
    private suspend fun getInfoFromFirebase(): MutableList<ForRiderProduct> {
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
    }
}
