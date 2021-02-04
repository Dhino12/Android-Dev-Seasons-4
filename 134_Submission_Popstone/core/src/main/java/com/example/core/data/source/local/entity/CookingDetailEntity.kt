package com.example.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "cookingDetail")
data class CookingDetailEntity(
        val servings: String? = null,

        val times: String? = null,

        val ingredient: String? = null,

        val thumb: String? = null,

        val author: String? = null,

        val step: String? = null,

        @PrimaryKey
        val title: String,

        val difficulty: String? = null,

        val desc: String? = null,

        var isFavorite: Boolean = false
) : Parcelable