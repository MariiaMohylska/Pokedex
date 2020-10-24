package com.android.example.thepokedex.domain

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.example.thepokedex.database.PokemonDB
import com.android.example.thepokedex.database.PokemonDatabase

interface PokemonRepository {
    val pokemons: LiveData<List<PokemonDB>>
    suspend fun getPokemonList():List<Pokemon>
    suspend fun getPokemonById(id: Int): PokemonDetails
    suspend fun refreshPokemonData()
}