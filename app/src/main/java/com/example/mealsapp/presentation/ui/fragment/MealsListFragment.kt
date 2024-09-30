package com.example.mealsapp.presentation.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.mealsapp.R
import com.example.mealsapp.databinding.FragmentMealsListBinding
import com.example.mealsapp.domain.model.Meal
import com.example.mealsapp.presentation.adapter.ImageAdapterAutoImageSlider
import com.example.mealsapp.presentation.adapter.MealAdapter
import com.example.mealsapp.presentation.adapter.OnListItemClick
import com.example.mealsapp.presentation.ui.base.frgment.BaseFragment
import com.example.mealsapp.presentation.ui.viewmodel.MealsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class MealsListFragment : BaseFragment<FragmentMealsListBinding>(), OnListItemClick {

    private val mealsViewModel: MealsViewModel by viewModels()
    private var meal: ArrayList<Meal> = ArrayList()
    private val mealAdapter: MealAdapter by lazy { MealAdapter(meal) }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupViewPager()
    }

    override fun setupObservers() {
        showLoadingCase()
        observeMeals()
        mealsViewModel.loadMealsForCurrentDay()
    }

    private fun observeMeals() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mealsViewModel.meals.collect { meals ->
                    updateMealList(meals)
                    handleLoadingCase()
                }
            }
        }
    }

    private fun handleLoadingCase() {
        lifecycleScope.launch {
            delay(1000)
            cancelLoadingCase()
        }
    }

    private fun setupRecyclerView() {
        mealAdapter.onListItemClick = this
        binding.mealRecyclerView.apply {
            adapter = mealAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun onItemClick(meal: Meal) {
        navigateToMealDetails(meal)
    }

    private fun navigateToMealDetails(meal: Meal) {
        val action = MealsListFragmentDirections.actionNavigationMealsListToMealDetailsFragment(meal)
        findNavController().navigate(action)
    }

    private fun updateMealList(meals: List<Meal>) {
        mealAdapter.updateMeals(meals)
    }

    private fun showLoadingCase() {
        binding.apply {
            textView3.visibility = View.INVISIBLE
            imageView3.visibility = View.INVISIBLE
            textView2.visibility = View.INVISIBLE
            vpAutoscroll.visibility = View.INVISIBLE
            mealRecyclerView.visibility = View.INVISIBLE
            textView.visibility = View.INVISIBLE
            SliderDots.visibility = View.INVISIBLE
            loadingGif.visibility = View.VISIBLE
            rootHome.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.g_loading))

        }
    }

    private fun cancelLoadingCase() {
        binding.apply {
            textView3.visibility = View.VISIBLE
            imageView3.visibility = View.VISIBLE
            textView2.visibility = View.VISIBLE
            vpAutoscroll.visibility = View.VISIBLE
            mealRecyclerView.visibility = View.VISIBLE
            textView.visibility = View.VISIBLE
            SliderDots.visibility = View.VISIBLE
            loadingGif.visibility = View.INVISIBLE
            rootHome.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))

        }
    }

    private lateinit var handler: Handler
    private lateinit var autoScrollRunnable: Runnable
    private var currentPage = 0
    private fun setupViewPager() {
        handler = Handler(Looper.getMainLooper())
        binding.vpAutoscroll.apply {
            adapter = ImageAdapterAutoImageSlider(
                ArrayList(mealsViewModel.getImageList()),
                this,
            )
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
            setUpTransformer()
        }
        autoScrollRunnable = Runnable {
            if (binding.vpAutoscroll.currentItem == binding.vpAutoscroll.adapter?.itemCount?.minus(1)) {
                currentPage = 0
            } else {
                currentPage++
            }
            binding.vpAutoscroll.setCurrentItem(currentPage, true)
            handler.postDelayed(autoScrollRunnable, 3000)
        }
        handler.postDelayed(autoScrollRunnable, 3000)
    }

    private fun ViewPager2.setUpTransformer() {
        val transformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(30))
            addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.14f
            }
        }
        setPageTransformer(transformer)
    }

    override fun setupListener() {}

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(autoScrollRunnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(autoScrollRunnable, 3000)
    }
}