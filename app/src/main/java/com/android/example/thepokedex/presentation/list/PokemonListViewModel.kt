package com.android.example.thepokedex.presentation.list

import com.android.example.thepokedex.domain.Pokemon
import androidx.lifecycle.*
import com.android.example.thepokedex.domain.PokemonDetails
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

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _content = repository.pokemons
    val content : LiveData<List<PokemonDetails>>
            get() = _content

init {
    showLoading()
}
    fun loadData(){
//            _content.value = repository.pokemons.value
            if(_content.value.isNullOrEmpty()) {
            viewModelScope.launch {
                try {
                    delay(100)
                    repository.refreshPokemonData()
                    loadingDone()
                    _eventNetworkError.value = false
                    _isNetworkErrorShown.value = false
                } catch (networkError: IOException) {
                    if (_content.value.isNullOrEmpty())
                        _eventNetworkError.value = true
                }
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    fun showLoading(){
        _isLoading.value = true
    }

    fun loadingDone(){
        _isLoading.value = false
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