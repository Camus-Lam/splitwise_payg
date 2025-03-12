package com.example.splitwise_payg.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.splitwise_payg.UserState
import com.example.splitwise_payg.db.respositories.AccountRepository
import com.example.splitwise_payg.event.AccountEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val accountRepository: AccountRepository,
): ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state.asStateFlow()

    val currentUserId: StateFlow<Int?> = accountRepository.currentUserId

    fun onAccountEvent(event: AccountEvent) {
        when (event) {
            is AccountEvent.Login -> viewModelScope.launch{
                _state.value = UserState(isLoading = true)
                val result = accountRepository.userLogin(event.emailAddress, event.password)
                _state.value = result.fold(
                    onSuccess = { user ->
                        UserState(
                            fullName = user.fullName,
                            emailAddress = user.emailAddress,
                            isLoggedIn = true,
                            isLoading = false,
                        )
                    },
                    onFailure = {
                        UserState(
                            errorMessage = result.exceptionOrNull()?.message,
                            isLoading = false
                        )
                    }
                )
            }

            is AccountEvent.SignUp -> viewModelScope.launch {
                _state.value = UserState(isLoading = true)
                val result = accountRepository.userSignUp(event.fullName, event.emailAddress, event.password, event.phoneNumber)
                _state.value = result.fold(
                    onSuccess = {
                        UserState(
                            fullName = event.fullName,
                            emailAddress = event.emailAddress,
                            phoneNumber = event.phoneNumber,
                            isLoggedIn = true,
                            isLoading = false,
                        )
                    },
                    onFailure = {
                        UserState(
                            errorMessage = result.exceptionOrNull()?.message,
                            isLoading = false
                        )
                    }
                )
            }

            is AccountEvent.Logout -> viewModelScope.launch {
                _state.value = UserState()
            }
        }
    }
}