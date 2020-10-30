package com.android.example.thepokedex.data

data class PokemonInfoResponse(
    val id: Int,
    val name: String,
    val abilities: List<Ability>,
    val height: String,
    val weight: String,
    val types: List<Types>
){
    data class Ability(
        val ability: AbilityParsialInfo,
        val slot: Int
    )

    data class AbilityParsialInfo(
        val name: String,
        val url: String
    )

    data class Types(
        val slot: Int,
        val type: TypeParsialInfo
    )

    data class TypeParsialInfo(
        val name: String,
        val url: String
    )
}

val PokemonInfoResponse.imageUrl: String
    get() = "https://pokeres.bastionbot.org/images/pokemon/$id.png"