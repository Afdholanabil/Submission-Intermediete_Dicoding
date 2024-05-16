package com.example.submission_intermediete_dicoding.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission_intermediete_dicoding.data.response.AddStoryResponse
import com.example.submission_intermediete_dicoding.data.response.ListStoryItem
import com.example.submission_intermediete_dicoding.data.response.StoryResponse
import com.example.submission_intermediete_dicoding.database.MyStory.MyStory
import com.example.submission_intermediete_dicoding.repository.MyStoryRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class StoryViewModel(private val storyRepository: MyStoryRepository) : ViewModel() {

    val allMyStories: LiveData<List<MyStory>> = storyRepository.getAllMyStory()
    private val _addStoryResult = MutableLiveData<Result<AddStoryResponse>>()
    val addStoryResult: LiveData<Result<AddStoryResponse>> get() = _addStoryResult

    private val _stories = MutableLiveData<List<ListStoryItem>?>()
    val stories: LiveData<List<ListStoryItem>?> get() = _stories

    fun getStories() {
        viewModelScope.launch {
            try {
                val response = storyRepository.getStories()
                if (!response.error!!) {
                    _stories.postValue(response.listStory)
                } else {
                    Log.d(TAG, "Error : else")
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error : $e")
            }
        }
    }

    fun addStory(description: String, photoFile: File, lat: Double?, lon: Double?, myStory: MyStory) {
        viewModelScope.launch {
            try {

                val requestFile = photoFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val photo = MultipartBody.Part.createFormData("photo", photoFile.name, requestFile)
                storyRepository.addStory(description, photo, lat, lon, myStory)
            } catch (e: Exception) {
                // Handle the error appropriately
            }
        }
    }

    companion object {
        private const val TAG = "StoryViewModel"
    }
}