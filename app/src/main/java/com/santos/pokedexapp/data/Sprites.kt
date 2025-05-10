package com.santos.pokedexapp.data

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)

data class Sprites(
    @Json(name = "front_default")
    val frontDefault: String?,
    val other: OtherSprites? = null
) : Parcelable
