package com.example.newsapp.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.model.Category
import com.example.newsapplication.Constants
import com.example.newsapplication.api.ApiManager
import com.example.newsapplication.model.NewsResponse
import com.example.newsapplication.model.SourcesItem
import com.example.newsapplication.model.SourcesResponse
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Response

class NewsFragment(val category: Category) : Fragment() {

    lateinit var tabLayout : TabLayout
    lateinit var processBar : ProgressBar
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("category",category.id)
        initView()

        getNewsSources()
    }
    val adapter = NewsAdapter(null)
    fun initView(){
        tabLayout = requireView().findViewById(R.id.tabLayout)
        processBar = requireView().findViewById(R.id.progressBar)
        recyclerView = requireView().findViewById(R.id.recyclerView)

        recyclerView.adapter = adapter


    }

    private fun getNewsSources() {
        ApiManager.getApis().getSources(Constants.apiKey, category.id)
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