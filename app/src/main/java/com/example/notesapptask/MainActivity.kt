package com.example.notesapptask

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.notesapptask.fragments.add_item
import com.example.notesapptask.fragments.notes_list
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var viewPager: ViewPager2
    private lateinit var toolbarTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)



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