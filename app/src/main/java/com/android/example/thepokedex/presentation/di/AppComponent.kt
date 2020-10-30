package com.android.example.thepokedex.presentation.di

import android.app.Application
import android.content.Context
import com.android.example.thepokedex.database.PokemonDatabase
import com.android.example.thepokedex.domain.PokemonRepository
import com.android.example.thepokedex.presentation.list.PokemonListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkAndDatabaseModule::class])
interface AppComponent {
    fun inject(fragment: PokemonListFragment)
    fun repository(): PokemonRepository
    fun application(): Application
    fun database(): PokemonDatabase
    fun context(): Context
}