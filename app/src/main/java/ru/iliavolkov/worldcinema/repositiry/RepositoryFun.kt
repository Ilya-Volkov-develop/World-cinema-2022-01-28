package ru.iliavolkov.worldcinema.repositiry

import retrofit2.Callback
import ru.iliavolkov.worldcinema.model.CoverDTO
import ru.iliavolkov.worldcinema.model.FilmInfoDTO
import ru.iliavolkov.worldcinema.model.TokenDTO

interface RepositoryFun {
    fun signUp(email:String, pass:String, firstName:String, lastName:String, callback: Callback<String>)
    fun signIn(email:String, pass:String, callback: Callback<TokenDTO>)
    fun getCover(callback: Callback<CoverDTO>)
    fun getMoviesList(filter:String,callback: Callback<List<FilmInfoDTO>>)
}