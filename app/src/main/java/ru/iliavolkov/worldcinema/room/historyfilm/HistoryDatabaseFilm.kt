package ru.iliavolkov.worldcinema.room.historyfilm

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryFilmEntity::class], version = 1, exportSchema = false)
abstract class HistoryDatabaseFilm:RoomDatabase() {
    abstract fun historyFilmDao(): HistoryFilmDAO
}