package ru.iliavolkov.worldcinema.repositiry

import retrofit2.Call
import retrofit2.http.*
import ru.iliavolkov.worldcinema.model.CoverDTO
import ru.iliavolkov.worldcinema.model.FilmInfoDTO
import ru.iliavolkov.worldcinema.model.TokenDTO
import ru.iliavolkov.worldcinema.utils.END_POINT_AUTH_LOGIN
import ru.iliavolkov.worldcinema.utils.END_POINT_AUTH_REGISTER
import ru.iliavolkov.worldcinema.utils.END_POINT_MOVIES_COVER

interface ApiRequest {
    @POST(END_POINT_AUTH_REGISTER)
    @FormUrlEncoded
    fun signUp(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String
    ):Call<String>

    @POST(END_POINT_AUTH_LOGIN)
    @FormUrlEncoded
    fun signIn(
        @Field("email") email: String,
        @Field("password") password: String
    ):Call<TokenDTO>

    @GET(END_POINT_MOVIES_COVER)
    fun getCover():Call<CoverDTO>

    @GET("movies")
    fun getMoviesList(
        @Query("filter") filter:String
    ):Call<List<FilmInfoDTO>>

}