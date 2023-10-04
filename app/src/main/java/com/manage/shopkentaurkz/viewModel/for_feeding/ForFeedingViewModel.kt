package com.manage.shopkentaurkz.viewModel.for_feeding

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.manage.shopkentaurkz.core.data_classes.FeedingCategories
import com.manage.shopkentaurkz.data.for_feeding.ForFeedingRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ForFeedingViewModel(application: Application) : AndroidViewModel(application) {

    private val repositoryFeeding = ForFeedingRepositoryImpl()

    private var _forFeeding = MutableLiveData<List<FeedingCategories?>>()
    val forFeeding: LiveData<List<FeedingCategories?>> = _forFeeding

    private var _tagsFeeding = MutableLiveData<List<String>>()
    val tagsFeeding: LiveData<List<String>> = _tagsFeeding

    //ссобщение об ошибке для пользователя
    var error = MutableLiveData<String>()


    fun getFeeding() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val category = repositoryFeeding.getFeeding()

                withContext(Dispatchers.Main) {
                    _forFeeding.value = category
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
                val tags = repositoryFeeding.getTags()

                withContext(Dispatchers.Main) {
                    _tagsFeeding.value = tags
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

