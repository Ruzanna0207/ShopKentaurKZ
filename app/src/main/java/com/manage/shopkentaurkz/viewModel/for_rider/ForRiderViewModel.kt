package com.manage.shopkentaurkz.viewModel.for_rider

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.manage.shopkentaurkz.core.data_classes.ForRiderProduct
import com.manage.shopkentaurkz.data.for_rider.ForRiderRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ForRiderViewModel(application: Application) : AndroidViewModel(application) {

    private val repositoryRider = ForRiderRepositoryImpl(application)

    private var _forRider = MutableLiveData<List<ForRiderProduct?>>()
    val forRider: LiveData<List<ForRiderProduct?>> = _forRider

    private var _tagsRider = MutableLiveData<List<String>>()
    val tagsRider: LiveData<List<String>> = _tagsRider

    //ссобщение об ошибке для пользователя
    var error = MutableLiveData<String>()


    //получ-е товаров категории
    fun getProductsForRider() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val category = repositoryRider.getCategoriesForRider()

                withContext(Dispatchers.Main) {
                    _forRider.value = category
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

//получ-е тегов
    fun getTagsRider() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val tags = repositoryRider.getTags()

                withContext(Dispatchers.Main) {
                    _tagsRider.value = tags
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

