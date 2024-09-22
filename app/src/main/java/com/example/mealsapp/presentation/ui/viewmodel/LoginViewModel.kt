package com.example.mealsapp.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealsapp.domain.model.User
import com.example.mealsapp.domain.repository.LoginRepository
import com.example.mealsapp.domain.usecase.GetUserByEmailUseCase
import com.example.mealsapp.domain.usecase.InsertUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserByEmailUseCase: GetUserByEmailUseCase,
    private val insertUserUseCase: InsertUserUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String, selectedRole: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingUser = getUserByEmailUseCase(email)
            if (existingUser == null) {
                val newUser = User(email = email, password = password, role = selectedRole)
                insertUserUseCase(newUser)
                _loginState.value = LoginState.Success(selectedRole, email)
            } else {
                if (existingUser.role == selectedRole) {
                    _loginState.value = LoginState.Success(selectedRole, email)
                } else {
                    _loginState.value = LoginState.Error("Cannot log in as $selectedRole with the same email")
                }
            }
        }
    }
}



sealed class LoginState {
    data object Idle : LoginState()
    data class Success(val role: String, val email: String) : LoginState() // Add email here
    data class Error(val message: String) : LoginState()
}