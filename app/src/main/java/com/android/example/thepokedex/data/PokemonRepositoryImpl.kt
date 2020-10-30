package com.android.example.thepokedex.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.palette.graphics.Palette
import com.android.example.thepokedex.R
import com.android.example.thepokedex.database.*
import com.android.example.thepokedex.domain.Pokemon
import com.android.example.thepokedex.domain.PokemonDetails
import com.android.example.thepokedex.domain.PokemonFullInfo
import com.android.example.thepokedex.domain.PokemonRepository
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.Exception
import java.net.URL

class PokemonRepositoryImpl(
    private val context: Context,
    private val database: PokemonDatabase,
    private val api: PokedexApiService
) : PokemonRepository {

    override val pokemons: LiveData<List<PokemonDetails>> =
        Transformations.map(database.pokemonDao.getPokemons()) {
            it.toPokemonDetailsList()
        }

    override suspend fun getPokemonList(): List<Pokemon> = api.getPokemonList()
        .results.map { PokemonPartialInfo ->
            Pokemon(
                PokemonPartialInfo.id,
                PokemonPartialInfo.name,
                PokemonPartialInfo.imageUrl
            )
        }


    override suspend fun getPokemonById(id: Int): PokemonFullInfo {

        try {
            val pokemonParsialInfo = api.getPokemonInfo(id)
            val abilities = pokemonParsialInfo.abilities.map {
                it.ability.name
            }
            val height = pokemonParsialInfo.height
            val weight = pokemonParsialInfo.weight
            val types = pokemonParsialInfo.types.map {
                it.type.name
            }

            val pokemonInfo = PokemonFullInfo(
                pokemonParsialInfo.id,
                pokemonParsialInfo.name,
                pokemonParsialInfo.imageUrl,
                abilities,
                height,
                weight,
                types
            )
            return pokemonInfo

        } catch (e: Exception) {
            Log.i("getPokemonById", e.toString())
            throw e
        }
    }

    override suspend fun refreshPokemonData() {
        withContext(Dispatchers.IO) {

            val pokemonList = getPokemonList()
                .toPokemonFullInfoList()
                .toPokemonDetails()
            database.pokemonDao.insertAll(pokemonList.toPokemonDBList())
        }
    }


     suspend fun List<Pokemon>.toPokemonFullInfoList(): List<PokemonFullInfo> {

        return this.map { pokemon ->

                this@PokemonRepositoryImpl.getPokemonById(pokemon.id)

        }
    }

    fun List<PokemonFullInfo>.toPokemonDetails(): List<PokemonDetails> {
        return this.map { pokemon ->
            PokemonDetails(
                pokemon.id,
                pokemon.name,
                pokemon.imageUrl,
                pokemon.abilities
            )
        }
    }



}

