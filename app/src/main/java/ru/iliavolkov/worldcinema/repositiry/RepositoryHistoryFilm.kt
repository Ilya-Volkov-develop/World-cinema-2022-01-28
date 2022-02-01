package ru.iliavolkov.worldcinema.repositiry

import ru.iliavolkov.worldcinema.model.FilmInfoDTO

interface RepositoryHistoryFilms {
    fun getAllHistoryFilms():List<FilmInfoDTO>
    fun saveFilm(filmInfoDTO: FilmInfoDTO)
    fun deleteFilm(filmInfoDTO: FilmInfoDTO)
}