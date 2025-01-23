package com.example.newsapp.dataSource.sources

import com.example.newsapp.model.Category
import com.example.newsapplication.Constants
import com.example.newsapplication.api.WebServices
import com.example.newsapplication.model.SourcesItem

class SourcesOnlineDataSourceImpl (val webServices: WebServices):SourcesOnlineDataSource {

    override suspend fun getSources(category: String): List<SourcesItem?>? {

        try {
        val result = webServices.getSources(Constants.apiKey,category).sources
            return result
        }catch (ex:Exception){
            throw ex
        }
    }
}