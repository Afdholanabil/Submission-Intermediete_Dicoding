package com.example.submission_intermediete_dicoding.ui.viewmodel

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submission_intermediete_dicoding.repository.MyStoryRepository
import com.example.submission_intermediete_dicoding.util.LoginPreference

class MainActivityViewModel(private val storyRepository: MyStoryRepository) : ViewModel() {

    val storyCount : LiveData<Int> = storyRepository.getStoryCount()
}