package com.android.example.thepokedex.domain

data class PokemonFullInfo(
    val id: Int,
    val name: String,
    val imageUrl: String,
    var abilities: List<String>,
    var height: String,
    var weight: String,
    val types: List<String>
)