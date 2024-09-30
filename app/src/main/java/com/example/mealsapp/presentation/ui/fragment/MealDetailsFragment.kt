package com.example.mealsapp.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.mealsapp.databinding.FragmentMealDetailsBinding
import com.example.mealsapp.domain.model.Meal
import com.example.mealsapp.presentation.ui.base.frgment.BaseFragment
import com.example.mealsapp.presentation.ui.viewmodel.CartState
import com.example.mealsapp.presentation.ui.viewmodel.MealDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MealDetailsFragment : BaseFragment<FragmentMealDetailsBinding>() {

    private val mealDetailsViewModel: MealDetailsViewModel by viewModels()

    override fun onFragmentReady(savedInstanceState: Bundle?) {}

    override fun setupListener() {
        val meal = getMealFromArgs()
        displayMealDetails(meal)
        loadMealImage(meal.imageUri)
        setupAddToCartButton(meal)
    }

    private fun getMealFromArgs(): Meal {
        val args = MealDetailsFragmentArgs.fromBundle(requireArguments())
        return args.meal
    }

    private fun displayMealDetails(meal: Meal) {
        binding.apply {
            txtTitle.text = meal.name
            txtDes.text = meal.description
            txtDay.text = meal.day
        }
    }

    private fun setupAddToCartButton(meal: Meal) {
        binding.button.setOnClickListener {
            val userEmail = getUserEmail()
            mealDetailsViewModel.addToCart(meal, userEmail!!)
        }
    }

    private fun loadMealImage(imageUri: String) {
        Glide.with(this).load(imageUri).into(binding.imageView)
    }

    override fun setupObservers() {
        observeCartState()
    }

    private fun observeCartState() {
        lifecycleScope.launch {
            mealDetailsViewModel.cartState.collect { state ->
                handleCartState(state)
            }
        }
    }

    private fun handleCartState(state: CartState) {
        when (state) {
            is CartState.Success -> showToast("Meal added to cart!")
            is CartState.Error -> showToast(state.message)
            CartState.Idle -> {}
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun getUserEmail(): String? {
        val sharedPrefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPrefs.getString("user_email", null)
    }
}
