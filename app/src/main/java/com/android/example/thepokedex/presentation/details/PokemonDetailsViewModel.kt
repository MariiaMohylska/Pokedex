package com.android.example.thepokedex.presentation.details

import androidx.lifecycle.*
import com.android.example.thepokedex.domain.PokemonDetails
import com.android.example.thepokedex.domain.PokemonRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PokemonDetailsViewModel(private val repository: PokemonRepository, val id: Int) : ViewModel() {
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

    class Factory(val repository: PokemonRepository, val id: Int): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(PokemonDetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PokemonDetailsViewModel(repository, id) as T
            }
            throw  IllegalArgumentException("Unable to construct viewmodel")
        }

    }
}