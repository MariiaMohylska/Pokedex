package com.android.example.thepokedex.presentation

import android.app.Application
import com.android.example.thepokedex.data.PokemonRepositoryImpl
import com.android.example.thepokedex.domain.Pokemon
import androidx.lifecycle.*
import com.android.example.thepokedex.database.getDatabase
import com.android.example.thepokedex.database.toPokemonList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

class PokemonListViewModel(application: Application): ViewModel() {
    private val repository : PokemonRepositoryImpl = PokemonRepositoryImpl(getDatabase(application))


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


    class Factory(val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PokemonListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PokemonListViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }

    }

}