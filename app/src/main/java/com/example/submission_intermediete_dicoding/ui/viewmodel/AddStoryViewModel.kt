package com.example.submission_intermediete_dicoding.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.submission_intermediete_dicoding.database.myStory.MyStory
import com.example.submission_intermediete_dicoding.repository.MyStoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val storyRepository: MyStoryRepository) : ViewModel() {
    suspend fun postStory(desc : RequestBody,photoFile : MultipartBody.Part,lat: Double?, lon : Double?, myStory: MyStory) {

        storyRepository.addStory(desc, photoFile,lat,lon,myStory)
    }

}