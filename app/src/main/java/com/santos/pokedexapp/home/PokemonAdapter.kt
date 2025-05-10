package com.santos.pokedexapp.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.santos.pokedexapp.data.PokemonItem
import com.santos.pokedexapp.databinding.ItemPokemonGridBinding

class PokemonAdapter(
    private val pokemons: List<PokemonItem>,
    private val onItemClick: (PokemonItem) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    inner class PokemonViewHolder(val binding: ItemPokemonGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PokemonItem) {
            binding.textViewPokemonName.text = item.name
            val id = item.url.split("/").filter { it.isNotEmpty() }.last()
            val imageUrl =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
            Glide.with(binding.imageViewPokemon.context)
                .load(imageUrl)
                .into(binding.imageViewPokemon)

            binding.root.setOnClickListener { onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)

    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemons[position])
        holder.itemView.setOnClickListener {
            onItemClick(pokemons[position])
        }
    }

    override fun getItemCount(): Int = pokemons.size
}
