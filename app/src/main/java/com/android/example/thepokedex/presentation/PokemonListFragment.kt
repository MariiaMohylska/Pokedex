package com.android.example.thepokedex.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.example.thepokedex.R
import com.android.example.thepokedex.presentation.adapter.PokemonAdapter
import kotlinx.android.synthetic.main.pokemon_list_fragment.*

class PokemonListFragment() : Fragment(R.layout.pokemon_list_fragment) {
    private val adapter = PokemonAdapter()
    private val viewModel : PokemonListViewModel by lazy{
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, PokemonListViewModel.Factory(activity.application))
            .get(PokemonListViewModel::class.java)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.adapter = adapter

        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer {isNetworkErrorShown ->
            if(isNetworkErrorShown) onNetworkError()
        })

        viewModel.content.observe(viewLifecycleOwner, Observer { data ->
            recycler_view.visibility = if(data != null){
                View.VISIBLE
            } else {
                View.GONE
            }
            adapter.submitList(viewModel.content.value)
        })
        viewModel.loadData()
    }

    fun onNetworkError(){
        if(!viewModel.isNetworkErrorShown.value!!){
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }


}

