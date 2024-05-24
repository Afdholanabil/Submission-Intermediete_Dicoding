package com.example.submission_intermediete_dicoding.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submission_intermediete_dicoding.util.LoginPreference

class ViewModelFactory(private val preferences: LoginPreference): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHEKED_CAST")

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(preferences) as T
        } else if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(preferences) as T
        }
        throw IllegalArgumentException("Unknown View Model Class:" + modelClass.name)
    }
}