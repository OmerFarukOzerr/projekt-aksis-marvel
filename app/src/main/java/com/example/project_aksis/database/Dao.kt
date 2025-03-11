package com.example.project_aksis.database

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.project_aksis.model.Result

@androidx.room.Dao
interface  Dao {
//
    @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertHero(heroResult: Result)

    @Query("SELECT * FROM 'save_chars'")
        suspend fun takeHeroes() : List<Result>

    @Query("SELECT * FROM 'save_chars'  WHERE id IN (:heroId)")
        suspend fun takeHeroById(heroId : Int) : Result

    @Query("DELETE FROM 'save_chars' WHERE id IN (:heroId)" )
    suspend fun deleteHeroById(heroId : Int)

    @Query("SELECT EXISTS (SELECT 1 FROM 'save_chars' WHERE id = :heroID)")
    fun existsHeroes(heroID: Int): LiveData<Boolean>


}