package com.android.example.thepokedex

import android.app.Activity
import android.app.Application
import com.android.example.thepokedex.data.PokedexApiService
import com.android.example.thepokedex.data.PokemonApi
import com.android.example.thepokedex.data.PokemonRepositoryImpl
import com.android.example.thepokedex.database.PokemonDatabase
import com.android.example.thepokedex.database.getDatabase
import com.android.example.thepokedex.domain.PokemonRepository
import dagger.Module
import dagger.Provides

@Module
class NetworkAndDatabaseModule{
    @Provides
    fun providesApi(): PokedexApiService = PokemonApi.retrofitService

    @Provides
    fun providesApplication(): Application = App.INSTANCE

    @Provides
    fun providesDatabase(app: Application): PokemonDatabase = getDatabase(app)

    @Provides
    fun providesRepository(db: PokemonDatabase, api: PokedexApiService): PokemonRepository
            = PokemonRepositoryImpl(db, api)
}