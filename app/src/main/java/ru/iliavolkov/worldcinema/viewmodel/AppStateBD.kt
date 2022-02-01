package ru.iliavolkov.worldcinema.viewmodel

import ru.iliavolkov.worldcinema.model.FilmInfoDTO

sealed class AppStateBD {
    data class Loading(val progress:Int): AppStateBD()
    data class Success(var weatherInfoHistoryData:List<FilmInfoDTO>): AppStateBD()
    data class Error(val error:String): AppStateBD()

}