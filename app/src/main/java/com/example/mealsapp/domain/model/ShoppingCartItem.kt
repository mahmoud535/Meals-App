package com.example.mealsapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shoppingCart")
data class ShoppingCartItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mealId: Int,
    val name: String,
    val description: String,
    val imageUri: String
)
