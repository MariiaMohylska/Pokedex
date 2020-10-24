package com.android.example.thepokedex.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.android.example.thepokedex.data.PokemonRepositoryImpl
import com.android.example.thepokedex.database.getDatabase
import com.android.example.thepokedex.database.toPokemonDetailsList
import com.android.example.thepokedex.domain.PokemonDetails
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PokemonDetailsViewModel(val application: Application, val id: Int) : ViewModel() {
    private val repository : PokemonRepositoryImpl = PokemonRepositoryImpl(getDatabase(application))

    private val _pokemonDetails = MutableLiveData<PokemonDetails>()
    val pokemonDetails: LiveData<PokemonDetails>
        get() = _pokemonDetails

    init {
       loadData()
    }

    fun loadData(){
        viewModelScope.launch {
            _pokemonDetails.value = repository.getPokemonById(id)
        }
    }

    class Factory(val application: Application, val id: Int): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(PokemonDetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PokemonDetailsViewModel(application, id) as T
            }
            throw  IllegalArgumentException("Unable to construct viewmodel")
        }

    }
}