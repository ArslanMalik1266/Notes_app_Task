package com.example.notesapptask


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.notesapptask.fragments.add_item
import com.example.notesapptask.fragments.notes_list


class ViewPagerAdapter(activity:FragmentActivity ) : FragmentStateAdapter(activity) {
    private val fragments = listOf(
        add_item(),
        notes_list()
    )

    override fun getItemCount() = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]
}