package ru.iliavolkov.worldcinema.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.iliavolkov.filmlibrary.repository.RepositoryImpl
import ru.iliavolkov.worldcinema.model.TokenDTO


class MainViewModel(private val liveData: MutableLiveData<Any> = MutableLiveData()): ViewModel() {

    private val repositoryImpl: RepositoryImpl by lazy { RepositoryImpl() }

    fun getLiveData() = liveData

    fun signUp(email: String, pass: String, firstName: String, lastName: String){
        repositoryImpl.signUp(email,pass,firstName,lastName,callbackSuccessfulRegistration)
    }
    fun signIn(email: String, pass: String){
        repositoryImpl.signIn(email,pass,callbackSignIn)
    }

    private val callbackSuccessfulRegistration = object : Callback<String> {
        override fun onFailure(call: Call<String>, t: Throwable) {
            Log.d("myLogs",t.toString())
        }
        override fun onResponse(call: Call<String>, response: Response<String>) {
            if (response.isSuccessful){
                response.body()?.let {
                    liveData.postValue(it)
                }
            }
        }
    }

    private val callbackSignIn = object : Callback<TokenDTO>{
        override fun onResponse(call: Call<TokenDTO>, response: Response<TokenDTO>) {
            if (response.isSuccessful){
                response.body()?.let{
                    liveData.postValue(it)
                }
            }
        }

        override fun onFailure(call: Call<TokenDTO>, t: Throwable) {
            Log.d("myLogs",t.toString())
        }

    }
}