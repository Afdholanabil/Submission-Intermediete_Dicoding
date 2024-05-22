package com.example.submission_intermediete_dicoding.database.allStory

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface AllStoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(allStory: AllStory)

    @Update
    fun update(allStory: AllStory)

    @Delete
    fun deleteStoryById(allStory: AllStory)

    @Query("SELECT * from allstory ORDER BY id = id")
    fun getAllStory(): LiveData<List<AllStory>>

    @Query("SELECT * FROM allstory where id = :id")
    fun getStoryFromId(id: String): LiveData<AllStory>
}