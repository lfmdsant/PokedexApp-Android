package com.santos.pokedexapp.data

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class PokemonItem(

    val name: String,
    val url: String

): Parcelable