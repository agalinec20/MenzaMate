package models

data class FavoriteMenuResponse(
    val id: Int,
    val userId: Int,
    val menuId: Int,
    val added: String,
    val menuTitle: String,
    val menuDescription: String
)
