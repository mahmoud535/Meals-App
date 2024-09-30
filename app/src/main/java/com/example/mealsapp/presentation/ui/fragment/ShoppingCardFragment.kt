package com.example.mealsapp.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealsapp.databinding.FragmentShoppingCardBinding
import com.example.mealsapp.presentation.adapter.ShoppingCartAdapter
import com.example.mealsapp.presentation.ui.base.frgment.BaseFragment
import com.example.mealsapp.presentation.ui.viewmodel.ShoppingCartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShoppingCardFragment : BaseFragment<FragmentShoppingCardBinding>() {

    private val shoppingCartViewModel: ShoppingCartViewModel by viewModels()
    private lateinit var adapter: ShoppingCartAdapter

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = ShoppingCartAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                shoppingCartViewModel.cartItems.collect { cartItems ->
                    adapter.submitList(cartItems)
                }
            }
        }
        loadCartItems()
    }

    private fun loadCartItems(){
        val userEmail = getUserEmail()
        shoppingCartViewModel.loadCartItems(userEmail!!)
    }

    override fun setupListener() {}

    private fun getUserEmail(): String? {
        val sharedPrefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPrefs.getString("user_email", null)
    }
}
