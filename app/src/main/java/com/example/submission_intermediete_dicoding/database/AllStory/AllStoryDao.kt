package com.example.submission_intermediete_dicoding.database.AllStory

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
    fun deleteFavoriteUserByUsername(allStory: AllStory)

    @Query("SELECT * from allstory ORDER BY id = id")
    fun getAllFavoriteUser(): LiveData<List<AllStory>>
}