package com.android.example.thepokedex.presentation.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.example.thepokedex.App
import com.android.example.thepokedex.R
import com.android.example.thepokedex.presentation.adapter.PokemonAdapter
import kotlinx.android.synthetic.main.item_pokemon.*
import kotlinx.android.synthetic.main.pokemon_details_fragment.*
import kotlinx.android.synthetic.main.pokemon_list_fragment.*

class PokemonListFragment() : Fragment(R.layout.pokemon_list_fragment) {
    private val adapter = PokemonAdapter()


    private val viewModel : PokemonListViewModel by lazy{
        ViewModelProvider(this, PokemonListViewModel.Factory(App.INSTANCE.appComponent.repository()))
            .get(PokemonListViewModel::class.java)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.adapter = adapter


        viewModel.loadData()
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer {isNetworkErrorShown ->
            if(isNetworkErrorShown) onNetworkError()
        })
        viewModel.content.observe(viewLifecycleOwner, Observer { data ->
            if(data != null){
                recycler_view.visibility = View.VISIBLE
                loading_view.visibility = View.GONE
            } else {
                recycler_view.visibility = View.GONE
                loading_view.visibility = View.VISIBLE
            }
            adapter.submitList(data)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { loading ->
            if(loading) {
                loading_view.visibility = View.VISIBLE
                recycler_view.visibility = View.GONE
            }else {
                loading_view.visibility = View.GONE
                recycler_view.visibility = View.VISIBLE
            }

        })

        adapter.pokemonOnClickListener = object : PokemonAdapter.PokemonOnClickListener {
            override fun onClicked(id: Int, color: Int) {
                val action =
                    PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailsFragment(
                        id,
                        color
                    )
                findNavController().navigate(action)

            }
        }
    }


    fun onNetworkError(){
        if(!viewModel.isNetworkErrorShown.value!!){
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    companion object{
        var isFront: Boolean = true

        @JvmStatic
        fun front(front: Boolean){
            isFront = front

        }
    }


}

