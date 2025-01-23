package com.example.newsapp.repo.source

import com.example.newsapp.dataSource.sources.SourcesOnlineDataSource
import com.example.newsapplication.model.SourcesItem

class SourcesRepositoryImpl(val sourcesOnlineDataSource: SourcesOnlineDataSource)
    :SourcesRepository{

    override suspend fun getSources(category: String): List<SourcesItem?>? {

        try {
            val result = sourcesOnlineDataSource.getSources(category)
            return result
        }catch (ex:Exception){
            throw ex
        }
    }
}