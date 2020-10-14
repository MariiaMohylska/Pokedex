package com.android.example.thepokedex.domain

interface PokemonRepository {
    suspend fun getPokemonList():List<Pokemon>
    suspend fun getPokemonById(id: Int): PokemonDetails
}