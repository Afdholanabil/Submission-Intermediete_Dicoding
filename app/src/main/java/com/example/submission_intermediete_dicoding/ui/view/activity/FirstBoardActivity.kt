package com.example.submission_intermediete_dicoding.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.submission_intermediete_dicoding.R
import com.example.submission_intermediete_dicoding.databinding.ActivityFirstBoardBinding
import com.google.android.material.snackbar.Snackbar

class FirstBoardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFirstBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.tvToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.apple.setOnClickListener {
            Snackbar.make(window.decorView.rootView,R.string.Message_notYetAvailable, Snackbar.LENGTH_SHORT).show()
        }

        binding.facebook.setOnClickListener {
            Snackbar.make(window.decorView.rootView,R.string.Message_notYetAvailable, Snackbar.LENGTH_SHORT).show()
        }
        binding.google.setOnClickListener {
            Snackbar.make(window.decorView.rootView,R.string.Message_notYetAvailable, Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val TAG = "FirstBoard Activity"

    }
}