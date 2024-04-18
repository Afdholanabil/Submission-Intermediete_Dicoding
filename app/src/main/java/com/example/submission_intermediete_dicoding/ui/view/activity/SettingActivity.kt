package com.example.submission_intermediete_dicoding.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.submission_intermediete_dicoding.R
import com.example.submission_intermediete_dicoding.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }
}