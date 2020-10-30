package com.android.example.thepokedex.data

import androidx.core.net.toUri

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonPartialInfo>
)

data class PokemonPartialInfo(
    val name: String,
    val url: String
)


val PokemonPartialInfo.id: Int
get() =  url.toUri().lastPathSegment?.toInt() ?:1

val PokemonPartialInfo.imageUrl: String
get() =  "https://pokeres.bastionbot.org/images/pokemon/$id.png"


