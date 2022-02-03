package ru.iliavolkov.worldcinema.room.historyepisode

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryEpisodeEntity::class], version = 1, exportSchema = false)
abstract class HistoryDatabaseEpisode:RoomDatabase() {
    abstract fun historyEpisode(): HistoryEpisodeDAO
}