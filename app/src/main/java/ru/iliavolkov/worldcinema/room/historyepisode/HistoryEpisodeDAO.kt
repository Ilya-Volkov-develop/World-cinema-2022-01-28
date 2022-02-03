package ru.iliavolkov.worldcinema.room.historyepisode

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryEpisodeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: HistoryEpisodeEntity)

    @Query("select * FROM history_episode_entity")
    fun getEpisode():HistoryEpisodeEntity
}