package com.example.submission_intermediete_dicoding.database.MyStory

import android.net.Uri
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
    var id : String,

    @ColumnInfo(name = "name")
    var name : String? = null,

    @ColumnInfo(name = "description")
    var desc : String? = null,

    @ColumnInfo(name = "photoUrl")
    var photoUrl : String? = null,

    @ColumnInfo(name = "Lat")
    var lat : Double? = null,

    @ColumnInfo(name = "Lon")
    var lon : Double? = null
) : Parcelable