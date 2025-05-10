package com.santos.pokedexapp

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.santos.pokedexapp.api.PokeApiService
import com.santos.pokedexapp.data.PokemonItem

class PokemonPagingSource(
    private val apiService: PokeApiService,
    private val filterName: String
) : PagingSource<Int, PokemonItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonItem> {
        return try {
            if (filterName.isNotBlank()) {
                // busca EXATA por nome
                val detail = apiService.getPokemonDetailByName(filterName)
                val item = PokemonItem(
                    name = detail.name,
                    url  = "https://pokeapi.co/api/v2/pokemon/${detail.id}/"
                )
                LoadResult.Page(
                    data    = listOf(item),
                    prevKey = null,
                    nextKey = null
                )
            } else {
                // paginação padrão
                val offset   = params.key ?: 0
                val response = apiService.getPokemonList(limit = params.loadSize, offset = offset)
                LoadResult.Page(
                    data    = response.results,
                    prevKey = if (offset == 0) null else offset - params.loadSize,
                    nextKey = if (response.next == null) null else offset + params.loadSize
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonItem>): Int? = null
}
