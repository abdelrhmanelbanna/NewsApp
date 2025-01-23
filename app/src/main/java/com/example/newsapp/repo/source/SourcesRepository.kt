package com.example.newsapp.repo.source

import com.example.newsapplication.model.SourcesItem

interface SourcesRepository {

    suspend fun getSources(category:String):List<SourcesItem?>?
}