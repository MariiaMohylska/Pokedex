package com.android.example.thepokedex.domain

data class PokemonDetails(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val abilities: List<String>
)