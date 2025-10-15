package hr.foi.rampu.menzamatefrontend.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import models.Rating
import models.SubmitRatingRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RatingViewModel : ViewModel() {

    private val _ratings = MutableStateFlow<List<Rating>>(emptyList())
    val ratings: StateFlow<List<Rating>> = _ratings

    fun fetchRatingsForMenu(menuId: Int) {
        viewModelScope.launch {
            try {
                val response = hr.foi.rampu.ws.services.RetrofitInstance.api.getRatingsForMenu(menuId)
                _ratings.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                println("Greška prilikom dohvaćanja ocjena: ${e.message}")
            }
        }
    }

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage

    fun submitRating(menuId: Int, userId: Int, rating: Int, comment: String?) {
        viewModelScope.launch {
            try {
                val ratingDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(Date())
                val submitRatingRequest = SubmitRatingRequest(
                    menuId = menuId,
                    userId = userId,
                    ratingDate = ratingDate,
                    comment = comment,
                    menuRating = rating
                )

                hr.foi.rampu.ws.services.RetrofitInstance.api.submitRating(submitRatingRequest)

                _successMessage.value = "Ocjena i komentar uspješno poslani!"

                fetchRatingsForMenu(menuId)

                fetchAverageRating(menuId)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Greška prilikom unosa ocjene: ${e.message}")
            }
        }
    }

    fun clearSuccessMessage() {
        _successMessage.value = null
    }

    private val _averageRating = MutableStateFlow(0.0)
    val averageRating: StateFlow<Double> = _averageRating

    private val _ratingCount = MutableStateFlow(0)
    val ratingCount: StateFlow<Int> = _ratingCount

    fun fetchAverageRating(menuId: Int) {
        viewModelScope.launch {
            try {
                val response = hr.foi.rampu.ws.services.RetrofitInstance.api.getAverageRating(menuId)
                _averageRating.value = response.averageRating
                _ratingCount.value = response.ratingCount
            } catch (e: Exception) {
                e.printStackTrace()
                println("Greška prilikom dohvaćanja prosječne ocjene: ${e.message}")
            }
        }
    }

}
