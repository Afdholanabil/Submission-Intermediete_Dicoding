package com.example.submission_intermediete_dicoding.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submission_intermediete_dicoding.database.AllStory.AllStory
import com.example.submission_intermediete_dicoding.database.AllStory.AllStoryDao
import com.example.submission_intermediete_dicoding.database.AllStory.AllStoryRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AllStoryRepository(application: Application) {
    private val mAllStoryDao :AllStoryDao
    private val executorService :ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db =AllStoryRoomDatabase.getDatabase(application)
        mAllStoryDao =db.allStoryDao()
    }

    fun getAllStory(): LiveData<List<AllStory>> = mAllStoryDao.getAllStory()

    fun getStoryById(id: String): LiveData<AllStory> = mAllStoryDao.getStoryFromId(id)


    fun insert(allStory: AllStory){
        executorService.execute { mAllStoryDao.insert(allStory) }
    }

    fun delete(allStory: AllStory){
        executorService.execute { mAllStoryDao.deleteStoryById(allStory) }
    }

}