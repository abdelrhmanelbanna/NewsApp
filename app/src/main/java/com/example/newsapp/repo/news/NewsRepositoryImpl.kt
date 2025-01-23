package com.example.newsapp.repo.news

import com.example.newsapp.dataSource.news.NewsOnlineDataSource
import com.example.newsapplication.model.ArticlesItem

class NewsRepositoryImpl(val newsOnlineDataSource: NewsOnlineDataSource)
    : NewsRepository {

    override suspend fun getNews(sourcesId: String?): List<ArticlesItem?>? {
        try {
            return newsOnlineDataSource.getNews(sourcesId)
        }catch (ex:Exception){
            throw ex
        }

    }
}