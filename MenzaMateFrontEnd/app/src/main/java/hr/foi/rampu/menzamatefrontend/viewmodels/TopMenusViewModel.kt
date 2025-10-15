package hr.foi.rampu.menzamatefrontend.viewmodels

import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import models.TopMenus

class TopMenusViewModel : ViewModel() {
    private val _topMenus = MutableStateFlow<List<TopMenus>>(emptyList())
    val topMenus: StateFlow<List<TopMenus>> = _topMenus

    init {
        fetchTopMenus()
    }

    private fun fetchTopMenus(count: Int = 10) {
        viewModelScope.launch {
            try {
                val response = hr.foi.rampu.ws.services.RetrofitInstance.api.getTopMenus(count)
                _topMenus.value = response
                Log.d("FetchTopMenus", "Dohvaćeni top jelovnici: $response")
            } catch (e: Exception) {
                Log.e("FetchTopMenus", "Greška prilikom dohvaćanje jelovnika.", e)
            }
        }
    }


}
