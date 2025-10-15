package models

data class UserResponse(
    val username: String,
    val email: String,
    val userId : Int,
    val role : String,
    val googleId: String
)
