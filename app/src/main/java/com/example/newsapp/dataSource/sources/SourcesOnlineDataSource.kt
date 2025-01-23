package com.example.newsapp.dataSource.sources

import com.example.newsapplication.model.SourcesItem

interface SourcesOnlineDataSource {
    suspend fun getSources(category:String):List<SourcesItem?>?
}