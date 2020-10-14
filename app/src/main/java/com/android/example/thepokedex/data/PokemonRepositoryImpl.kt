package com.android.example.thepokedex.data

import com.android.example.thepokedex.domain.Pokemon
import com.android.example.thepokedex.domain.PokemonDetails
import com.android.example.thepokedex.domain.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class PokemonRepositoryImpl : PokemonRepository {
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
}