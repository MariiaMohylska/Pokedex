package com.android.example.thepokedex.domain

import android.graphics.Color

data class PokemonDetails(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val abilities: List<String>
)