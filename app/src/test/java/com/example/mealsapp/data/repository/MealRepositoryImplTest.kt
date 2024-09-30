package com.example.mealsapp.data.repository

import org.junit.Assert.*

import com.example.mealsapp.data.local.MealDatabase
import com.example.mealsapp.data.local.MealDao
import com.example.mealsapp.domain.model.Meal
import com.example.mealsapp.domain.model.ShoppingCartItem
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.example.mealsapp.R


/*
-- When meals exist for the provided day, return the list of meals.
-- When no meals exist for the provided day, return an empty list.
-- When insertMeal is called, the meal should be inserted into the database.
-- When cart items exist for the provided email, return the list of items.
-- When no cart items exist for the provided email, return an empty list.
-- When addToCart is called, the item should be added to the cart.
-- Check if getImageList returns the expected list of image resources.
*/

class MealRepositoryImplTest {

    private lateinit var mockMealDatabase: MealDatabase
    private lateinit var mockMealDao: MealDao
    private lateinit var mealRepository: MealRepositoryImpl

    @Before
    fun setup() {
        mockMealDatabase = mockk()
        mockMealDao = mockk()
        mealRepository = MealRepositoryImpl(mockMealDatabase)
        every { mockMealDatabase.mealDao() } returns mockMealDao
    }

    @Test
    fun `when getMealsForDay is called and meals exist for the day then return meal list`() = runBlocking {
        // Arrange
        val day = "Monday"
        val expectedMeals = listOf(
            Meal(id = 1, day = day, name = "Pizza", description = "Delicious pizza", imageUri = "sample_uri"),
            Meal(id = 2, day = day, name = "Burger", description = "Tasty burger", imageUri = "sample_uri")
        )
        coEvery { mockMealDao.getMealsForDay(day) } returns expectedMeals

        // Act
        val result = mealRepository.getMealsForDay(day)

        // Assert
        assertEquals(expectedMeals, result)
        coVerify { mockMealDao.getMealsForDay(day) }
    }

    @Test
    fun `when getMealsForDay is called and no meals exist for the day then return empty list`() = runBlocking {
        // Arrange
        val day = "Monday"
        coEvery { mockMealDao.getMealsForDay(day) } returns emptyList()

        // Act
        val result = mealRepository.getMealsForDay(day)

        // Assert
        assertTrue(result.isEmpty())
        coVerify { mockMealDao.getMealsForDay(day) }
    }

    @Test
    fun `when insertMeal is called then insert meal into database`() = runBlocking {
        // Arrange
        val meal = Meal(id = 1, day = "Monday", name = "Pizza", description = "Delicious pizza", imageUri = "sample_uri" )
        coEvery { mockMealDao.addMeal(meal) } just Runs

        // Act
        mealRepository.insertMeal(meal)

        // Assert
        coVerify { mockMealDao.addMeal(meal) }
    }


    @Test
    fun `when getCartItems is called and items exist for the user then return cart items`() = runBlocking {
        // Arrange
        val userEmail = "test@example.com"
        val expectedCartItems = listOf(ShoppingCartItem(id = 1, mealId = 2, name = "Pizza", description = "Delicious pizza", imageUri = "sample_uri", userEmail = userEmail))
        coEvery { mockMealDao.getCartItem(userEmail) } returns expectedCartItems

        // Act
        val result = mealRepository.getCartItems(userEmail)

        // Assert
        assertEquals(expectedCartItems, result)
        coVerify { mockMealDao.getCartItem(userEmail) }
    }

    @Test
    fun `when getCartItems is called and no items exist for the user then return empty list`() = runBlocking {
        // Arrange
        val userEmail = "test@example.com"
        coEvery { mockMealDao.getCartItem(userEmail) } returns emptyList()

        // Act
        val result = mealRepository.getCartItems(userEmail)

        // Assert
        assertTrue(result.isEmpty())
        coVerify { mockMealDao.getCartItem(userEmail) }
    }

    @Test
    fun `when addToCart is called then add item to cart`() = runBlocking {
        // Arrange
        val cartItem = ShoppingCartItem(id = 1, mealId = 2, name = "Pizza", description = "Delicious pizza", imageUri = "sample_uri", userEmail = "mahmoud@gmail.com")
        coEvery { mockMealDao.addItemToCart(cartItem) } just Runs

        // Act
        mealRepository.addToCart(cartItem)

        // Assert
        coVerify { mockMealDao.addItemToCart(cartItem) }
    }

    @Test
    fun `when getImageList is called then return list of images`() {
        // Arrange
        val expectedImages = listOf(R.drawable.img16, R.drawable.img17, R.drawable.img4)

        // Act
        val result = mealRepository.getImageList()

        // Assert
        assertEquals(expectedImages, result)
    }



}
