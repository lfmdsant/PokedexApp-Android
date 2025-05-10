package com.santos.pokedexapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.santos.pokedexapp.PokemonPagingSource
import com.santos.pokedexapp.data.PokemonDetailResponse
import com.santos.pokedexapp.data.PokemonItem
import com.santos.pokedexapp.model.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class PokemonViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    // 1️⃣ Fluxo que guarda a string de busca
    private val _query = MutableStateFlow("")
    fun setQuery(q: String) { _query.value = q.trim().lowercase() }

    // 2️⃣ ÚNICO pagingDataFlow: repagina sempre que _query muda
    val pagingDataFlow: Flow<PagingData<PokemonItem>> =
        _query
            .flatMapLatest { q ->
                Pager(
                    config = PagingConfig(pageSize = 20, enablePlaceholders = false),
                    pagingSourceFactory = { PokemonPagingSource(repository.apiService, q) }
                ).flow
            }
            .cachedIn(viewModelScope)

    // 1) StateFlow para o detalhe
    private val _detail = MutableStateFlow<PokemonDetailResponse?>(null)
    val detail: StateFlow<PokemonDetailResponse?> = _detail

    // 3) Método de busca de detalhe
    fun fetchPokemonDetail(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getPokemonDetail(id)
                _detail.value = response
            } catch (e: Exception) {
                // aqui você pode logar ou propagar erro
            }
        }
    }
}