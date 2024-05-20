package com.example.submission_intermediete_dicoding.database.MyStory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submission_intermediete_dicoding.database.AllStory.AllStoryDao
import com.example.submission_intermediete_dicoding.database.AllStory.AllStoryRoomDatabase


@Database(entities = [MyStory::class], version = 1)
abstract class MyStoryRoomDatabase : RoomDatabase() {
    abstract fun myStoryDao(): MyStorydao
    companion object {
        @Volatile
        private var INSTANCE : MyStoryRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MyStoryRoomDatabase {
            if (INSTANCE == null) {
                synchronized(MyStoryRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MyStoryRoomDatabase::class.java,"my_story_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as MyStoryRoomDatabase
        }

    }
}