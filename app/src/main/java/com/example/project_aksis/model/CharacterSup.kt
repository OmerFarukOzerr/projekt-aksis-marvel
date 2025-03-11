package com.example.project_aksis.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


data class HeroesListResult(
    val code: Int? = null,
    val status: String? = null,
    val copyright: String? = null,
    val attributionText: String? = null,
    val attributionHTML: String? = null,
    val etag: String? = null,
    val data: Data? = null

)


data class Data(
    val offset: Int? = null,
    val limit: Int? = null,
    val total: Int? = null,
    val count: Int? = null,
    val results: List<Result>? = null)


@Serializable
@Entity(tableName = "save_chars")
data class Result(val id: Int,
                  @PrimaryKey(autoGenerate = true)
                  val primaryKey: Int = 0,
                  val name: String,
                  val description: String,
                  val modified: String,
                  val resourceURI: String,
                  @ColumnInfo(name = "url")
                  val urls: List<Url>,
                  @ColumnInfo(name = "thumb")
                  val thumbnail: Thumbnail,
                  @ColumnInfo(name = "comics")
                  val comics: Comics,
                  @ColumnInfo(name = "stories")
                  val stories: Stories,
                  @ColumnInfo(name = "events")
                  val events: Events,
                  @ColumnInfo(name = "series")
                  val series: Series,
    )

@Serializable
data class Url(val type: String,
               val url: String)

@Serializable
data class Thumbnail(
    val path: String,
    val extension: String)

@Serializable
data class Comics(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<Item>)

@Serializable
data class Item(val resourceURI: String,
                val name: String,
                val type: String)

@Serializable
data class Stories(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<Item>)

@Serializable
data class Events(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<Item>)

@Serializable
data class Series(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<Item>)