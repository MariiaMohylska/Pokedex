package com.android.example.thepokedex.domain

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.example.thepokedex.database.PokemonDatabase

interface PokemonRepository {
    suspend fun getPokemonList():List<Pokemon>
    suspend fun getPokemonById(id: Int): PokemonDetails
    suspend fun refreshPokemonData()
}