package ru.iliavolkov.worldcinema.repositiry

import ru.iliavolkov.worldcinema.room.historyepisode.HistoryEpisodeEntity

interface RepositoryEpisode {
    fun getEpisodePath(): HistoryEpisodeEntity
    fun saveEpisodePath(episodesPath: String)
}