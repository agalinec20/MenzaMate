package hr.foi.rampu.menzamatefrontend.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import models.Answer
import models.Survey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EmployeeSurveyViewModel(private val userId: Int) : ViewModel() {

    private val _allSurveys = MutableStateFlow<List<Survey>>(emptyList())
    val allSurveys: StateFlow<List<Survey>> = _allSurveys

    private val _mySurveys = MutableStateFlow<List<Survey>>(emptyList())
    val mySurveys: StateFlow<List<Survey>> = _mySurveys

    private val _surveyAnswers = MutableStateFlow<List<Answer>>(emptyList())
    val surveyAnswers: StateFlow<List<Answer>> = _surveyAnswers

    fun fetchAllSurveys() {
        viewModelScope.launch {
            try {
                Log.d("SurveyDebug", "Fetching all surveys...")
                val response = hr.foi.rampu.ws.services.RetrofitInstance.api.getAllSurveys()
                _allSurveys.value = response ?: emptyList()
                Log.d("SurveyDebug", "Fetched surveys: ${_allSurveys.value.size}")
            } catch (e: Exception) {
                Log.e("SurveyDebug", "Error fetching surveys", e)
                _allSurveys.value = emptyList()
            }
        }
    }

    fun fetchMySurveys() {
        viewModelScope.launch {
            try {
                Log.d("SurveyDebug", "Fetching my surveys for userId: $userId")
                val response = hr.foi.rampu.ws.services.RetrofitInstance.api.getSurveysByUserId(userId)
                _mySurveys.value = response ?: emptyList()
                Log.d("SurveyDebug", "Fetched my surveys: ${_mySurveys.value.size}")
            } catch (e: Exception) {
                Log.e("SurveyDebug", "Error fetching my surveys", e)
                _mySurveys.value = emptyList()
            }
        }
    }

    fun fetchAnswersBySurveyId(surveyId: Int?) {
        if (surveyId == null) {
            Log.e("SurveyDebug", "Survey ID is null")
            _surveyAnswers.value = emptyList()
            return
        }

        viewModelScope.launch {
            try {
                Log.d("SurveyDebug", "Fetching answers for surveyId: $surveyId")
                val response = hr.foi.rampu.ws.services.RetrofitInstance.api.getAnswersBySurveyId(surveyId)
                _surveyAnswers.value = response ?: emptyList()
                Log.d("SurveyDebug", "Fetched answers: ${_surveyAnswers.value.size}")
            } catch (e: Exception) {
                Log.e("SurveyDebug", "Error fetching answers", e)
                _surveyAnswers.value = emptyList()
            }
        }
    }

    fun deleteSurvey(surveyId: Int) {
        viewModelScope.launch {
            try {
                hr.foi.rampu.ws.services.RetrofitInstance.api.deleteSurvey(surveyId)
                fetchAllSurveys()
            } catch (e: Exception) {
                Log.e("SurveyDebug", "Error deleting survey", e)
            }
        }
    }
}