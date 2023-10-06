package com.manage.shopkentaurkz.data.for_horse

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.manage.shopkentaurkz.core.data_classes.ForHorseProduct
import com.manage.shopkentaurkz.domain.for_horse.ForHorseRepository
import kotlinx.coroutines.tasks.await

class ForHorseRepositoryImpl : ForHorseRepository {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    //получ-е товаров категории
    override suspend fun getCategoriesForHorse(): List<ForHorseProduct> {
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
            getInfoFromFirebase().flatMap { products ->
                products.categories ?: emptyList()
            }
        } catch (e: Exception) {
            Log.e("shop", "Ошибка", e)
            return emptyList()
        }
    }

    //функция обращ-ся к Firebase и получает данные
    private suspend fun getInfoFromFirebase(): MutableList<ForHorseProduct> {
        val snapshot = database.child("forHorse").get().await()

        val categoriesList = mutableListOf<ForHorseProduct>()
        if (snapshot.exists()) {
            for (categorySnapshot in snapshot.children) {
                val category = categorySnapshot.getValue(ForHorseProduct::class.java)
                category?.let {
                    categoriesList.add(it)
                }
            }
        }
        return categoriesList
    }
}