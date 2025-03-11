package com.example.project_aksis.service.repository

import com.example.project_aksis.model.HeroesListResult
import com.example.project_aksis.service.CharAPI
import com.example.project_aksis.util.Constants.NO_NETWORK_CONNECTION
import com.example.project_aksis.util.Constants.NULL_DATA
import com.example.project_aksis.util.Constants.OTHER_ERROR
import com.example.project_aksis.util.NetworkHelper
import com.example.project_aksis.util.Resource
import javax.inject.Inject

class ApiRepo @Inject constructor(
    private val retrofit : CharAPI,
    private val networkHelper: NetworkHelper
) : ApiRepository{

    override suspend fun getCharactersFromAPI(offSet : Int, order : String): Resource<HeroesListResult> {

        return if(networkHelper.isNetworkConnected()) {
            val response = retrofit.getCharacters(offSet = offSet.toString(), orderBy = order)

            if (response.isSuccessful) {
                response.body()?.let {

                    return@let Resource.success(it)
                } ?: Resource.error(NULL_DATA, null)

            } else {
                Resource.error(OTHER_ERROR, null)
            }

        } else {
            Resource.error(NO_NETWORK_CONNECTION, null)
        }
    }

    override suspend fun getCharactersByName(characterId: Int): Resource<HeroesListResult> {
        return if(networkHelper.isNetworkConnected()) {
            val response = retrofit.getCharactersByName(characterId = characterId.toString())

            if (response.isSuccessful) {
                response.body()?.let {

                    return@let Resource.success(it)
                } ?: Resource.error(NULL_DATA, null)

            } else {
                Resource.error(OTHER_ERROR, null)
            }

        } else {
            Resource.error(NO_NETWORK_CONNECTION, null)
        }
    }

}