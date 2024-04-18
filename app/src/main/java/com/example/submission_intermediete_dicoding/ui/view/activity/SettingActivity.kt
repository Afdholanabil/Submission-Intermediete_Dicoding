package com.example.submission_intermediete_dicoding.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submission_intermediete_dicoding.R
import com.example.submission_intermediete_dicoding.databinding.ActivitySettingBinding
import com.example.submission_intermediete_dicoding.ui.viewmodel.SettingViewModel
import com.example.submission_intermediete_dicoding.ui.viewmodel.ViewModelFactory
import com.example.submission_intermediete_dicoding.util.LoginPreference
import com.example.submission_intermediete_dicoding.util.datastore

class SettingActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = LoginPreference.getInstance(application.datastore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )
        binding.btnLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            settingViewModel.saveLoginSession(false)
        }
    }
}