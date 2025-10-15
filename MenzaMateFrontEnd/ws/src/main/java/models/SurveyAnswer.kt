package models

data class SurveyAnswer(
    val answerId: Int = 0,
    val responses: String,
    val userId: Int,
    val answered: String,
    val questionId: Int,
    val questionText :String

)