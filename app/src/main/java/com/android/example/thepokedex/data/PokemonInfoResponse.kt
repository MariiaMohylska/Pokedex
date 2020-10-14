package com.android.example.thepokedex.data

data class PokemonInfoResponse(
    val id: Int,
    val name: String,
    val abilities: List<Ability>
){
    data class Ability(
        val ability: AbilityParsialInfo,
        val slot: Int
    )

    data class AbilityParsialInfo(
        val name: String,
        val url: String
    )
}

val PokemonInfoResponse.imageUrl: String
    get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"