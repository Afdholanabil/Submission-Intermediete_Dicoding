package com.example.submission_intermediete_dicoding.database.AllStory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AllStory::class], version = 1)
abstract class AllStoryRoomDatabase : RoomDatabase() {
    abstract fun allStoryDao(): AllStoryDao
    companion object {
        @Volatile
        private var INSTANCE : AllStoryRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AllStoryRoomDatabase {
            if (INSTANCE == null) {
                synchronized(AllStoryRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,AllStoryRoomDatabase::class.java,"all_story_database")
                        .build()
                }
            }
            return INSTANCE as AllStoryRoomDatabase
        }

    }
}