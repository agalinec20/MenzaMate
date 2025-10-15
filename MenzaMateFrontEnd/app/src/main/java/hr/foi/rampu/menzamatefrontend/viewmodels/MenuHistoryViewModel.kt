package hr.foi.rampu.menzamatefrontend.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import models.MenuHistoryRequest
import models.MenuHistoryResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MenuHistoryViewModel : ViewModel() {
    private val _menuHistory = MutableStateFlow<List<MenuHistoryResponse>>(emptyList())
    val menuHistory: StateFlow<List<MenuHistoryResponse>> = _menuHistory

    fun loadMenuHistory(userId: Int) {
        viewModelScope.launch {
            try {

                val history = hr.foi.rampu.ws.services.RetrofitInstance.api.getMenuHistoryForUser(userId)

                val uniqueMenus = history.groupBy { it.menuId }
                    .map { it.value.first() }


                val sortedHistory = uniqueMenus.sortedBy { it.added }

                _menuHistory.value = sortedHistory

            } catch (e: Exception) {
            }
        }
    }

    fun addHistory(userId: Int, menuId: Int) {
        viewModelScope.launch {
            try {

                hr.foi.rampu.ws.services.RetrofitInstance.api.addMenuHistory(MenuHistoryRequest(userId, menuId))
                loadMenuHistory(userId)

            } catch (e: Exception) {

            }
        }
    }
}


