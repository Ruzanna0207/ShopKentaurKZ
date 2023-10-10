package com.manage.shopkentaurkz.data.for_feeding

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.manage.shopkentaurkz.core.data_classes.FeedingCategories
import com.manage.shopkentaurkz.core.roomDataBase.for_feeding.ForFeedingDataBase
import com.manage.shopkentaurkz.domain.for_feeding.ForFeedingRepository
import kotlinx.coroutines.tasks.await

class ForFeedingRepositoryImpl(context: Context) : ForFeedingRepository {
    private val roomDatabase = Room
        .databaseBuilder(context, ForFeedingDataBase::class.java, "product_feeding") //создание БД
        .build()

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    //получ-е товаров категории
    override suspend fun getFeeding(): List<FeedingCategories> {
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
            return emptyList()
        }
    }

    //функция обращ-ся к Firebase и получает данные
    private suspend fun getInfoFromFirebase(): MutableList<FeedingCategories> {
        val snapshot = database.child("forFeedingHorse").get().await()

        val savedEntity = roomDatabase.forFeedingProductDao().getAllFeedingProducts().map { it.toFeedingCategories() }
        Log.i("bd", "бд $savedEntity")

        val categoriesList = mutableListOf<FeedingCategories>()

        if (snapshot.exists()) {
            for (categorySnapshot in snapshot.children) {
                val category = categorySnapshot.getValue(FeedingCategories::class.java)
                category?.let {
                    categoriesList.add(it)
                    roomDatabase.forFeedingProductDao().insert(it.toFeedingEntity())
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