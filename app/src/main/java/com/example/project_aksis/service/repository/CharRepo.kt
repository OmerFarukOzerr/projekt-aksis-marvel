package com.example.project_aksis.service.repository

import androidx.lifecycle.LiveData
import com.example.project_aksis.database.Dao
import com.example.project_aksis.model.Result
import com.example.project_aksis.util.Constants.EMPTY_DB
import com.example.project_aksis.util.Resource
import javax.inject.Inject

class CharRepo @Inject constructor(
    private val dao : Dao
) : CharRepository {
    override suspend fun insertHero(result: Result) {
        dao.insertHero(result)
    }

    override suspend fun takeHeroes(): Resource<List<Result>> {
        val response = dao.takeHeroes()

        return if(response.isEmpty()) {
            Resource.error(EMPTY_DB, null)

        } else {
            Resource.success(response)
        }
    }

    override suspend fun takeHero(heroId: Int): Resource<Result> {
        return Resource.success(dao.takeHeroById(heroId))
    }

    override suspend fun deleteHeroById(heroId: Int) {
        dao.deleteHeroById(heroId)
    }

    override fun checkFav(heroId: Int): LiveData<Boolean> {
        return dao.existsHeroes(heroId)
    }

}