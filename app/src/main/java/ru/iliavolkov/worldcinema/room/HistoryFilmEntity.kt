package ru.iliavolkov.worldcinema.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import ru.iliavolkov.worldcinema.model.Tag

@Entity(tableName = "history_weather_entity")
@TypeConverters(ListToString::class,TagToString::class)
data class HistoryFilmEntity(
    @PrimaryKey(autoGenerate = true) val movieID: Long,
    val name: String,
    val description: String,
    val age: String,
    val images: List<String>,
    val poster: String,
    val tags: List<Tag>
)

class ListToString() {
    @TypeConverter
    fun toString(images: List<String>?): String? {
        if (images == null) return null

        val stringList = mutableListOf<String>()
        images.forEach {
            stringList.add(it)

        }

        return stringList.joinToString(",")
    }

    @TypeConverter
    fun toStringList(str: String?): List<String>? {
        if (str == null) return listOf()
        if (str == "") return listOf()

        return str.split(",")
    }
}

class TagToString() {
    @TypeConverter
    fun toString(tags: List<Tag>?): String? {
        if (tags == null) return null

        val stringList = mutableListOf<String>()
        tags.forEach {
            stringList.add(it.idTags)
            stringList.add(it.tagName)
        }

        return stringList.joinToString(",")
    }

    @TypeConverter
    fun toBarcodeList(str: String?): List<Tag>? {
        if (str == null) return listOf()
        if (str == "") return listOf()

        val barcodeList = mutableListOf<Tag>()

        val strList = str.split(",")
        for (i in strList.indices step 2) {
            barcodeList.add(Tag(strList[i], strList[i + 1]))
        }

        return barcodeList
    }
}
