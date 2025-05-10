package com.santos.pokedexapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.santos.pokedexapp.R
import com.santos.pokedexapp.databinding.FragmentPokemonListBinding
import com.santos.pokedexapp.home.PokemonPagingAdapter
import com.santos.pokedexapp.home.PokemonViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonListFragment : Fragment(R.layout.fragment_pokemon_list) {

    private var _binding: FragmentPokemonListBinding? = null
    private val binding get() = _binding!!

    // injeta corretamente nosso ViewModel
    private val pokemonViewModel: PokemonViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1) Configura adapter e RecyclerView
        val adapter = PokemonPagingAdapter { item ->
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PokemonDetailFragment.newInstance(item))
                .addToBackStack(null)
                .commit()
        }

        binding.recyclerViewPokemons.layoutManager =
            GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
        binding.recyclerViewPokemons.adapter = adapter

        // 2) Mostra o ProgressBar enquanto carrega a primeira página
        adapter.addLoadStateListener { loadStates ->
            binding.progressBar.isVisible =
                loadStates.source.refresh is LoadState.Loading
        }

        // 3) Observa o fluxo de páginas (apenas UMA vez!)
        viewLifecycleOwner.lifecycleScope.launch {
            pokemonViewModel.pagingDataFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        // 4) Escuta buscas no SearchView e repassa pro ViewModel
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                pokemonViewModel.setQuery(query)
                binding.searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                pokemonViewModel.setQuery(newText)
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}