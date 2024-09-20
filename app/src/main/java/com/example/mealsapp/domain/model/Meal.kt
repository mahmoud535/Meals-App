package com.example.mealsapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "meals")
data class Meal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val day: String,
    val name: String,
    val description: String,
    val imageUri: String
) : Parcelable