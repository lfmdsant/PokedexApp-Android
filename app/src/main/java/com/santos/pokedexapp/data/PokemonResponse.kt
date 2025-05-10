package com.santos.pokedexapp.data

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class PokemonResponse(

    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonItem>

): Parcelable
