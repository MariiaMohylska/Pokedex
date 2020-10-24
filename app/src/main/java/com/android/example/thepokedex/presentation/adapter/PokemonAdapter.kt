package com.android.example.thepokedex.presentation.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.example.thepokedex.R
import com.android.example.thepokedex.domain.Pokemon
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class PokemonAdapter : ListAdapter<Pokemon, PokemonAdapter.ViewHolder>(
    PokemonDiffCallback()
){

    var pokemonOnClickListener: PokemonOnClickListener? = null

    interface PokemonOnClickListener {
        fun onClicked(id: Int)
    }

    class ViewHolder private constructor(view: View):RecyclerView.ViewHolder(view){
        private val name = view.findViewById<TextView>(R.id.pokemon_name)
        private val image = view.findViewById<ImageView>(R.id.pokemon_img)
        fun bind(pokemon: Pokemon,
                 pokemonOnClickListener: PokemonOnClickListener?
        ){
            itemView.setOnClickListener{
                pokemonOnClickListener?.onClicked(pokemon.id)
            }

            name.text = pokemon.name
            Picasso.get()
                .load(pokemon.imgUrl)
                .into(image, object: Callback {
                    override fun onSuccess() {
                        Log.d("Adapter", "Loaded")
                    }

                    override fun onError(e: Exception?) {
                        Log.d("Adapter", "Error", e)
                    }
                })

        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                return ViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
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
        holder.bind(getItem(position)
            , pokemonOnClickListener
        )
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