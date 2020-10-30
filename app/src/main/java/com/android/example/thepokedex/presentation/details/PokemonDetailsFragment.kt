package com.android.example.thepokedex.presentation.details

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.example.thepokedex.App
import com.android.example.thepokedex.R
import com.android.example.thepokedex.domain.PokemonDetails
import com.android.example.thepokedex.domain.PokemonFullInfo
import com.android.example.thepokedex.presentation.adapter.AbilityAdapter
import com.android.example.thepokedex.presentation.adapter.TypesAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_pokemon.pokemon_img
import kotlinx.android.synthetic.main.item_pokemon.pokemon_name
import kotlinx.android.synthetic.main.pokemon_details_fragment.*

class PokemonDetailsFragment : Fragment(R.layout.pokemon_details_fragment) {
    private val typeAdapter = TypesAdapter()
    private val abilityAdapter = AbilityAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemonId = requireArguments().getInt("POKEMON_ID")
        val color = requireArguments().getInt("COLOR")
        val viewModel by lazy {
            ViewModelProvider(
                this,
                PokemonDetailsViewModel.Factory(App.INSTANCE.appComponent.repository(), pokemonId)
            )
                .get(PokemonDetailsViewModel::class.java)
        }

        viewModel.loadData()

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { loading->
            if(loading){
                imgCard.visibility = View.GONE
                infoView.visibility = View.GONE
                loadingView.visibility = View.VISIBLE
            } else{
                imgCard.visibility = View.VISIBLE
                infoView.visibility = View.VISIBLE
                loadingView.visibility = View.GONE
            }
        })

        viewModel.isError.observe(viewLifecycleOwner, Observer { err ->
            if(err){
                imgCard.visibility = View.GONE
                infoView.visibility = View.GONE
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }else{
                imgCard.visibility = View.VISIBLE
                infoView.visibility = View.VISIBLE
            }
        })

        imgCard.setCardBackgroundColor(color)
        pokemon_types.adapter = typeAdapter
        pokemon_abilities.adapter = abilityAdapter
        pokemon_types.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        pokemon_abilities.layoutManager = GridLayoutManager(context, 3)
        viewModel.pokemonDetails.observe(viewLifecycleOwner, Observer { pokemonDetails ->
            if (pokemonDetails != null) {
                showPokemonDetails(pokemonDetails)
                typeAdapter.submitList(pokemonDetails.types)
                abilityAdapter.submitList(pokemonDetails.abilities)
            }
        })
    }

    fun showPokemonDetails(pokemon: PokemonFullInfo) {
        Picasso.get()
            .load(pokemon.imageUrl)
            .into(pokemon_img)
        pokemon_name.text = pokemon.name
        pokemon_height.text = pokemon.height
        pokemon_weight.text = pokemon.weight
    }

}