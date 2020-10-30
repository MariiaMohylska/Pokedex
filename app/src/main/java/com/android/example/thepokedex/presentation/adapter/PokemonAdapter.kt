package com.android.example.thepokedex.presentation.adapter


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.example.thepokedex.App
import com.android.example.thepokedex.R
import com.android.example.thepokedex.domain.PokemonDetails
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import java.io.IOException
import java.net.URL

class PokemonAdapter() : ListAdapter<PokemonDetails, PokemonAdapter.ViewHolder>(
    PokemonDiffCallback()
) {

    var pokemonOnClickListener: PokemonOnClickListener? = null

    interface PokemonOnClickListener {
        fun onClicked(id: Int, color: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.pokemon_name)
        private val image = view.findViewById<ImageView>(R.id.pokemon_img)
        private val abilities = view.findViewById<TextView>(R.id.abilities)
        private val backgroundFront = view.findViewById<ConstraintLayout>(R.id.front_card)
        private val backgroundBack = view.findViewById<ConstraintLayout>(R.id.back_card)

        fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()

        fun URL.toBitmap(): Bitmap? {
            return try {
                BitmapFactory.decodeStream(openStream())
            } catch (e: IOException) {
                Log.i("PokemonAdapter", "Convert to BitMap exeption")
                null
            }
        }

        fun bind(
            pokemon: PokemonDetails,
            pokemonOnClickListener: PokemonOnClickListener?
        ) {

            name.text = pokemon.name
            abilities.text = pokemon.abilities.joinToString(separator = "\n\n")


            val result: Deferred<Bitmap?> = GlobalScope.async {
                URL(pokemon.imageUrl).toBitmap()
            }
            var color = 0
            GlobalScope.launch(Dispatchers.IO) {
                val img = result.await()
                img?.let {
                    createPaletteSync(it).getMutedColor(
                        ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.design_default_color_primary)
                    )
                }?.let {
                    color = it
                    backgroundFront.setBackgroundColor(it)
                    backgroundBack.setBackgroundColor(it)
                }
            }

            image.setOnClickListener {
                pokemonOnClickListener?.onClicked(pokemon.id, color)
            }


            Picasso.get()
                .load(pokemon.imageUrl)
                .into(image, object : Callback {
                    override fun onSuccess() {
                        Log.d("Adapter", "Loaded")
                    }

                    override fun onError(e: Exception?) {
                        Log.d("Adapter", "Error", e)
                    }
                })

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                return ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_pokemon, parent, false)
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
        holder.bind(
            getItem(position), pokemonOnClickListener
        )
    }


}

class PokemonDiffCallback : DiffUtil.ItemCallback<PokemonDetails>() {
    override fun areItemsTheSame(oldItem: PokemonDetails, newItem: PokemonDetails): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PokemonDetails, newItem: PokemonDetails): Boolean {
        return oldItem == newItem
    }

}