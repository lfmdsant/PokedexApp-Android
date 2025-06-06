package com.santos.pokedexapp.data

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class TypeSlot(
    val slot: Int,
    val type: TypeInfo
) : Parcelable
