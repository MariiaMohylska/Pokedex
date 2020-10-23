package com.android.example.thepokedex.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.android.example.thepokedex.R

class PokemonDetailsFragment: Fragment(R.layout.pokemon_details_fragment) {

    private val pokemonId: Int
        get() = requireArguments().getInt(POKEMON_ID)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
//        Logic
    }

    companion object{
        private const val POKEMON_ID = "pokemon_id"

        fun getNewInstance(id: Int) = PokemonDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(POKEMON_ID, id)
                }
            }
        }
}