package ru.iliavolkov.worldcinema.model

import com.google.gson.annotations.SerializedName

data class FilmInfoDTO(
    @SerializedName("movieId")
    val movieID: String,

    val name: String,
    val description: String,
    val age: String,
    val images: List<String>,
    val poster: String,
    val tags: List<Tag>
)

data class Tag (
    val idTags: String,
    val tagName: String
)
