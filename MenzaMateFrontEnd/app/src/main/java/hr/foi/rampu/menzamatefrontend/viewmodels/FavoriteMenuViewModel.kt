package hr.foi.rampu.menzamatefrontend.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import models.FavoriteMenuRequest
import models.FavoriteMenuResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteMenuViewModel : ViewModel() {
    private val _favoriteMenus = MutableStateFlow<List<FavoriteMenuResponse>>(emptyList())
    val favoriteMenus: StateFlow<List<FavoriteMenuResponse>> = _favoriteMenus

    fun loadFavorites(userId: Int) {
        viewModelScope.launch {
            try {
                val favorites = hr.foi.rampu.ws.services.RetrofitInstance.api.getFavoritesForUser(userId)
                _favoriteMenus.value = favorites
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addFavorite(userId: Int, menuId: Int) {
        viewModelScope.launch {
            try {
                hr.foi.rampu.ws.services.RetrofitInstance.api.addFavoriteMenu(FavoriteMenuRequest(userId, menuId))
                loadFavorites(userId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun removeFavorite(userId: Int, menuId: Int) {
        viewModelScope.launch {
            try {
                hr.foi.rampu.ws.services.RetrofitInstance.api.removeFavoriteMenu(userId, menuId)
                loadFavorites(userId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
