package com.example.submission_intermediete_dicoding.database.myStory

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MyStorydao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(myStory: MyStory)

    @Update
    fun update(myStory: MyStory)

    @Delete
    fun deleteMyStoryById(myStory: MyStory)

    @Query("SELECT * from mystory")
    fun getAllMyStory(): LiveData<List<MyStory>>

    @Query("SELECT * FROM mystory where id = :id")
    fun getMyStoryById(id: String): LiveData<MyStory>

    @Query("SELECT COUNT(*) from mystory")
    fun getStoryCount(): LiveData<Int>
}