package com.example.newsapp.repo.news

import com.example.newsapplication.model.ArticlesItem

interface NewsRepository {
    suspend fun getNews(sourcesId:String?):List<ArticlesItem?>?
}
