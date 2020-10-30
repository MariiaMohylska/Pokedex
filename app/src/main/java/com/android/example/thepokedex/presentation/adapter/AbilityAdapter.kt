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

class AbilityAdapter() : ListAdapter<String, AbilityAdapter.ViewHolder>(
    AbilityDiffCallback()
) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ability = view.findViewById<TextView>(R.id.ability)

        fun bind(
            abilities: String,
            position: Int
        ) {
            if (abilities != "") {
                if (position % 3 == 0 || position == 0) {
                    ability.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.insomnia).toDrawable()
                } else {
                    if (position % 2 != 0) {
                        ability.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.color_change).toDrawable()
                    } else {
                        if (position % 2 == 0) {
                            ability.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.immunity).toDrawable()
                        } else {
                            ability.background = ContextCompat.getColor(App.INSTANCE.applicationContext, R.color.ability).toDrawable()
                        }
                    }
                }

            }

        ability.text = abilities

    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_abilities, parent, false)
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
        getItem(position),
        position
    )
}


}

class AbilityDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}