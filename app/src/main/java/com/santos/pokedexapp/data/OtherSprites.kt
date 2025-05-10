package com.santos.pokedexapp.data

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class OtherSprites(

    @Json(name = "official-artwork")
    val officialArtwork: ArtworkSprites? = null
): Parcelable
