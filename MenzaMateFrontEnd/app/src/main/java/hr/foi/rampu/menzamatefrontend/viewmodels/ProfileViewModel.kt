package hr.foi.rampu.menzamatefrontend.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import models.UpdateUserRequest
import models.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _user = MutableStateFlow<UserResponse?>(null)
    val user: StateFlow<UserResponse?> = _user

    fun getUserData(userId: Int) {
        viewModelScope.launch {
            try {
                val response = hr.foi.rampu.ws.services.RetrofitInstance.api.getUserById(userId)
                _user.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateUserData(userId: Int, newUsername: String) {
        viewModelScope.launch {
            try {
                val currentUser = user.value ?: return@launch

                hr.foi.rampu.ws.services.RetrofitInstance.api.updateUser(
                    userId,
                    UpdateUserRequest(
                        userId = currentUser.userId,
                        role = currentUser.role,
                        email = currentUser.email,
                        username = newUsername,
                        googleId = currentUser.googleId
                    )
                )

                getUserData(userId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
