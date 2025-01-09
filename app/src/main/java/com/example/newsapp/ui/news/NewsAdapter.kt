package com.example.newsapp.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.model.Category
import com.example.newsapplication.model.ArticlesItem

class NewsAdapter( var items:List<ArticlesItem?>?=null)
    : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items?.get(position)

        holder.title.text = item?.title

        holder.author.text = item?.author

        holder.time.text = item?.publishedAt

        Glide.with(holder.itemView)
            .load(item?.urlToImage)
            .into(holder.image)



    }



    override fun getItemCount(): Int {
        return items?.size?:0
    }

    fun changeData(articles: List<ArticlesItem?>?) {

        items = articles
        notifyDataSetChanged()

    }


    class ViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView){

            val title : TextView = itemView.findViewById(R.id.title)
            val time : TextView = itemView.findViewById(R.id.time)
            val image : ImageView = itemView.findViewById(R.id.image)
            val author : TextView = itemView.findViewById(R.id.author)

    }


}