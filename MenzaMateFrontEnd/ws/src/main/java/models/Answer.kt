package models

data class Answer(
    val answerId: Int,
    val responses: String,
    val userId: Int,
    val answered: String,
    val questionId: Int,
    val questionText : String
)