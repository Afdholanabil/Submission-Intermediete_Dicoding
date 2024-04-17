package com.example.submission_intermediete_dicoding.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submission_intermediete_dicoding.ui.view.fragment.ListFragment
import com.example.submission_intermediete_dicoding.ui.view.fragment.MyListFragment

class SectionPageAdapter(activity : AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position) {
            0 -> fragment = MyListFragment()
            1 -> fragment = ListFragment()
        }
        return fragment as Fragment
    }
}