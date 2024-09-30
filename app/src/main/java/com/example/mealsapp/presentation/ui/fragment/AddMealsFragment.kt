package com.example.mealsapp.presentation.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mealsapp.domain.model.Meal
import android.widget.Spinner
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mealsapp.R
import com.example.mealsapp.databinding.FragmentAddMealsBinding
import com.example.mealsapp.presentation.ui.base.frgment.BaseFragment
import com.example.mealsapp.presentation.ui.viewmodel.AddMealViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMealsFragment : BaseFragment<FragmentAddMealsBinding>() {

    private val addMealViewModel: AddMealViewModel by viewModels()

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        setupImageViewClickListener()
    }

    override fun setupListener() {
        binding.addMealButton.setOnClickListener {
            val meal = createMeal() ?: return@setOnClickListener
            addMeal(meal)
        }
    }

    private fun addMeal(meal: Meal) {
        addMealViewModel.addMeal(meal)
        showToast("Meal added")
        navigateToMealList()
    }

    private fun navigateToMealList() {
        findNavController().navigate(R.id.action_navigation_add_meals_to_navigation_meals_list)
    }

    private fun setupImageViewClickListener() {
        binding.mealImageView.setOnClickListener { openImageChooser() }
    }

    private var imageUri: Uri? = null
    private fun createMeal(): Meal? {
        val title = binding.mealTitleInput.text.toString().trim()
        val description = binding.mealDescriptionInput.text.toString().trim()
        val selectedDay = getSelectedDay()
        if (!isMealInputValid(title, description, imageUri)) { return null }
        return Meal(day = selectedDay, name = title, description = description, imageUri = imageUri.toString())
    }

    private fun isMealInputValid(title: String, description: String, imageUri: Uri?): Boolean {
        if (title.isEmpty() || description.isEmpty() || imageUri == null) {
            showToast("Please fill all fields")
            return false
        }
        return true
    }

    private fun getSelectedDay(): String {
        val daySpinner: Spinner = binding.daySpinner
        val days = resources.getStringArray(R.array.days_of_week)
        return days[daySpinner.selectedItemPosition]
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun setupObservers() {}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            setImagePreview(imageUri)
        }
    }

    private fun setImagePreview(uri: Uri?) {
        binding.mealImageView.setImageURI(uri)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val IMAGE_PICK_CODE = 1001
    }
}
