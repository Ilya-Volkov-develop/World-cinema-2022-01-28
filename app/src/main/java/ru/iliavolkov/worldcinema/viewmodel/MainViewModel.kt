package ru.iliavolkov.worldcinema.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.iliavolkov.worldcinema.model.CoverDTO
import ru.iliavolkov.worldcinema.model.EpisodesDTO
import ru.iliavolkov.worldcinema.model.FilmInfoDTO
import ru.iliavolkov.worldcinema.model.TokenDTO
import ru.iliavolkov.worldcinema.repositiry.RepositoriesRoomImpl
import ru.iliavolkov.worldcinema.repositiry.RepositoryRemoteImpl
import ru.iliavolkov.worldcinema.utils.VIDEO_URL


class MainViewModel(private val liveData: MutableLiveData<Any> = MutableLiveData()): ViewModel() {

    private val repositoryRemoteImpl: RepositoryRemoteImpl by lazy { RepositoryRemoteImpl() }
    private val repositoriesRoomImpl: RepositoriesRoomImpl by lazy { RepositoriesRoomImpl() }

    fun getLiveData() = liveData

    fun signUp(email: String, pass: String, firstName: String, lastName: String){
        repositoryRemoteImpl.signUp(email,pass,firstName,lastName,callbackSuccessfulRegistration)
    }
    fun signIn(email: String, pass: String){
        repositoryRemoteImpl.signIn(email,pass,callbackSignIn)
    }

    fun getCover(){
        repositoryRemoteImpl.getCover(callbackCover)
    }

    fun getMoviesList(filter:String){
        repositoryRemoteImpl.getMoviesList(filter,callbackMoviesList)
    }

    fun getEpisodes(movieID: String) {
        repositoryRemoteImpl.getEpisodes(movieID,callbackEpisodesList)
    }

    fun saveFilm(film:FilmInfoDTO){
        repositoriesRoomImpl.saveFilm(film)
    }

    fun deleteFilm(film:FilmInfoDTO){
        repositoriesRoomImpl.deleteFilm(film)
    }

    fun saveEpisodePath(path:String){
        repositoriesRoomImpl.saveEpisodePath(path)
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

    private val callbackCover = object :Callback<CoverDTO>{
        override fun onResponse(call: Call<CoverDTO>, response: Response<CoverDTO>) {
            if (response.isSuccessful){
                response.body()?.let{
                    liveData.postValue(it)
                }
            }
        }

        override fun onFailure(call: Call<CoverDTO>, t: Throwable) {
            TODO("Not yet implemented")
        }

    }

    private val callbackMoviesList = object : Callback<List<FilmInfoDTO>>{
        override fun onResponse(call: Call<List<FilmInfoDTO>>, response: Response<List<FilmInfoDTO>>) {
            if (response.isSuccessful)
                response.body()?.let{
                    liveData.postValue(AppStateInfo.Success(it))
                }
        }

        override fun onFailure(call: Call<List<FilmInfoDTO>>, t: Throwable) {
            Log.d("myLogs",t.toString())
        }

    }

    private val callbackEpisodesList = object : Callback<List<EpisodesDTO>>{
        override fun onResponse(call: Call<List<EpisodesDTO>>, response: Response<List<EpisodesDTO>>) {
            if (response.isSuccessful)
                response.body()?.let{
                    liveData.postValue(AppStateEpisodes.Success(it))
                }
        }

        override fun onFailure(call: Call<List<EpisodesDTO>>, t: Throwable) {

        }

    }
}