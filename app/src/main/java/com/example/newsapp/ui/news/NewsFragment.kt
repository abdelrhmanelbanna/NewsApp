package com.example.newsapp.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsBinding
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


    lateinit var viewModel: NewsViewModel
    lateinit var viewDataBinding: FragmentNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_news,container,false)
        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("category", category.id)
        initView()

        viewModel.getNewsSources(category)
    }

    val adapter = NewsAdapter(null)

    fun initView() {

        viewDataBinding.recyclerView.adapter = adapter

        viewModel.processBarVisibility.observe(viewLifecycleOwner, Observer { processBarVisiable ->
            if (processBarVisiable) {
                viewDataBinding.progressBar.isVisible = true
            } else {
                viewDataBinding.progressBar.isVisible = false
            }
        })

        viewModel.sourcesResponse.observe(viewLifecycleOwner, Observer { soursesResponse ->
            addSourcesToTabLayout(soursesResponse)
        })

        viewModel.newsResponse.observe(viewLifecycleOwner, Observer { newsResponse ->
            adapter.changeData(newsResponse)
        })


    }

    private fun addSourcesToTabLayout(sources: List<SourcesItem?>?) {

        sources?.forEach {

            val tab = viewDataBinding.tabLayout.newTab()
            tab.setText(it?.name)
            tab.tag = sources
            viewDataBinding.tabLayout.addTab(tab)

        }

        viewDataBinding.tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val source = sources?.get(tab?.position ?: 0)

                viewModel.getNewsBySource(source)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val source = sources?.get(tab?.position ?: 0)
                //   val source  = tab?.tag as SourcesItem

                viewModel.getNewsBySource(source)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val source = sources?.get(tab?.position ?: 0)
                viewModel.getNewsBySource(source)
            }

        })
        viewDataBinding.tabLayout.getTabAt(0)?.select()
    }


}