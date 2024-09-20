package com.example.mealsapp.data.repository

import com.example.mealsapp.data.local.MealDatabase
import com.example.mealsapp.data.local.UserDao
import com.example.mealsapp.domain.model.User
import com.example.mealsapp.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImp @Inject constructor(private val mealDatabase: MealDatabase): LoginRepository {
    override suspend fun getUserByEmail(email: String): User? {
        return mealDatabase.userDao().getUserByEmail(email)
    }

    override suspend fun insertUser(user: User) {
        mealDatabase.userDao().insertUser(user)
    }
}