package models

data class Rating(
    val menuId: Int,
    val userId: Int,
    val username: String,
    val ratingDate: String,
    val comment: String?,
    val menuRating: Int
)

data class SubmitRatingRequest(
    val menuId: Int,
    val userId: Int,
    val ratingDate: String,
    val comment: String?,
    val menuRating: Int
)



