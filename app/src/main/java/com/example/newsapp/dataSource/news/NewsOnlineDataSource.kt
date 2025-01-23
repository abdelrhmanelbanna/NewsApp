package com.example.newsapp.dataSource.news

import com.example.newsapplication.model.ArticlesItem

interface NewsOnlineDataSource {
    suspend fun getNews(sourcesId:String?):List<ArticlesItem?>?
}