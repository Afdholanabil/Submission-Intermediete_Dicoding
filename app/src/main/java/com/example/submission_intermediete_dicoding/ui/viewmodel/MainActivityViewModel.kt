package com.example.submission_intermediete_dicoding.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submission_intermediete_dicoding.repository.MyStoryRepository

class MainActivityViewModel(private val storyRepository: MyStoryRepository) : ViewModel() {

    val storyCount : LiveData<Int> = storyRepository.getStoryCount()
}