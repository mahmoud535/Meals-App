package com.example.mealsapp.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealsapp.databinding.FragmentShoppingCardBinding
import com.example.mealsapp.presentation.adapter.ShoppingCartAdapter
import com.example.mealsapp.presentation.ui.viewmodel.ShoppingCartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingCardFragment : Fragment() {

    private lateinit var binding: FragmentShoppingCardBinding
    private val shoppingCartViewModel: ShoppingCartViewModel by viewModels()
    private lateinit var adapter: ShoppingCartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShoppingCardBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupObservers()
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = ShoppingCartAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            shoppingCartViewModel.cartItems.collect { cartItems ->
                adapter.submitList(cartItems)
            }
        }
        val userEmail = getUserEmail()
        shoppingCartViewModel.loadCartItems(userEmail!!)
    }

    private fun getUserEmail(): String? {
        val sharedPrefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPrefs.getString("user_email", null)
    }
}
