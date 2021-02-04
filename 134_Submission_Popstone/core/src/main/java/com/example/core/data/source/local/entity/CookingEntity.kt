package com.example.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "cooking")
data class CookingEntity(
        @PrimaryKey
        @ColumnInfo(name = "cookingID")
        val cookingID: String,

        @ColumnInfo(name = "title")
        val title: String?,

        @ColumnInfo(name = "thumb")
        val thumb: String?,

        @ColumnInfo(name = "times")
        val times: String?,

        @ColumnInfo(name = "portion")
        val portion: String?,

        @ColumnInfo(name = "difficulty")
        val difficulty: String?,

        @ColumnInfo(name = "tags")
        val tags: String? = null,
) : Parcelable