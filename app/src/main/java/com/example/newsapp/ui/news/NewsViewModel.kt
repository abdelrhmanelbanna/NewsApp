package com.example.newsapp.ui.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.Category
import com.example.newsapplication.Constants
import com.example.newsapplication.api.ApiManager
import com.example.newsapplication.model.ArticlesItem
import com.example.newsapplication.model.SourcesItem
import kotlinx.coroutines.launch


class NewsViewModel : ViewModel() {

    var sourcesResponse = MutableLiveData<List<SourcesItem?>?>()
    var newsResponse = MutableLiveData<List<ArticlesItem?>?>()
    var processBarVisibility = MutableLiveData<Boolean>()


    fun getNewsSources(category: Category) {

        processBarVisibility.value = true

        viewModelScope.launch {
            try {
                val result = ApiManager.getApis().getSources(Constants.apiKey, category.id)
                processBarVisibility.value = false
                sourcesResponse.value = result.sources
            } catch (ex: Exception) {
                processBarVisibility.value = false
                Log.e("fail", ex.localizedMessage)
            }

        }

    }

    fun getNewsBySource(source: SourcesItem?) {
        processBarVisibility.value = true

        viewModelScope.launch {
            try {
                val result = ApiManager.getApis().getNews(Constants.apiKey, source?.id ?: "")
                processBarVisibility.value = false
                newsResponse.value = result.articles
            } catch (ex: Exception) {
                processBarVisibility.value = false
                Log.e("fail", ex.localizedMessage)
            }

        }
    }


}