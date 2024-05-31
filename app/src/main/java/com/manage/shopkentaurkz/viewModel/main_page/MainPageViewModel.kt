package com.manage.shopkentaurkz.viewModel.main_page

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.manage.shopkentaurkz.core.data_classes.ProductCategories
import com.manage.shopkentaurkz.data.main_page.MainPageRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MainPageViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainPageRepositoryImpl(application)

    private var _currentCategories = MutableLiveData<List<ProductCategories?>>()
    val currentCategories: LiveData<List<ProductCategories?>> = _currentCategories

    //ссобщение об ошибке для пользователя
    private var error = MutableLiveData<String>()

    fun onCraete() {
        getCategories()
    }

    //получ-е категорий товаров
    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val category = repository.getCategories()

                withContext(Dispatchers.Main) {
                    _currentCategories.value = category
                }

            } catch (e: Exception) {
                // Обработка ошибок
                if (e is HttpException && e.code() == 404) {
                    val errorMessage = "Not found"
                    Log.e("shop", errorMessage)

                    withContext(Dispatchers.Main) {
                        error.value = errorMessage
                    }
                } else {
                    val errorMessage = "Error while retrieving information:${e.message}"
                    Log.e("shop", errorMessage)

                    withContext(Dispatchers.Main) {
                        error.value = errorMessage
                    }
                }
            }
        }
    }
}