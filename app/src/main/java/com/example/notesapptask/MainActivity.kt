package com.example.notesapptask

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var viewPager: ViewPager2
    private lateinit var toolbarTitle: TextView
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var navigationView : NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainContent)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)

        val menuButton = findViewById<ImageView>(R.id.profileDrawer)
        navigationView = findViewById<NavigationView>(R.id.navigationView)


        menuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_Add -> viewPager.currentItem = 0
                R.id.nav_notes -> viewPager.currentItem = 1
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


        viewPager = findViewById<ViewPager2>(R.id.viewPager)

        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter
        toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)

        bottomNav = findViewById<BottomNavigationView>(R.id.bottomAppBar)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_notes -> {
                    viewPager.currentItem = 1
                    toolbarTitle.text = "Notes"
                    true
                }

                R.id.action_add_note -> {
                    viewPager.currentItem = 0
                    toolbarTitle.text = "Add Note"
                    true
                }

                else -> false
            }
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottomNav.menu.getItem(position).isChecked = true

                toolbarTitle.text = when (position) {
                    0 -> "Add Note"
                    1 -> "Notes"
                    else -> ""
                }
            }
        })
    }

}