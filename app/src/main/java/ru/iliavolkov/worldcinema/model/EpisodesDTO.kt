package ru.iliavolkov.worldcinema.model

import com.google.gson.annotations.SerializedName

data class EpisodesDTO(
        @SerializedName("episodeId")
        val episodeID: String,

        val name: String,
        val description: String,

        @SerializedName("moviesId")
        val moviesID: String,

        val director: String,
        val stars: List<String>,
        val year: String,
        val runtime: String,
        val preview: String,
        val images: List<String>
)
