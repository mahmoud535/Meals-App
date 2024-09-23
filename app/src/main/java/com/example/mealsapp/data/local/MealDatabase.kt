package com.example.mealsapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mealsapp.domain.model.Meal
import com.example.mealsapp.domain.model.ShoppingCartItem
import com.example.mealsapp.domain.model.User
import dagger.hilt.android.qualifiers.ApplicationContext

private const val  DATABASE_NAME = "user_database"
@Database(entities = [Meal::class, ShoppingCartItem::class, User::class], version = 1, exportSchema = false)
abstract class MealDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: MealDatabase? = null

        fun getDatabase(@ApplicationContext context: Context): MealDatabase {
            return instance?: synchronized(Any()){
                instance ?: buildDatabase(context).also{ instance = it}
            }
        }

        private  fun buildDatabase(@ApplicationContext context: Context): MealDatabase {
            return Room.databaseBuilder(
                context.applicationContext, MealDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}