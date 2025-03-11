package com.example.project_aksis.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_aksis.model.Data
import com.example.project_aksis.model.HeroesListResult
import com.example.project_aksis.model.Result
import com.example.project_aksis.service.repository.ApiRepository
import com.example.project_aksis.service.repository.CharRepository
import com.example.project_aksis.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val apiRepo : ApiRepository,
    private val charRepo : CharRepository
) :ViewModel() {

    private val dummyData = Data(0,0,0,0, listOf())
    private val dummyHeroesListResult = HeroesListResult(
        0,"","","","","",dummyData)

    private val _heroes = MutableLiveData<Resource<HeroesListResult>>()
    val heroes : LiveData<Resource<HeroesListResult>>
        get() = _heroes

    private val _heroesDb = MutableLiveData<Resource<List<Result>>>()
    val heroesDb : LiveData<Resource<List<Result>>>
        get() = _heroesDb

    fun takeHeroes(lastListItemCount : Int, order : String) {
        viewModelScope.launch {
            _heroes.value = Resource.loading(null)
            val response = apiRepo.getCharactersFromAPI(lastListItemCount,order)
            _heroes.value = response

        }
    }

    fun takeHeroesFromDb() {
        viewModelScope.launch {
            _heroesDb.value = Resource.loading(null)
            val response = charRepo.takeHeroes()
            _heroesDb.value = response
        }
    }

    fun checkFav(heroId: Int) : LiveData<Boolean>  {
        return charRepo.checkFav(heroId)
    }


    fun clearApiHeroes() {
        viewModelScope.launch {
            _heroes.value = Resource.loading(dummyHeroesListResult)
            _heroes.value = Resource.success(dummyHeroesListResult)
        }
    }

    fun clearDbHeroes() {
        _heroesDb.value = Resource.loading(listOf())
        _heroesDb.value = Resource.success(listOf())
    }

}