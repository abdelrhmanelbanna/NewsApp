package com.example.newsapp.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.model.Category

class CategoryAdapter(val categories : List<Category>)
    :RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                if(viewType==LEFT_SIDED) R.layout.left_sided_category
                else R.layout.right_sided_category,parent,false)

        return ViewHolder(view)
    }

    val LEFT_SIDED = 10;
    val RIGHT_SIDED = 20;
    override fun getItemViewType(position: Int): Int {
        if(position%2==0){
            return LEFT_SIDED
        }else{
            return RIGHT_SIDED
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = categories[position]
        holder.title.setText(item.titleId)
        holder.image.setImageResource(item.imageResId)

        val drawable = holder.root.background.mutate()
        val color = holder.root.context.getColor(item.backgroundColorId)
        drawable.setTint(color)

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position,item)
        }


    }
    var onItemClickListener:OnItemClickListener?=null

    interface OnItemClickListener{
        fun onItemClick(pos:Int,category: Category)
    }

    override fun getItemCount(): Int {
        return categories.size
    }



    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){

        val title:TextView = itemView.findViewById(R.id.titleCategory)
        val image:ImageView = itemView.findViewById(R.id.imageCategory)
        val root:ConstraintLayout = itemView.findViewById(R.id.rootCategory)



    }


}