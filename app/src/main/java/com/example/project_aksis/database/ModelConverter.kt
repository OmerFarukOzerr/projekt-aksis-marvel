package com.example.project_aksis.database

import androidx.room.TypeConverter
import com.example.project_aksis.model.Comics
import com.example.project_aksis.model.Events
import com.example.project_aksis.model.Item
import com.example.project_aksis.model.Series
import com.example.project_aksis.model.Stories
import com.example.project_aksis.model.Thumbnail
import com.example.project_aksis.model.Url
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ModelConverter {

    private val gson = Gson()

    // Thumbnail nesnesi için dönüştürme
    @TypeConverter
    fun fromThumbnail(thumbnail: Thumbnail): String {
        return gson.toJson(thumbnail)
    }

    @TypeConverter
    fun toThumbnail(thumbnailString: String): Thumbnail {
        return gson.fromJson(thumbnailString, Thumbnail::class.java)
    }

    // List<Url> için dönüştürme
    @TypeConverter
    fun fromUrlList(value: List<Url>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toUrlList(value: String): List<Url>? {
        val listType = object : TypeToken<List<Url>>() {}.type
        return gson.fromJson(value, listType)
    }

    // List<Item> için dönüştürme
    @TypeConverter
    fun fromItemList(value: List<Item>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toItemList(value: String): List<Item>? {
        val listType = object : TypeToken<List<Item>>() {}.type
        return gson.fromJson(value, listType)
    }

    // Comics için dönüştürme
    @TypeConverter
    fun fromComics(comics: Comics): String {
        return gson.toJson(comics)
    }

    @TypeConverter
    fun toComics(comicsString: String): Comics {
        return gson.fromJson(comicsString, Comics::class.java)
    }

    // Stories için dönüştürme
    @TypeConverter
    fun fromStories(stories: Stories): String {
        return gson.toJson(stories)
    }

    @TypeConverter
    fun toStories(storiesString: String): Stories {
        return gson.fromJson(storiesString, Stories::class.java)
    }

    // Events için dönüştürme
    @TypeConverter
    fun fromEvents(events: Events): String {
        return gson.toJson(events)
    }

    @TypeConverter
    fun toEvents(eventsString: String): Events {
        return gson.fromJson(eventsString, Events::class.java)
    }

    // Series için dönüştürme
    @TypeConverter
    fun fromSeries(series: Series): String {
        return gson.toJson(series)
    }

    @TypeConverter
    fun toSeries(seriesString: String): Series {
        return gson.fromJson(seriesString, Series::class.java)
    }


}

