package com.example.submission_intermediete_dicoding.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.example.submission_intermediete_dicoding.R
import com.example.submission_intermediete_dicoding.data.response.LoginResponse
import com.example.submission_intermediete_dicoding.databinding.ActivityMainBinding
import com.example.submission_intermediete_dicoding.ui.adapter.SectionPageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val loginData = intent.getParcelableExtra<LoginResponse>("id_login")

        Log.d(TAG, "loginData : $loginData")
        Log.d(TAG, "loginData-name : ${loginData?.loginResult?.name}")

        binding.toolbar.setNavigationOnClickListener{
            startActivity(Intent(this@MainActivity, SettingActivity::class.java))
        }

        val sectionPageAdapter = SectionPageAdapter(this)
        val viewPager : ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPageAdapter
        val tabs : TabLayout = binding.tabs
        TabLayoutMediator(tabs,viewPager){tab, position -> tab.text = resources.getString(
            TAB_TITLES[position]
        )}.attach()

        supportActionBar?.elevation = 0f

        binding.btnAddStory.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }

        binding.toolbar.title = ("Selamat Datang, " + loginData?.loginResult?.name) ?: "guest"



    }


    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        private const val TAG = "MainActivity"
    }
}