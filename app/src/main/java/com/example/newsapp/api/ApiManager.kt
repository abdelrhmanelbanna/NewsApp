package com.example.newsapplication.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.Level


class ApiManager {


    companion object {

        private var retrofit : Retrofit?=null

        private fun getInstance():Retrofit{

            if(retrofit==null){
                val logging = HttpLoggingInterceptor { message ->
                    Log.e("api", message)
                }

                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()


                retrofit= Retrofit.Builder()
                    .baseUrl("https://newsapi.org/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit!!
        }

        fun getApis():WebServices{
            return getInstance().create(WebServices::class.java)
        }

    }

}