package com.santos.pokedexapp.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.santos.pokedexapp.R
import com.santos.pokedexapp.data.PokemonItem
import com.santos.pokedexapp.databinding.ItemPokemonGridBinding

class PokemonPagingAdapter(
    private val onItemClick: (PokemonItem) -> Unit
) : PagingDataAdapter<PokemonItem, PokemonPagingAdapter.PokemonViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PokemonItem>() {
            override fun areItemsTheSame(old: PokemonItem, new: PokemonItem) =
                old.url == new.url

            override fun areContentsTheSame(old: PokemonItem, new: PokemonItem) =
                old == new
        }
    }

    inner class PokemonViewHolder(val binding: ItemPokemonGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PokemonItem?) {
            item ?: return
            binding.textViewPokemonName.text =
                item.name.replaceFirstChar(Char::titlecase)
            val id = item.url.trimEnd('/').split("/").last()
            val img =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
            Glide.with(binding.imageViewPokemon.context)
                .load(img)
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.imageViewPokemon)
            binding.root.setOnClickListener { onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonGridBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) =
        holder.bind(getItem(position))
}