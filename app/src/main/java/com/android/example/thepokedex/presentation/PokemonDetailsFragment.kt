package com.android.example.thepokedex.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.example.thepokedex.R
import com.android.example.thepokedex.domain.PokemonDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_pokemon.*
import kotlinx.android.synthetic.main.item_pokemon.pokemon_img
import kotlinx.android.synthetic.main.item_pokemon.pokemon_name
import kotlinx.android.synthetic.main.pokemon_details_fragment.*

class PokemonDetailsFragment: Fragment(R.layout.pokemon_details_fragment) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val pokemonId = requireArguments().getInt("POKEMON_ID")

       val viewModel by lazy {
            val activity = requireNotNull(this.activity){
                "You can only access the viewModel after onActivityCreated()"
            }
            ViewModelProvider(this,
                PokemonDetailsViewModel.Factory(activity.application, pokemonId))
                .get(PokemonDetailsViewModel::class.java)
        }
        viewModel.pokemonDetails.observe(viewLifecycleOwner, Observer { pokemonDetails ->
            if(pokemonDetails != null){
                showPokemonDetails(pokemonDetails)
            }
        })
    }

    fun showPokemonDetails(pokemon: PokemonDetails){
        Picasso.get()
            .load(pokemon.imageUrl)
            .into(pokemon_img)
         pokemon_name.text = pokemon.name
        pokemon_ability.text = pokemon.abilities.toString()
    }

}