package ru.iliavolkov.worldcinema.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmInfoDTO(
    @SerializedName("movieId")
    val movieID: String,

    val name: String,
    val description: String,
    val age: String,
    val images: List<String>,
    val poster: String,
    val tags: List<Tag>
):Parcelable
@Parcelize
data class Tag (
    val idTags: String,
    val tagName: String
):Parcelable
