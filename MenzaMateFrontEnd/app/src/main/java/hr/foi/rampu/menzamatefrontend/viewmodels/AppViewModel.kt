package hr.foi.rampu.menzamatefrontend.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class FilterCriteria(val mealType: String? = null, val date: String? = null,
                          val excludeSimilar: Boolean = false)

class AppViewModel(private val userId: Int, private val role: String) : ViewModel() {
    private val _showFilterDialog = MutableStateFlow(false)
    val showFilterDialog: StateFlow<Boolean> = _showFilterDialog

    private val _filterCriteria = MutableStateFlow<FilterCriteria?>(null)
    val filterCriteria: StateFlow<FilterCriteria?> = _filterCriteria

    private val _userRole = MutableStateFlow(role)
    val userRole: StateFlow<String> = _userRole

    fun openFilterDialog() {
        _showFilterDialog.value = true
    }

    fun closeFilterDialog() {
        _showFilterDialog.value = false
    }

    fun setFilterCriteria(mealType: String?, date: String?, excludeSimilar: Boolean) {
        _filterCriteria.value = FilterCriteria(mealType, date, excludeSimilar)
    }


    fun clearFilterCriteria() {
        _filterCriteria.value = null
    }

    fun getUserId(): Int {
        return userId
    }

    fun getUserRole(): String = _userRole.value


    class AppViewModelFactory(private val userId: Int, private val role: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AppViewModel(userId, role) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    class SurveyViewModelFactory(private val userId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SurveyViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SurveyViewModel(userId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
