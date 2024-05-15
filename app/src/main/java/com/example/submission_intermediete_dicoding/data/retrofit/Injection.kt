package com.example.submission_intermediete_dicoding.data.retrofit

import android.content.Context
import com.example.submission_intermediete_dicoding.repository.UserRepository

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository(apiService)
    }

//    fun provideStoryRepository(context: Context): StoryRepository {
//        val pref = LoginPreference.getInstance(context.datastore)
//        val token = runBlocking { pref.getLoginSession().first()?.loginResult?.token }
//        val apiService = ApiConfig.getApiService(token)
//        return StoryRepository.getInstance(apiService, pref)
//    }
}