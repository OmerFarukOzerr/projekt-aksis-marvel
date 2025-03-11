package com.example.project_aksis.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.project_aksis.model.Result

@Database(entities = arrayOf(Result::class), version = 9, exportSchema = false)
@TypeConverters(ModelConverter::class)
abstract class CharDataBase : RoomDatabase(){

    abstract fun getDao() : Dao
}