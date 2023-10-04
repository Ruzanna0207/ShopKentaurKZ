package com.manage.shopkentaurkz.data.main_page

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.manage.shopkentaurkz.core.data_classes.ProductCategories
import com.manage.shopkentaurkz.domain.main_page.MainPageRepository
import kotlinx.coroutines.tasks.await


class MainPageRepositoryImpl : MainPageRepository {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override suspend fun getCategories(): List<ProductCategories> {
        try {
            val snapshot = database.child("allCategories").get().await()

            val categoriesList = mutableListOf<ProductCategories>()

            if (snapshot.exists()) {
                for (categorySnapshot in snapshot.children) {
                    val category = categorySnapshot.getValue(ProductCategories::class.java)
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
}