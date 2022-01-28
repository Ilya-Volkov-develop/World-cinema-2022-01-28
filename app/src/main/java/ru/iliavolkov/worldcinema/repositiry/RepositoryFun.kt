package ru.iliavolkov.filmlibrary.repository

import retrofit2.Callback
import ru.iliavolkov.worldcinema.model.TokenDTO

interface RepositoryFun {
    fun signUp(email:String, pass:String, firstName:String, lastName:String, callback: Callback<String>)
    fun signIn(email:String, pass:String, callback: Callback<TokenDTO>)
//    fun getVideoFromServer(search:Int, field:String, callBack: Callback<DataDTO>)
//    fun getInfoFromServer(id: Long, field: String, callBack: Callback<FilmDTO>)
}