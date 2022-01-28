package ru.iliavolkov.worldcinema.repositiry

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import ru.iliavolkov.worldcinema.model.TokenDTO
import ru.iliavolkov.worldcinema.utils.END_POINT_AUTH_LOGIN
import ru.iliavolkov.worldcinema.utils.END_POINT_AUTH_REGISTER

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

//    @GET(KINOPOISK_API_URL_END_POINT)
//    fun getFilms(
//            @Header(API_KEY_NAME) apikey:String,
//            @Query("search") search:Int,
//            @Query("field") field:String,
//            @Query("token") token:String,
//            @Query("limit") limit:Int
//    ):Call<DataDTO>
//
//    @GET(KINOPOISK_API_URL_END_POINT)
//    fun getFilmInfo(
//        @Query("search") id:Long,
//        @Query("field") field:String,
//        @Query("token") token:String
//    ):Call<FilmDTO>

}