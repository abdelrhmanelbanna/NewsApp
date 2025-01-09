package com.example.newsapp.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.model.Category

class CategoryFragment : Fragment() {



   val categoriesList = listOf(
       Category("sports",R.string.sports,R.drawable.ic_sports,R.color.red),
       Category("general",R.string.general,R.drawable.ic_politics,R.color.blue),
       Category("health",R.string.health,R.drawable.ic_health,R.color.pink),
       Category("business",R.string.business,R.drawable.ic_bussines,R.color.brawn),
       Category("entertainment",R.string.entertainment,R.drawable.ic_environment,R.color.babyBlue),
       Category("science",R.string.science,R.drawable.ic_science,R.color.yellow),

   )

    lateinit var recyclerView : RecyclerView
    val adapter = CategoryAdapter(categoriesList)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.CategoryRecyclerView)

        adapter.onItemClickListener = object : CategoryAdapter.OnItemClickListener{
            override fun onItemClick(pos: Int, category: Category) {

             onCategoryClickListener?.onCategoryClick(category)

            }

        }

        recyclerView.adapter = adapter

    }

    var onCategoryClickListener:OnCategoryClickListener?=null
    interface OnCategoryClickListener{
        fun onCategoryClick(category: Category)
    }



}