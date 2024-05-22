package com.example.submission_intermediete_dicoding.database.myStory

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class MyStory(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int = 0,

    @ColumnInfo(name = "description")
    var desc : String? = null,

    @ColumnInfo(name = "photoUrl")
    var photoUrl : String? = null,

    @ColumnInfo(name = "Lat")
    var lat : Double? = null,

    @ColumnInfo(name = "Lon")
    var lon : Double? = null
) : Parcelable