package com.example.mealsapp.presentation.ui.viewmodel

import org.junit.Assert.*

import com.example.mealsapp.domain.model.Meal
import com.example.mealsapp.domain.model.ShoppingCartItem
import com.example.mealsapp.domain.usecase.AddToCartUseCase
import com.example.mealsapp.domain.usecase.GetCartItemsUseCase
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
-- when addToCart is called and cart is not empty then do not add to cart and set error state
*/
@ExperimentalCoroutinesApi
class MealDetailsViewModelTest {

    private lateinit var getCartItemsUseCase: GetCartItemsUseCase
    private lateinit var addToCartUseCase: AddToCartUseCase
    private lateinit var mealDetailsViewModel: MealDetailsViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        getCartItemsUseCase = mockk()
        addToCartUseCase = mockk()
        mealDetailsViewModel = MealDetailsViewModel(getCartItemsUseCase, addToCartUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `when addToCart is called and cart is not empty then do not add to cart and set error state`() = runBlockingTest {
        // Arrange
        val meal = Meal(id = 1, day = "Monday", name = "Pizza", description = "Delicious pizza", imageUri = "sample_uri")
        val userEmail = "test@example.com"
        val cartItems = listOf(ShoppingCartItem(id = 1, mealId = meal.id, name = meal.name, description = meal.description, imageUri = meal.imageUri, userEmail = userEmail,  dateAdded = "2023-07-01"))
        coEvery { getCartItemsUseCase(userEmail) } returns cartItems

        // Act
        mealDetailsViewModel.addToCart(meal, userEmail)
        advanceUntilIdle()

        // Assert
        coVerify(exactly = 0) { addToCartUseCase(any()) }
        assertTrue(mealDetailsViewModel.cartState.value is CartState.Error)
        val errorState = mealDetailsViewModel.cartState.value as CartState.Error
        assertEquals("No time-zone data files registered", errorState.message)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}

