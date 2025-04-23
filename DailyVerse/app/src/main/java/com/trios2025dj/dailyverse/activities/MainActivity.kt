package com.trios2025dj.dailyverse.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.trios2025dj.dailyverse.R
import com.trios2025dj.dailyverse.fragments.CategoriesFragment
import com.trios2025dj.dailyverse.fragments.FavoritesFragment
import com.trios2025dj.dailyverse.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        nav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> switchFragment(HomeFragment())
                R.id.nav_categories -> switchFragment(CategoriesFragment())
                R.id.nav_favorites -> switchFragment(FavoritesFragment())
            }
            true
        }

        // Load default fragment
        switchFragment(HomeFragment())
    }

    private fun switchFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}