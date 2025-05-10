package com.santos.pokedexapp.model

import com.santos.pokedexapp.api.PokeApiService
import com.santos.pokedexapp.data.PokemonDetailResponse
import com.santos.pokedexapp.data.PokemonResponse

class PokemonRepository(val apiService: PokeApiService) {

    suspend fun getPokemons(limit: Int, offset: Int): PokemonResponse {
        return apiService.getPokemons(limit, offset)
    }

    suspend fun getPokemonDetail(id: Int): PokemonDetailResponse {
        return apiService.getPokemonDetail(id)
    }
}