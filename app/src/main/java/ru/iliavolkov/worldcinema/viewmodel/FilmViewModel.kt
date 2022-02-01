package ru.iliavolkov.worldcinema.viewmodel

import androidx.lifecycle.ViewModel
import ru.iliavolkov.worldcinema.model.FilmInfoDTO
import ru.iliavolkov.worldcinema.repositiry.RepositoriesRoomImpl

class FilmViewModel: ViewModel() {

    private val repositoriesRoomImpl: RepositoriesRoomImpl by lazy {
        RepositoriesRoomImpl()
    }

    fun saveWeather(filmInfoDTO: FilmInfoDTO){
        repositoriesRoomImpl.saveFilm(filmInfoDTO)
    }

}