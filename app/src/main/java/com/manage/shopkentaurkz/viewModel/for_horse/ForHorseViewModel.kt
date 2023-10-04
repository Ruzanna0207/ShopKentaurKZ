package com.manage.shopkentaurkz.viewModel.for_horse

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.manage.shopkentaurkz.data.for_horse.ForHorseRepositoryImpl
import com.manage.shopkentaurkz.core.data_classes.ForHorseProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ForHorseViewModel(application: Application) : AndroidViewModel(application) {

    private val repositoryHorse = ForHorseRepositoryImpl()

    private var _forHorse = MutableLiveData<List<ForHorseProduct?>>()
    val forHorse: LiveData<List<ForHorseProduct?>> = _forHorse

    private var _tagsHorse = MutableLiveData<List<String>>()
    val tagsHorse: LiveData<List<String>> = _tagsHorse

    //ссобщение об ошибке для пользователя
    var error = MutableLiveData<String>()


    fun getProductsForHorse() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val category = repositoryHorse.getCategoriesForHorse()

                withContext(Dispatchers.Main) {
                    _forHorse.value = category
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


     fun getTagsHorse() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val tags = repositoryHorse.getTags()

                withContext(Dispatchers.Main) {
                    _tagsHorse.value = tags
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

