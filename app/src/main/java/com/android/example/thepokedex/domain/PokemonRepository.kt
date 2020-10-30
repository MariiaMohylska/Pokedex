package com.android.example.thepokedex.domain

import androidx.lifecycle.LiveData
import com.android.example.thepokedex.database.PokemonDB

interface PokemonRepository {
    val pokemons: LiveData<List<PokemonDetails>>
    suspend fun getPokemonList():List<Pokemon>
    suspend fun getPokemonById(id: Int): PokemonFullInfo
    suspend fun refreshPokemonData()
}