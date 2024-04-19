package com.example.submission_intermediete_dicoding.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.submission_intermediete_dicoding.R
import com.example.submission_intermediete_dicoding.databinding.ActivityOnBoardBinding
import com.example.submission_intermediete_dicoding.ui.adapter.OnBoardInteractionListener
import com.example.submission_intermediete_dicoding.ui.adapter.OnBoardPageAdaprter
import com.example.submission_intermediete_dicoding.ui.viewmodel.OnBoardViewModel
import com.example.submission_intermediete_dicoding.ui.viewmodel.onBoardViewModelFactory
import com.example.submission_intermediete_dicoding.util.OnBoardPreference
import com.example.submission_intermediete_dicoding.util.datastore

class OnBoardActivity : AppCompatActivity(), OnBoardInteractionListener {

    private lateinit var binding: ActivityOnBoardBinding
    private lateinit var pagerAdapter: OnBoardPageAdaprter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val pref =OnBoardPreference.getInstance(application.datastore)

        val onBoardViewModel = ViewModelProvider(this, onBoardViewModelFactory(pref)).get(
            OnBoardViewModel::class.java
        )
        pagerAdapter = OnBoardPageAdaprter(this)
        pagerAdapter.setOnBoardInteractionListener(this)
        binding.vpOnboard.adapter = pagerAdapter

        supportActionBar?.elevation = 0f
    }

    override fun moveToNextPage() {
        val nextPage = binding.vpOnboard.currentItem + 1
        if (nextPage < pagerAdapter.itemCount) {
            binding.vpOnboard.setCurrentItem(nextPage, true)
        } else {
            finish()
        }
    }

    override fun finishOnBoarding() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}