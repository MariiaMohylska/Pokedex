package com.android.example.thepokedex.domain

interface PokemonRepository {
    fun getPokemonList() :List<Pokemon>
}