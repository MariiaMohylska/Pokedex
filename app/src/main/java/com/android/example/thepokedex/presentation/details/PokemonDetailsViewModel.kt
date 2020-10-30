package com.android.example.thepokedex.presentation.details

import androidx.lifecycle.*
import com.android.example.thepokedex.domain.PokemonDetails
import com.android.example.thepokedex.domain.PokemonFullInfo
import com.android.example.thepokedex.domain.PokemonRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.IllegalArgumentException

class PokemonDetailsViewModel(private val repository: PokemonRepository, val id: Int) : ViewModel() {
    private val _pokemonDetails = MutableLiveData<PokemonFullInfo>()
    val pokemonDetails: LiveData<PokemonFullInfo>
        get() = _pokemonDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean>
        get() = _isError

    init {
       showLoading()
    }

    fun loadData(){
        viewModelScope.launch {
            try {
                _pokemonDetails.value = repository.getPokemonById(id).apply {
                        if (this.abilities.size % 2 == 0) {
                            val newList = mutableListOf<String>()
                            this.abilities.map {
                                newList.add(it)
                            }
                            val number = this.abilities.size - 1
                            var count = 0
                            for (i in 1..100 step 2) {
                                newList.add(i, "")
                                count += 1
                                if (count == number) break
                            }
                            this.abilities = newList
                        }


                    val heigtDouble = this.height.toDouble() / 10
                    this.height = heigtDouble.toString().plus(" M")

                    val weightDouble = this.weight.toDouble() / 10
                    this.weight = weightDouble.toString().plus(" KG")
                    loadingDone()
                }
                _isError.value = false
            } catch (e: Exception){
                _isError.value = true
            }

        }
    }


    fun showLoading(){
        _isLoading.value = true
    }

    fun loadingDone(){
        _isLoading.value = false
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