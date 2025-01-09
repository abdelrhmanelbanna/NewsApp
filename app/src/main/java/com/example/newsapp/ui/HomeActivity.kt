package com.example.newsapp.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.model.Category
import com.example.newsapp.ui.category.CategoryFragment
import com.example.newsapp.ui.news.NewsFragment
import com.example.newsapp.ui.settings.SettingsFragment

class HomeActivity : AppCompatActivity() {

    lateinit var menuBt:ImageView
    lateinit var drawerLayout: DrawerLayout
    lateinit var categoryText : TextView
    lateinit var settingsText : TextView

    val categoryFragment = CategoryFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initView()
        pushFragment(categoryFragment)

    }

    fun initView(){
        menuBt = findViewById(R.id.iconMenu)
        drawerLayout = findViewById(R.id.drawer_layout)
        categoryText  = findViewById(R.id.CategortyText)
        settingsText = findViewById(R.id.SettingsText)

        menuBt.setOnClickListener{
            drawerLayout.open()
        }

        categoryText.setOnClickListener{
            pushFragment(categoryFragment)
        }

        settingsText.setOnClickListener{
            pushFragment(SettingsFragment())
        }

        categoryFragment.onCategoryClickListener = object :CategoryFragment.OnCategoryClickListener{
            override fun onCategoryClick(category: Category) {
                pushFragment(NewsFragment(category))
            }

        }
    }

    fun pushFragment(fragment:Fragment){

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer,fragment)
            .commit()

        drawerLayout.close()
    }
}