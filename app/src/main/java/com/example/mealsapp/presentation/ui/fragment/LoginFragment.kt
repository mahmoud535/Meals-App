package com.example.mealsapp.presentation.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mealsapp.R
import com.example.mealsapp.databinding.FragmentAdminLoginBinding
import com.example.mealsapp.presentation.ui.activity.MainActivity
import com.example.mealsapp.presentation.ui.base.frgment.BaseFragment
import com.example.mealsapp.presentation.ui.viewmodel.LoginState
import com.example.mealsapp.presentation.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentAdminLoginBinding>() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        setupSpinner()
    }

    override fun setupListener() {
        binding.loginButton.setOnClickListener { initiateUserLogin() }
    }

    override fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginState.collect { state ->
                    handleLoginState(state)
                }
            }
        }
    }

    private fun setupSpinner() {
        val roles = resources.getStringArray(R.array.user_roles)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, roles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.roleSpinner.adapter = adapter
    }

    private fun initiateUserLogin() {
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()
        val selectedRole = binding.roleSpinner.selectedItem.toString()
        loginUser(email, password, selectedRole)
    }

    private fun loginUser(email: String, password: String, selectedRole: String) {
        if (areFieldsValid(email, password))
            loginViewModel.login(email, password, selectedRole)
        else
            showToast("Please fill in all fields")
    }

    private fun handleLoginState(state: LoginState) {
        when (state) {
            is LoginState.Success -> {
                saveUserEmail(state.email)
                navigateBasedOnRole(state.role)
            }
            is LoginState.Error ->  showToast(state.message)
            LoginState.Idle -> {}
        }
    }

    private fun navigateBasedOnRole(role: String) {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.putExtra("USER_ROLE", role)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun areFieldsValid(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun saveUserEmail(email: String) {
        val sharedPrefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString("user_email", email)
        editor.apply()
    }
}

