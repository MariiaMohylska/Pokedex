package com.android.example.thepokedex.data

import com.android.example.thepokedex.domain.Pokemon
import com.android.example.thepokedex.domain.PokemonRepository
import com.android.example.thepokedex.domain.pokemonList

class PokemonRepositoryImpl : PokemonRepository {
    override fun getPokemonList(): List<Pokemon> = pokemonList;
}