package com.android.example.thepokedex.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.example.thepokedex.App
import com.android.example.thepokedex.R

class TypesAdapter() : ListAdapter<String, TypesAdapter.ViewHolder>(
    TypeDiffCallback()
) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val type = view.findViewById<TextView>(R.id.type)

        fun bind(
            types: String
        ) {
            when(types) {
                "normal" ->  {type.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.normal).toDrawable()}
                "fighting" -> {type.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.fighting).toDrawable()}
                "flying" -> {type.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.flying).toDrawable()}
                "poison" -> {type.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.poison).toDrawable()}
                "ground" ->{type.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.ground).toDrawable()}
                "rock" -> {type.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.rock).toDrawable()}
                "bug" -> {type.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.bug).toDrawable()}
                "ghost" -> {type.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.ghost).toDrawable()}
                "steel" -> {type.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.steel).toDrawable()}
                "fire" -> {type.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.fire).toDrawable()}
                "water" -> {type.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.water).toDrawable()}
                "grass" -> {type.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.grass).toDrawable()}
                "electric" -> {type.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.electric).toDrawable()}
                "psychic" -> {type.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.psychic).toDrawable()}
                "ice" -> {type.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.ice).toDrawable()}
                "dragon" -> {type.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.dragon).toDrawable()}
                "dark" -> {type.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.dark).toDrawable()}
                "fairy" -> {type.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.fairy).toDrawable()}
                "unknown" -> {type.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.unknown).toDrawable()}
                "shadow" -> {type.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.shadow).toDrawable()}
            }
            type.text = types
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                return ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_types, parent, false)
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
            getItem(position)
        )
    }


}

class TypeDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}