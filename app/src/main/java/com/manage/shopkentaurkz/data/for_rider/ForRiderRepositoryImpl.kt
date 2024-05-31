package com.manage.shopkentaurkz.data.for_rider

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.manage.shopkentaurkz.core.data_classes.ForRiderProduct
import com.manage.shopkentaurkz.core.roomDataBase.for_rider.ForRiderDataBase
import com.manage.shopkentaurkz.domain.for_rider.ForRiderRepository
import kotlinx.coroutines.tasks.await

class ForRiderRepositoryImpl(context: Context) : ForRiderRepository {
    private val roomDatabase = Room
        .databaseBuilder(context, ForRiderDataBase::class.java, "product_for_rider") //создание БД
        .build()

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

        val savedEntity = roomDatabase.forRiderProductDao().getAllRiderProducts()
            .map { it.toRiderProductCategories() }
        Log.i("bd", "бд ${roomDatabase.forRiderProductDao().getAllRiderProducts()}")

        val categoriesList = mutableListOf<ForRiderProduct>()
        if (snapshot.exists()) {
            for (categorySnapshot in snapshot.children) {
                val category = categorySnapshot.getValue(ForRiderProduct::class.java)
                category?.let {
                    categoriesList.add(it)
                    roomDatabase.forRiderProductDao().insert(it.toRiderEntity())
                }
            }
        }
        return if (savedEntity == categoriesList) {
            Log.i("bd", "бд $savedEntity")
            savedEntity.toMutableList()
        } else {
            Log.i("bd", categoriesList.toString())
            categoriesList
        }
    }
}