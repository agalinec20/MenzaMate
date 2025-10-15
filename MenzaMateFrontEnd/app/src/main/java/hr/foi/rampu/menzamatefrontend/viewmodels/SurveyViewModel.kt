package hr.foi.rampu.menzamatefrontend.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
import models.Answer
import models.Question
import models.Survey
import models.SurveyAnswer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SurveyViewModel(private val userId: Int) : ViewModel() {

    private val _surveys = MutableStateFlow<List<Survey>>(emptyList())
    val surveys: StateFlow<List<Survey>> = _surveys

    private val _completedSurveys = MutableStateFlow<List<Survey>>(emptyList())
    val completedSurveys: StateFlow<List<Survey>> = _completedSurveys

    private val _availableSurveys = MutableStateFlow<List<Survey>>(emptyList())
    val availableSurveys: StateFlow<List<Survey>> = _availableSurveys

    private val _surveyAnswers = MutableStateFlow<List<Answer>>(emptyList())
    val surveyAnswers: StateFlow<List<Answer>> = _surveyAnswers

    fun fetchSurveys() {
        viewModelScope.launch {
            try {
                val response = hr.foi.rampu.ws.services.RetrofitInstance.api.getAllSurveys()
                _surveys.value = response
            } catch (e: Exception) {
                Log.e("SurveyViewModel", "Error fetching surveys", e)
            }
        }
    }

    fun fetchAllAnswersByUserId(userId: Int) {
        viewModelScope.launch {
            try {
                Log.d("SurveyViewModel", "Fetching all answers for userId: $userId")
                val response = hr.foi.rampu.ws.services.RetrofitInstance.api.getAnswersByUserId(userId)
                _surveyAnswers.value = response
                Log.d("SurveyViewModel", "Fetched answers: ${_surveyAnswers.value}")
            } catch (e: Exception) {
                Log.e("SurveyViewModel", "Error fetching answers", e)
            }
        }
    }

    fun fetchAvailableSurveys(userId: Int) {
        viewModelScope.launch {
            try {
                val availableResponse = hr.foi.rampu.ws.services.RetrofitInstance.api.getAvailableSurveysForUser(userId)
                _availableSurveys.value = availableResponse

                val allSurveysResponse = hr.foi.rampu.ws.services.RetrofitInstance.api.getAllSurveys()
                _surveys.value = allSurveysResponse

                val completedSurveysList = allSurveysResponse.filter { survey ->
                    survey.questions?.all { question ->
                        _surveyAnswers.value.any { answer ->
                            answer.questionId == question.questionId && answer.userId == userId
                        }
                    } == true
                }

                _completedSurveys.value = completedSurveysList
            } catch (e: Exception) {
                Log.e("SurveyViewModel", "Error fetching surveys", e)
            }
        }
    }

    fun createSurveyWithQuestions(name: String, description: String, questions: List<String>) {
        viewModelScope.launch {
            try {
                val survey = Survey(
                    surveyId = 0,
                    surveyName = name,
                    surveyDescription = description,
                    surveyDate = getCurrentTime(),
                    userId = userId,
                    questions = questions.map { questionText ->
                        Question(questionId = 0, surveyId = 0, questionText = questionText, answers = null, userAnswers = null)
                    },
                    responses = null
                )
                hr.foi.rampu.ws.services.RetrofitInstance.api.createSurvey(survey)
                fetchSurveys()
            } catch (e: Exception) {
                Log.e("SurveyViewModel", "Error creating survey", e)
            }
        }
    }


    suspend fun submitAnswerToSurvey(questionId: Int, surveyId: Int, userId: Int, response: String ) {
        val answer = SurveyAnswer(
            responses = response,
            userId = userId,
            answered = getCurrentTime(),
            questionId = questionId,
            questionText=""
        )

        try {
            val response = hr.foi.rampu.ws.services.RetrofitInstance.api.submitAnswer(questionId, answer)
            fetchAvailableSurveys(userId)
            fetchCompletedSurveys(userId)
        } catch (e: Exception) {
            Log.e("Survey", "Exception submitting answer: ${e.message}")
        }
    }

    fun getCurrentTime(): String {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        return now.format(formatter)
    }

    fun fetchCompletedSurveys(userId: Int) {
        viewModelScope.launch {
            try {
                Log.d("SurveyViewModel", "Fetching completed surveys for userId: $userId")

                val availableSurveysList = _availableSurveys.value

                val completedSurveysList = availableSurveysList.filter { survey ->
                    survey.questions?.all { question ->
                        _surveyAnswers.value.any { it.questionId == question.questionId && it.userId == userId }
                    } == true
                }

                _completedSurveys.value = completedSurveysList
                Log.d("SurveyViewModel", "Fetched completed surveys: ${_completedSurveys.value.size}")

            } catch (e: Exception) {
                Log.e("SurveyViewModel", "Error fetching completed surveys", e)
            }
        }
    }

}


