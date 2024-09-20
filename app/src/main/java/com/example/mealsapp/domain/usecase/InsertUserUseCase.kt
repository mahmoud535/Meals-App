package com.example.mealsapp.domain.usecase

import com.example.mealsapp.domain.model.User
import com.example.mealsapp.domain.repository.LoginRepository
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(user: User) {
        loginRepository.insertUser(user)
    }
}