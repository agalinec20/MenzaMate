package hr.foi.rampu.menzamatefrontend.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EmployeeSurveyViewModelFactory(private val userId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeSurveyViewModel::class.java)) {
            return EmployeeSurveyViewModel(userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}