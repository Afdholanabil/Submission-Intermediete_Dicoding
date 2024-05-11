package com.example.submission_intermediete_dicoding.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submission_intermediete_dicoding.util.OnBoardPreference

class OnBoardFactory(private val preference : OnBoardPreference): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OnBoardViewModel::class.java)) {
            return OnBoardViewModel(preference) as T
        }
        throw IllegalArgumentException("Unknown View MOdel Class: ${modelClass.name}")
    }
}