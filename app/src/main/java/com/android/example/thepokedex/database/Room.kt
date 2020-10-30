package com.android.example.thepokedex.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PokemonDao{
    @Query("select * from pokemondb")
    fun getPokemons(): LiveData<List<PokemonDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( pokemons: List<PokemonDB>)
}

@Database(entities = [PokemonDB::class], version = 1, exportSchema = false)
abstract class PokemonDatabase: RoomDatabase(){
    abstract val pokemonDao: PokemonDao
}

private lateinit var INSTANCE: PokemonDatabase

fun getDatabase(context: Context): PokemonDatabase{
    synchronized(PokemonDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext,
            PokemonDatabase::class.java,
            "pokemon").build()
        }
    }
    return INSTANCE
}
