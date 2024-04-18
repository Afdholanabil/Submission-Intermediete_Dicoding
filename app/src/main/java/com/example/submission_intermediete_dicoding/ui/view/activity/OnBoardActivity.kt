package com.example.submission_intermediete_dicoding.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.submission_intermediete_dicoding.R
import com.example.submission_intermediete_dicoding.databinding.ActivityOnBoardBinding
import com.example.submission_intermediete_dicoding.ui.adapter.OnBoardPageAdaprter
import com.example.submission_intermediete_dicoding.util.OnBoardPreference
import com.example.submission_intermediete_dicoding.util.datastore

class OnBoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val pref =OnBoardPreference.getInstance(application.datastore)
        val onBoardPagerAdapter = OnBoardPageAdaprter(pref, this)
        val viewPager : ViewPager2 = binding.vpOnboard
        viewPager.adapter = onBoardPagerAdapter

        supportActionBar?.elevation = 0f
    }
}