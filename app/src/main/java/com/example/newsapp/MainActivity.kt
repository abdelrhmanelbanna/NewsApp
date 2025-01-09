package com.example.newsapp

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.ui.news.NewsAdapter
import com.example.newsapplication.Constants
import com.example.newsapplication.api.ApiManager
import com.example.newsapplication.model.NewsResponse
import com.example.newsapplication.model.SourcesItem
import com.example.newsapplication.model.SourcesResponse
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var tabLayout : TabLayout
    lateinit var processBar : ProgressBar
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initView()


        getNewsSources()


    }

    val adapter = NewsAdapter(null)
    fun initView(){
        tabLayout = findViewById(R.id.tabLayout)
        processBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.adapter = adapter


    }

    private fun getNewsSources() {
        ApiManager.getApis().getSources(Constants.apiKey,"")
            .enqueue( object : retrofit2.Callback<SourcesResponse>{
                override fun onResponse(call: Call<SourcesResponse>,
                                        response: Response<SourcesResponse>
                ) {

                    // Log.e("responseData",response.body().toString())

                    processBar.isVisible=false
                    addSourcesToTabLayout(response.body()?.sources)
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    processBar.isVisible=false
                    Log.e("error",t.localizedMessage)
                }


            })

    }

    private fun addSourcesToTabLayout(sources: List<SourcesItem?>?) {

        sources?.forEach{

            val tab= tabLayout.newTab()
            tab.setText(it?.name)
            tab.tag = sources
            tabLayout.addTab(tab)

        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val source = sources?.get(tab?.position?:0)

                getNewsBySource(source)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val source = sources?.get(tab?.position?:0)
                //   val source  = tab?.tag as SourcesItem

                getNewsBySource(source)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val source = sources?.get(tab?.position?:0)
                getNewsBySource(source)
            }

        })
        tabLayout.getTabAt(0)?.select()
    }

    private fun getNewsBySource(source: SourcesItem?) {

        ApiManager.getApis().getNews(Constants.apiKey,source?.id?:"")
            .enqueue(object : retrofit2.Callback<NewsResponse>{

                override fun onResponse(call: Call<NewsResponse>,
                                        response: Response<NewsResponse>
                ) {
                    processBar.isVisible = false
                    adapter.changeData(response.body()?.articles)
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    processBar.isVisible = false
                }


            })

    }

}