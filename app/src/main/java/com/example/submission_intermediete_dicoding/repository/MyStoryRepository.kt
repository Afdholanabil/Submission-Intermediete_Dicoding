package com.example.submission_intermediete_dicoding.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submission_intermediete_dicoding.database.AllStory.AllStory
import com.example.submission_intermediete_dicoding.database.AllStory.AllStoryDao
import com.example.submission_intermediete_dicoding.database.AllStory.AllStoryRoomDatabase
import com.example.submission_intermediete_dicoding.database.MyStory.MyStory
import com.example.submission_intermediete_dicoding.database.MyStory.MyStoryRoomDatabase
import com.example.submission_intermediete_dicoding.database.MyStory.MyStorydao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MyStoryRepository(application: Application) {
    private val mMyStoryDao : MyStorydao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = MyStoryRoomDatabase.getDatabase(application)
        mMyStoryDao =db.myStoryDao()
    }

    fun getAllMyStory(): LiveData<List<MyStory>> = mMyStoryDao.getAllMyStory()

    fun getMyStoryById(id: String): LiveData<MyStory> = mMyStoryDao.getMyStoryById(id)


    fun insert(myStory: MyStory){
        executorService.execute { mMyStoryDao.insert(myStory) }
    }

    fun delete(myStory: MyStory){
        executorService.execute { mMyStoryDao.deleteMyStoryById(myStory) }
    }
}