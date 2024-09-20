package com.example.mealsapp.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealsapp.data.repository.MealRepositoryImpl
import com.example.mealsapp.domain.model.Meal
import com.example.mealsapp.domain.usecase.GetImageListUseCase
import com.example.mealsapp.domain.usecase.GetMealsForDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MealsViewModel @Inject constructor(
    private val getMealsForDayUseCase: GetMealsForDayUseCase,
    private val getImageListUseCase: GetImageListUseCase
) : ViewModel() {
    private val _meals = MutableStateFlow<List<Meal>>(emptyList())
    val meals: StateFlow<List<Meal>> get() = _meals

    fun loadMealsForCurrentDay() {
        viewModelScope.launch {
            val currentDayMeals = getMealsForDayUseCase(getCurrentDay())
            _meals.value = currentDayMeals
        }
    }

    fun getImageList(): List<Int> {
        return getImageListUseCase()
    }

    private fun getCurrentDay(): String {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "Sunday"
            Calendar.MONDAY -> "Monday"
            Calendar.TUESDAY -> "Tuesday"
            Calendar.WEDNESDAY -> "Wednesday"
            Calendar.THURSDAY -> "Thursday"
            Calendar.FRIDAY -> "Friday"
            Calendar.SATURDAY -> "Saturday"
            else -> "Unknown"
        }
    }

}
