package ru.iliavolkov.worldcinema.viewmodel

import ru.iliavolkov.worldcinema.model.FilmInfoDTO

sealed class AppStateInfo {
    data class Success(var filmInfo: List<FilmInfoDTO>): AppStateInfo()
}