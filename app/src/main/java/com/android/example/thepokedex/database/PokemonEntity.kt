package com.android.example.thepokedex.database


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.example.thepokedex.domain.PokemonDetails

@Entity
data class PokemonDB (
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String,
    val abilities: String)

fun List<PokemonDetails>.toPokemonDBList(): List<PokemonDB>{
   return this.map{
       PokemonDB(it.id, it.name, it.imageUrl, it.abilities.joinToString { it })
   }
}

fun  List<PokemonDB>.toPokemonDetailsList(): List<PokemonDetails>{
    return this.map{
        PokemonDetails(it.id, it.name, it.imageUrl, it.abilities.split(", "))
    }
}


