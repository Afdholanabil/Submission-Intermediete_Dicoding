package com.example.submission_intermediete_dicoding.data.retrofit

import android.content.Context
import com.example.submission_intermediete_dicoding.database.MyStory.MyStoryRoomDatabase
import com.example.submission_intermediete_dicoding.repository.MyStoryRepository
import com.example.submission_intermediete_dicoding.repository.UserRepository
import com.example.submission_intermediete_dicoding.util.LoginPreference
import com.example.submission_intermediete_dicoding.util.dataStore
import com.example.submission_intermediete_dicoding.util.datastore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository(apiService)
    }

    fun provideStoryRepository(context: Context): MyStoryRepository {
        val apiService = ApiConfig.getApiService()
        val loginPreference = LoginPreference.getInstance(context.dataStore)
        val database = MyStoryRoomDatabase.getDatabase(context)
        return MyStoryRepository.getInstance(apiService, loginPreference, database.myStoryDao())
    }
}