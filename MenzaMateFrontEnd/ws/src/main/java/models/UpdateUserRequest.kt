package models

data class UpdateUserRequest(
    val userId: Int,
    val role: String,
    val email: String,
    val username: String,
    val googleId: String
)
