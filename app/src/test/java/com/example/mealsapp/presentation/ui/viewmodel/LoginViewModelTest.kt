package com.example.mealsapp.presentation.ui.viewmodel

import org.junit.Assert.*

import com.example.mealsapp.domain.model.User
import com.example.mealsapp.domain.usecase.GetUserByEmailUseCase
import com.example.mealsapp.domain.usecase.InsertUserUseCase
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

/*
-- when login is called and user does not exist then insertUserUseCase is called
-- when login is called and user exists with the same role then return success state
-- when login is called and user exists with different role then return error state
*/

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private lateinit var getUserByEmailUseCase: GetUserByEmailUseCase
    private lateinit var insertUserUseCase: InsertUserUseCase
    private lateinit var loginViewModel: LoginViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        getUserByEmailUseCase = mockk()
        insertUserUseCase = mockk()
        loginViewModel = LoginViewModel(getUserByEmailUseCase, insertUserUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `when login is called and user does not exist then insertUserUseCase is called`() = runBlocking {
        // Arrange
        val email = "test@example.com"
        val password = "password"
        val role = "Guest"
        coEvery { getUserByEmailUseCase(email) } returns null
        coEvery { insertUserUseCase(any()) } just Runs

        // Act
        loginViewModel.login(email, password, role)

        // Assert
        coVerify { insertUserUseCase(User(email, password, role)) }
        assertEquals(LoginState.Success(role, email), loginViewModel.loginState.value)
    }

    @Test
    fun `when login is called and user exists with the same role then return success state`() = runBlockingTest {
        // Arrange
        val email = "test@example.com"
        val password = "password"
        val role = "Admin"
        val existingUser = User(email = email, password = password, role = role)
        coEvery { getUserByEmailUseCase(email) } returns existingUser

        // Act
        loginViewModel.login(email, password, role)
        advanceUntilIdle()

        // Assert
        assertEquals(LoginState.Success(role, email), loginViewModel.loginState.value)
    }


    @Test
    fun `when login is called and user exists with different role then return error state`() = runBlocking {
        // Arrange
        val email = "test@example.com"
        val password = "password"
        val role = "Admin"
        val existingUser = User(email = email, password = password, role = "Guest")
        coEvery { getUserByEmailUseCase(email) } returns existingUser

        // Act
        loginViewModel.login(email, password, role)

        // Assert
        assertEquals(LoginState.Error("Cannot log in as $role with the same email"), loginViewModel.loginState.value)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}
