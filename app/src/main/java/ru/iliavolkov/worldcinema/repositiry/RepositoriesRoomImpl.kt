package ru.iliavolkov.worldcinema.repositiry


import ru.iliavolkov.worldcinema.model.FilmInfoDTO
import ru.iliavolkov.worldcinema.room.App
import ru.iliavolkov.worldcinema.room.HistoryFilmEntity

class RepositoriesRoomImpl:RepositoryHistoryFilms {

    override fun getAllHistoryFilms(): List<FilmInfoDTO> {
        return convertHistoryFilmEntityToFilmInfoDTO(App.getHistoryFilmDao().getAllHistoryWeather())
    }
    override fun saveFilm(filmInfoDTO: FilmInfoDTO) {
        Thread {
            App.getHistoryFilmDao().insert(
                    convertFilmInfoDTOToHistoryFilmEntity(filmInfoDTO)
            )
        }.start()
    }

    override fun deleteFilm(filmInfoDTO: FilmInfoDTO) {
        Thread {
            App.getHistoryFilmDao().delete(
                    convertFilmInfoDTOToHistoryFilmEntity(filmInfoDTO)
            )
        }.start()
    }

    private fun convertHistoryFilmEntityToFilmInfoDTO(entityList:List<HistoryFilmEntity>):List<FilmInfoDTO>{
        return entityList.map{
            FilmInfoDTO(
                it.movieID.toString(),
                it.name,
                it.description,
                it.age,
                it.images,
                it.poster,
                it.tags
            )
        }
    }

    private fun convertFilmInfoDTOToHistoryFilmEntity(filmInfoDTO: FilmInfoDTO): HistoryFilmEntity {
        return HistoryFilmEntity(
            filmInfoDTO.movieID.toLong(),
            filmInfoDTO.name,
            filmInfoDTO.description,
            filmInfoDTO.age,
            filmInfoDTO.images,
            filmInfoDTO.poster,
            filmInfoDTO.tags
        )
    }
}