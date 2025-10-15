package hr.foi.rampu.menzamatefrontend.viewmodels

import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import models.Menu

class HomePageViewModel : ViewModel() {
    private val _menus = MutableStateFlow<List<Menu>>(emptyList())
    val menus: StateFlow<List<Menu>> = _menus

    private val _filteredMenus = MutableStateFlow<List<Menu>>(emptyList())
    val filteredMenus: StateFlow<List<Menu>> = _filteredMenus

    init {
        fetchMenus()
    }

    private fun fetchMenus() {
        viewModelScope.launch {
            try {
                val response = hr.foi.rampu.ws.services.RetrofitInstance.api.getAllMenus()
                Log.d("FetchMenus", "Response: $response")
                _menus.value = response
                _filteredMenus.value = response
            } catch (e: Exception) {
                Log.e("FetchMenus", "Error fetching menus", e)
            }
        }
    }

    fun applyFilter(mealType: String?, date: String?, excludeSimilar: Boolean) {
        viewModelScope.launch {
            try {
                val response = if (excludeSimilar) {
                    hr.foi.rampu.ws.services.RetrofitInstance.api.getDistinctMenus()
                } else {
                    hr.foi.rampu.ws.services.RetrofitInstance.api.getAllMenus()
                }

                _filteredMenus.value = response.filter { menu ->
                    val matchesMealType = mealType.isNullOrEmpty() || menu.title.contains(mealType, ignoreCase = true)
                    val matchesDate = date.isNullOrEmpty() || menu.createdDate.contains(date)
                    matchesMealType && matchesDate
                }
            } catch (e: Exception) {
                Log.e("FilterMenus", "Error applying filter", e)
            }
        }
    }

}
