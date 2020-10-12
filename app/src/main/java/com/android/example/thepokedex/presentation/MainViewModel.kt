package com.android.example.thepokedex.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.thepokedex.data.PokemonRepositoryImpl
import com.android.example.thepokedex.domain.Pokemon
import com.android.example.thepokedex.domain.PokemonRepository
import android.os.Handler
import kotlin.random.Random

class MainViewModel: ViewModel() {
    private val repository : PokemonRepository = PokemonRepositoryImpl()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
            get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean>
            get() = _isError

    private val _content = MutableLiveData<List<Pokemon>>()
    val content
            get() = _content


    fun loadData(){
        _isLoading.value = true
        _isError.value = false
        _content.value = emptyList()

        Handler().postDelayed({
            if(Random.nextInt() % 10 == 0){
                _isLoading.value = false
                _isError.value = true
                _content.value = emptyList()
            }else{
                val data = repository.getPokemonList()
                _isError.value = false
                _isLoading.value = false
                _content.postValue(data)
            }
        }, 300)
    }

}