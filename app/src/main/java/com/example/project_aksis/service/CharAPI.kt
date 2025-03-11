package com.example.project_aksis.service

import com.example.project_aksis.hilt.Hilt
import com.example.project_aksis.model.HeroesListResult
import com.example.project_aksis.util.Constants.PUBLIC_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharAPI {
    @GET("v1/public/characters")
    suspend fun getCharacters(
        @Query("ts") ts : String = Hilt.ts,
        @Query("apikey") apiKey : String = PUBLIC_KEY,
        @Query("hash") hash : String = Hilt.generateHash(),
        @Query("offset") offSet : String,
        @Query("orderBy") orderBy: String

    ) : Response<HeroesListResult>


    @GET("v1/public/characters/{characterId}")
    suspend fun getCharactersByName(
        @Path("characterId") characterId : String,
        @Query("ts") ts : String = Hilt.ts,
        @Query("apikey") apiKey : String = PUBLIC_KEY,
        @Query("hash") hash : String = Hilt.generateHash()

    ) : Response<HeroesListResult>
}