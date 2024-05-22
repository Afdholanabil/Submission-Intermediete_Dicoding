package com.example.submission_intermediete_dicoding.database.myStory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

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
                        .build()
                }
            }
            return INSTANCE as MyStoryRoomDatabase
        }

    }
}