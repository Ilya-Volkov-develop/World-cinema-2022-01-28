package ru.iliavolkov.worldcinema.repositiry


import ru.iliavolkov.worldcinema.model.FilmInfoDTO
import ru.iliavolkov.worldcinema.room.App
import ru.iliavolkov.worldcinema.room.HistoryFilmEntity

class RepositoriesRoomImpl:RepositoryHistoryFilms {

    override fun getAllHistoryFilms(): List<FilmInfoDTO> {
        return convertHistoryWeatherEntityToWeather(App.getHistoryWeatherDao().getAllHistoryWeather())
    }

    private fun convertHistoryWeatherEntityToWeather(entityList:List<HistoryFilmEntity>):List<FilmInfoDTO>{
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

    override fun saveFilm(filmInfoDTO: FilmInfoDTO) {
        Thread {
            App.getHistoryWeatherDao().insert(
                convertWeatherToHistoryWeatherEntity(filmInfoDTO)
            )
        }.start()
    }

    private fun convertWeatherToHistoryWeatherEntity(filmInfoDTO: FilmInfoDTO): HistoryFilmEntity {
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