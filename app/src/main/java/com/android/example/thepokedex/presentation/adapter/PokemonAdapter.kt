package com.android.example.thepokedex.presentation.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.example.thepokedex.domain.Pokemon
import com.android.example.thepokedex.databinding.ItemPokemonBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class PokemonAdapter : ListAdapter<Pokemon, PokemonAdapter.ViewHolder>(
    PokemonDiffCallback()
){

    class ViewHolder private constructor(val binding: ItemPokemonBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(pokemon: Pokemon){
            binding.pokemon = pokemon
            Picasso.get()
                .load(pokemon.imgUrl)
                .into(binding.pokemonImg, object: Callback {
                    override fun onSuccess() {
                        Log.d("Adapter", "Loaded")
                    }

                    override fun onError(e: Exception?) {
                        Log.d("Adapter", "Error", e)
                    }
                })
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                return ViewHolder(
                    ItemPokemonBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class PokemonDiffCallback : DiffUtil.ItemCallback<Pokemon>(){
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }

}