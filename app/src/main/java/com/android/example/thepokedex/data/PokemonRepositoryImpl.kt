package com.android.example.thepokedex.data

import com.android.example.thepokedex.database.*
import com.android.example.thepokedex.domain.Pokemon
import com.android.example.thepokedex.domain.PokemonDetails
import com.android.example.thepokedex.domain.PokemonRepository
import kotlinx.coroutines.*
import java.lang.Exception

class PokemonRepositoryImpl(private val database: PokemonDatabase) : PokemonRepository {

    val pokemons= database.pokemonDao.getPokemons()

    override suspend fun getPokemonList(): List<Pokemon>{
        lateinit var pokemonList: List<Pokemon>
            withContext(Dispatchers.IO) {
                try {
                    pokemonList = PokemonApi.retrofitService.getPokemonList()
                        .results.map { PokemonPartialInfo ->
                            Pokemon(
                                PokemonPartialInfo.id,
                                PokemonPartialInfo.name,
                                PokemonPartialInfo.imageUrl
                            )
                        }
                } catch (e: Exception) {
                    throw e
                }
            }
        return pokemonList
    }

     override suspend fun getPokemonById(id: Int) : PokemonDetails {
        lateinit var pokemonInfo: PokemonDetails
        withContext(Dispatchers.IO){
            try {
                val pokemonParsialInfo = PokemonApi.retrofitService.getPokemonInfo(id)
                val abilities = pokemonParsialInfo.abilities.map {
                    it.ability.name
                }
                pokemonInfo = PokemonDetails(
                    pokemonParsialInfo.id,
                    pokemonParsialInfo.name,
                    pokemonParsialInfo.imageUrl,
                    abilities)

            }catch (e: Exception){
                throw e
            }
        }
        return pokemonInfo
    }

    override suspend fun refreshPokemonData() {
        withContext(Dispatchers.IO){
            val pokemonList = getPokemonList()
                .toPokemonDetailsList()
            database.pokemonDao.insertAll(pokemonList.toPokemonDBList())
        }
    }

    fun List<Pokemon>.toPokemonDetailsList(): List<PokemonDetails> {
        return this.map { Pokemon->
            runBlocking {
                this@PokemonRepositoryImpl.getPokemonById(Pokemon.id)
            }
        }
    }

}

fun List<PokemonDetails>.toPokemonList(): List<Pokemon>{
    return this.map { PokemonDetails ->
        Pokemon(PokemonDetails.id, PokemonDetails.name, PokemonDetails.imageUrl)
    }
}