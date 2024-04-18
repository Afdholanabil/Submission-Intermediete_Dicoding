package com.example.submission_intermediete_dicoding.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submission_intermediete_dicoding.ui.view.activity.LoginActivity
import com.example.submission_intermediete_dicoding.ui.view.fragment.OnBoardFirstScreen
import com.example.submission_intermediete_dicoding.ui.view.fragment.OnBoardSecondScreen
import com.example.submission_intermediete_dicoding.util.LoginPreference
import com.example.submission_intermediete_dicoding.util.OnBoardPreference

class OnBoardPageAdaprter(private val pref : OnBoardPreference, activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position) {
            0 -> fragment = OnBoardFirstScreen()
            1 -> OnBoardSecondScreen
        }

        return fragment as Fragment
    }
}