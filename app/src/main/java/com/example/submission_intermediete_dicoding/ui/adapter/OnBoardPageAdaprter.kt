package com.example.submission_intermediete_dicoding.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submission_intermediete_dicoding.ui.view.activity.LoginActivity
import com.example.submission_intermediete_dicoding.util.LoginPreference

class OnBoardPageAdaprter(private val pref : LoginPreference, activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        TODO("Not yet implemented")
    }
}