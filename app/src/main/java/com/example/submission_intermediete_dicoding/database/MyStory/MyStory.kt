package com.example.submission_intermediete_dicoding.database.MyStory

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class MyStory(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    private val id : String,

    @ColumnInfo(name = "name")
    private val name : String? = null,

    @ColumnInfo(name = "description")
    private val desc : String? = null,

    @ColumnInfo(name = "photoUrl")
    private val photoUrl : String? = null,

    @ColumnInfo(name = "Lat")
    private val lat : Double? = null,

    @ColumnInfo(name = "Lon")
    private val lon : Double? = null
) : Parcelable