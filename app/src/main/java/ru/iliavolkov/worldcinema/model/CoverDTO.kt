package ru.iliavolkov.worldcinema.model

import com.google.gson.annotations.SerializedName

data class CoverDTO(
    @SerializedName("movieId")
    val movieID: String,

    val backgroundImage: String,
    val foregroundImage: String
)
