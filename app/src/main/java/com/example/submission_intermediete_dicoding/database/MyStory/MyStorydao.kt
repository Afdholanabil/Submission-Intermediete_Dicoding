package com.example.submission_intermediete_dicoding.database.MyStory

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.submission_intermediete_dicoding.database.AllStory.AllStory
import com.example.submission_intermediete_dicoding.database.MyStory.MyStory

@Dao
interface MyStorydao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(myStory: MyStory)

    @Update
    fun update(myStory: MyStory)

    @Delete
    fun deleteMyStoryById(myStory: MyStory)

    @Query("SELECT * from mystory ORDER BY id = id")
    fun getAllMyStory(): LiveData<List<MyStory>>

    @Query("SELECT * FROM mystory where id = :id")
    fun getMyStoryById(id: String): LiveData<MyStory>
}