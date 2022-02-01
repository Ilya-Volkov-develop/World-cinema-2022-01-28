package ru.iliavolkov.worldcinema.room

import androidx.room.*

@Dao
interface HistoryFilmDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: HistoryFilmEntity)

    @Delete
    fun delete(entity: HistoryFilmEntity)

    @Update
    fun update(entity: HistoryFilmEntity)

    @Query("select * FROM history_weather_entity")
    fun getAllHistoryWeather():List<HistoryFilmEntity>
}