package com.example.submission_intermediete_dicoding.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
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
            settingViewModel.logout()
        }

        settingViewModel.getLoginSession().observe(this) { token ->
            if (token == null) {
                navigateToLoginActivity()
                Log.d(TAG,"token-setting: $token")
            } else {
                Log.d(TAG,"token-setting: $token")
            }
        }
    }


    private fun navigateToLoginActivity() {
        val intent = Intent(this, FirstBoardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()

    }
    companion object {
        private const val TAG = "SettingActivity"
    }
}