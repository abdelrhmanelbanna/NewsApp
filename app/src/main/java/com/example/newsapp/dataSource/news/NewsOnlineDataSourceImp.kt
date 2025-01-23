package com.example.newsapp.dataSource.news

import com.example.newsapplication.Constants
import com.example.newsapplication.api.WebServices
import com.example.newsapplication.model.ArticlesItem

class NewsOnlineDataSourceImp(val webServices: WebServices)
    : NewsOnlineDataSource {

    override suspend fun getNews(sourcesId: String?): List<ArticlesItem?>? {

        try {
            val result = webServices.getNews(Constants.apiKey,sourcesId!!).articles
            return result
        }catch (ex:Exception){
            throw ex
        }

    }
}