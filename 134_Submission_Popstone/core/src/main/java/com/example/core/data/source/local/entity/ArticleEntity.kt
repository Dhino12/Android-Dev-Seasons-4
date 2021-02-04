package com.example.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "article")
data class ArticleEntity(
        @PrimaryKey
        @ColumnInfo(name = "key")
        var key: String,

        @ColumnInfo(name = "title")
        var title: String?,

        @ColumnInfo(name = "thumb")
        var thumb: String?,

        @ColumnInfo(name = "tags")
        var tags: String?,

        @ColumnInfo(name = "favorite")
        var isFavorite: Boolean
) : Parcelable