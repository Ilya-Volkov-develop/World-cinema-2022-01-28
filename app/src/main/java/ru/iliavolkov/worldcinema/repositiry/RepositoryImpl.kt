package ru.iliavolkov.filmlibrary.repository

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.iliavolkov.worldcinema.model.TokenDTO
import ru.iliavolkov.worldcinema.repositiry.ApiRequest
import ru.iliavolkov.worldcinema.utils.BASE_URL

class RepositoryImpl :RepositoryFun {
    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .client(OkHttpClient())
        .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
            ))
            .build().create(ApiRequest::class.java)

    override fun signUp(email: String, pass: String, firstName: String, lastName: String, callback: Callback<String>) {
        retrofit.signUp(email,pass,firstName,lastName).enqueue(callback)
    }

    override fun signIn(email: String, pass: String, callback: Callback<TokenDTO>) {
        retrofit.signIn(email,pass).enqueue(callback)
    }

//    override fun getVideoFromServer(search:Int, field:String, callBack: Callback<DataDTO>) {
//            //retrofit.getFilms(BuildConfig.KINOPOISK_API_KEY,search,field,BuildConfig.KINOPOISK_API_KEY,50).enqueue(callBack)
//    }
//
//    override fun getInfoFromServer(id: Long, field: String, callBack: Callback<FilmDTO>) {
//        //retrofit.getFilmInfo(id,field, BuildConfig.KINOPOISK_API_KEY).enqueue(callBack)
//    }
}