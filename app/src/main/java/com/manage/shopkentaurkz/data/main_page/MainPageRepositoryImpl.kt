package com.manage.shopkentaurkz.data.main_page

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.manage.shopkentaurkz.core.data_classes.ProductCategories
import com.manage.shopkentaurkz.core.roomDataBase.main_page.ProductsDatabase
import com.manage.shopkentaurkz.domain.main_page.MainPageRepository
import kotlinx.coroutines.tasks.await

class MainPageRepositoryImpl(context: Context) : MainPageRepository {
    private val roomDatabase = Room
        .databaseBuilder(context, ProductsDatabase::class.java, "products_database") //создание БД
        .build()

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference


    override suspend fun getCategories(): List<ProductCategories> {
        val savedEntity = roomDatabase.productCategoriesDao().getAllProducts().map { it.toProductCategories() }
        Log.i("bd", "бд $savedEntity")
        try {
            val snapshot = database.child("allCategories").get().await()

            val categoriesList = mutableListOf<ProductCategories>()

            if (snapshot.exists()) {
                for (categorySnapshot in snapshot.children) {
                    val category = categorySnapshot.getValue(ProductCategories::class.java)
                    category?.let {
                        categoriesList.add(it)
                        roomDatabase.productCategoriesDao().insert(it.toProductEntity())
                    }
                }
            }

            return if (savedEntity == categoriesList) {
                Log.i("bd", "бд $savedEntity")
                savedEntity
            } else {
                Log.i("bd", categoriesList.toString())
                categoriesList
            }
        } catch (e: Exception) {
            Log.e("bd", "Error fetching categories: ${e.message}", e)
            return emptyList()
        }
    }
}