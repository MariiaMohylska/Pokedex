package com.android.example.thepokedex.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.thepokedex.data.PokemonRepositoryImpl
import com.android.example.thepokedex.domain.Pokemon
import com.android.example.thepokedex.domain.PokemonRepository
import android.os.Handler
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception
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
        _content.value = null

        Handler().postDelayed({
            if(Random.nextInt() % 10 == 0){
                _isLoading.value = false
                _isError.value = true
                _content.value = null
            }else{
                _isError.value = false
                _isLoading.value = false
                viewModelScope.launch {
                    try {
                        val data = repository.getPokemonList()
                        _content.postValue(data)
                    }catch (e: Exception){
                        _isError.value = true
                    }
                }


            }
        }, 600)
    }

}