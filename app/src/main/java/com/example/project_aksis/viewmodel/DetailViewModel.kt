package com.example.project_aksis.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_aksis.model.HeroesListResult
import com.example.project_aksis.model.Result
import com.example.project_aksis.service.repository.ApiRepository
import com.example.project_aksis.service.repository.CharRepository
import com.example.project_aksis.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val charRepository : CharRepository,
    private val apiRepository : ApiRepository
) : ViewModel() {

    private val _heroes = MutableLiveData<Resource<HeroesListResult>>()
    val heroes : LiveData<Resource<HeroesListResult>>
        get() = _heroes

    private val _heroDetailDb = MutableLiveData<Resource<Result>>()
    val heroDetailDb : LiveData<Resource<Result>>
        get() = _heroDetailDb

    fun getHeroDetailFromDb(heroId: Int) {
        viewModelScope.launch {
            _heroDetailDb.value = Resource.loading(null)
            val charDetail = charRepository.takeHero(heroId)
            _heroDetailDb.value = charDetail
        }
    }

    fun takeHeroById(heroId : Int) {
        viewModelScope.launch {
            _heroes.value = Resource.loading(null)
            val response = apiRepository.getCharactersByName(heroId)
            _heroes.value = response
        }
    }



    fun insertHeroDetail(result : Result) {
        viewModelScope.launch { charRepository.insertHero(result) }
    }


    fun deleteHeroDetail(heroId : Int) {
        viewModelScope.launch { charRepository.deleteHeroById(heroId) }
    }


    fun checkFav(heroId: Int) : LiveData<Boolean>  {
        return charRepository.checkFav(heroId)
    }
}