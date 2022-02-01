package ru.iliavolkov.worldcinema.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryFilmEntity::class], version = 1, exportSchema = false)
abstract class HistoryDatabase:RoomDatabase() {
    abstract fun historyWeatherDao(): HistoryFilmDAO
}