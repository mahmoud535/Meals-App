package com.example.mealsapp.presentation.ui.fragment

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
import com.example.mealsapp.presentation.ui.viewmodel.CartState
import com.example.mealsapp.presentation.ui.viewmodel.MealDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MealDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMealDetailsBinding
    private val mealDetailsViewModel: MealDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealDetailsBinding.inflate(layoutInflater)
        actions()
        return binding.root
    }

    private fun actions(){
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        val meal = getMealFromArgs()
        binding.apply {
            txtTitle.text = meal.name
            txtDes.text = meal.description
            txtDay.text = meal.day
            button.setOnClickListener {  mealDetailsViewModel.addToCart(meal)  }
        }
        Glide.with(this).load(meal.imageUri).into(binding.imageView)
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            mealDetailsViewModel.cartState.collect { state ->
                when (state) {
                    is CartState.Success -> showToast("Meal added to cart!")
                    is CartState.Error -> showToast(state.message)
                    CartState.Idle -> {}
                }
            }
        }
    }

    private fun getMealFromArgs(): Meal {
        val args = MealDetailsFragmentArgs.fromBundle(requireArguments())
        return args.meal
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
