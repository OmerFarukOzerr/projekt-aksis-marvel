package com.example.project_aksis.service.repository

import androidx.lifecycle.LiveData
import com.example.project_aksis.model.Result
import com.example.project_aksis.util.Resource

interface CharRepository {

    suspend fun insertHero(result : Result)

    suspend fun takeHeroes() : Resource<List<Result>>

    suspend fun takeHero(heroId : Int) : Resource<Result>

    suspend fun deleteHeroById(heroId: Int)


    fun checkFav(heroId : Int) : LiveData<Boolean>
}