package com.example.mealsapp.data.repository

import com.example.mealsapp.data.local.MealDatabase
import com.example.mealsapp.data.local.UserDao
import com.example.mealsapp.domain.model.User
import org.junit.Assert.*

import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/*
-- when getUserByEmail is called and user exists then return user
-- when getUserByEmail is called and user does not exist then return null
-- when insertUser is called then insert user into database
 */
class LoginRepositoryImplTest {

    private lateinit var mockMealDatabase: MealDatabase
    private lateinit var mockUserDao: UserDao
    private lateinit var loginRepository: LoginRepositoryImp

    @Before
    fun setup() {
        mockMealDatabase = mockk()
        mockUserDao = mockk()
        loginRepository = LoginRepositoryImp(mockMealDatabase)
        every { mockMealDatabase.userDao() } returns mockUserDao
    }

    @Test
    fun `when getUserByEmail is called and user exists then return user`() = runBlocking {
        // Arrange
        val email = "test@example.com"
        val expectedUser = User(email = "mahmoud@gmail.com", password = "123456",  role = "Guest")
        coEvery { mockUserDao.getUserByEmail(email) } returns expectedUser

        // Act
        val result = loginRepository.getUserByEmail(email)

        // Assert
        assertEquals(expectedUser, result)
        coVerify { mockUserDao.getUserByEmail(email) }
    }

    @Test
    fun `when getUserByEmail is called and user does not exist then return null`() = runBlocking {
        // Arrange
        val email = "nonexistent@example.com"
        coEvery { mockUserDao.getUserByEmail(email) } returns null

        // Act
        val result = loginRepository.getUserByEmail(email)

        // Assert
        assertNull(result)
        coVerify { mockUserDao.getUserByEmail(email) }
    }

    @Test
    fun `when insertUser is called then insert user into database`() = runBlocking {
        // Arrange
        val user = User(email = "mahmoud@gmail.com", password = "123456",  role = "Guest")
        coEvery { mockUserDao.insertUser(user) } just Runs

        // Act
        loginRepository.insertUser(user)

        // Assert
        coVerify { mockUserDao.insertUser(user) }
    }
}

