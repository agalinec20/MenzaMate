package models


data class Survey(
    val surveyId: Int,
    val surveyName: String?,
    val surveyDescription: String?,
    val surveyDate: String?,
    val userId: Int,
    val questions: List<Question>?,
    val responses: List<SurveyResponse>?
)


data class Question(
    val questionId: Int,
    val questionText: String?,
    val surveyId: Int,
    val answers: List<String>?,
    val userAnswers: List<UserResponse>?
)


data class SurveyResponse(
    val surveyId: Int,
    val userId: Int,
    val responses: List<UserSurveyResponse>
)

data class UserSurveyResponse(
    val questionId: Int,
    val answer: String
)
