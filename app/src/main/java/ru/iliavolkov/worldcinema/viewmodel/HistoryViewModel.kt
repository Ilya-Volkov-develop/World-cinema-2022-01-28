package ru.iliavolkov.worldcinema.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.iliavolkov.worldcinema.repositiry.RepositoriesRoomImpl


class HistoryViewModel(private val liveData: MutableLiveData<AppStateBD> = MutableLiveData()): ViewModel() {

    private val repositoriesRoomImpl: RepositoriesRoomImpl by lazy {
        RepositoriesRoomImpl()
    }

    fun getLiveData() = liveData

    fun getAllHistory(){
        Thread {
            val listWeather = repositoriesRoomImpl.getAllHistoryFilms()
            liveData.postValue(AppStateBD.Success(listWeather))
        }.start()
    }
}