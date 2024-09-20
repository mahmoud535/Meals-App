package com.example.mealsapp.domain.repository

import com.example.mealsapp.domain.model.User

interface LoginRepository {
    suspend fun getUserByEmail(email: String): User?
    suspend fun insertUser(user: User)
}