package com.example.project_aksis.service.repository

import com.example.project_aksis.model.HeroesListResult
import com.example.project_aksis.util.Resource

interface ApiRepository {

    suspend fun getCharactersFromAPI(offSet : Int, order : String) : Resource<HeroesListResult>
    suspend fun getCharactersByName(characterId : Int) : Resource<HeroesListResult>
}