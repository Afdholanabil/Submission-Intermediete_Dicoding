package com.example.submission_intermediete_dicoding.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.submission_intermediete_dicoding.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userI = intent.getStringExtra(TAG_USERNAME)
        val photoI = intent.getStringExtra(TAG_PHOTO)
        val descI = intent.getStringExtra(TAG_DESC)
        Log.d(TAG,"Data: $userI, $photoI, $descI")

        binding.tvUsername.text = userI
        binding.tvUsernameDesc.text = "$userI |"
        Glide.with(this).load(photoI).into(binding.imgContentDetail)
        binding.yvDescDetail.text = descI
    }

    companion object {
        private const val TAG_USERNAME = "username"
        private const val TAG_PHOTO = "photoUrl"
        private const val TAG_DESC = "description"
        private const val TAG = "DetailActivity"
    }
}