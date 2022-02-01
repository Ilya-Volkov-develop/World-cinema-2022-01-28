package ru.iliavolkov.worldcinema.viewmodel

import ru.iliavolkov.worldcinema.model.EpisodesDTO

sealed class AppStateEpisodes {
    data class Success(var filmInfo: List<EpisodesDTO>): AppStateEpisodes()
}