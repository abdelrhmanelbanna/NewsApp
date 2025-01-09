package com.example.newsapplication.api

import com.example.newsapplication.model.NewsResponse
import com.example.newsapplication.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("v2/top-headlines/sources")
    fun getSources(
      @Query("apiKey")apiKey :String ,
    ): Call<SourcesResponse>

    @GET("/v2/everything")
    fun getNews(
        @Query("apiKey")apiKey :String ,
        @Query("sources")sources :String ,
    ):Call<NewsResponse>

}