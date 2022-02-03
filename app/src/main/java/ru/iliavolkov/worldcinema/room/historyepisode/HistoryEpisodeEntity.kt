package ru.iliavolkov.worldcinema.room.historyepisode

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_episode_entity")
data class HistoryEpisodeEntity(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val episodePath: String
)