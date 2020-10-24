package com.android.example.thepokedex.presentation.list

import com.android.example.thepokedex.domain.Pokemon
import androidx.lifecycle.*
import com.android.example.thepokedex.database.toPokemonList
import com.android.example.thepokedex.domain.PokemonRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

class PokemonListViewModel(private val repository: PokemonRepository): ViewModel() {
    private val _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError : LiveData<Boolean>
        get() = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val _content = Transformations.map(repository.pokemons){
        it.toPokemonList()
    }
    val content : LiveData<List<Pokemon>>
            get() = _content

    init {
        loadData()
    }

    fun loadData(){
    viewModelScope.launch {
            try {
                delay(10)
                repository.refreshPokemonData()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            }catch (networkError: IOException){
                if(_content.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
         }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }


    class Factory(val repository: PokemonRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PokemonListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PokemonListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }

    }

}