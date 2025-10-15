package models

import com.google.gson.annotations.SerializedName

data class Menu(
    @SerializedName("menuId") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("createdDate") val createdDate: String
)


