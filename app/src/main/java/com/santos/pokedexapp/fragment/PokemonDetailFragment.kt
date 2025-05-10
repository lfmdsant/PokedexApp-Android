package com.santos.pokedexapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.santos.pokedexapp.R
import com.santos.pokedexapp.data.PokemonItem
import com.santos.pokedexapp.databinding.FragmentPokemonDetailBinding
import com.santos.pokedexapp.home.PokemonViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonDetailFragment : Fragment() {

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!

    // 1) Delegate do Koin
    private val viewModel: PokemonViewModel by viewModel()

    // 2) O PokemonItem precisa ser Parcelable
    private lateinit var item: PokemonItem

    companion object {
        private const val ARG_POKEMON = "arg_pokemon"
        fun newInstance(p: PokemonItem) = PokemonDetailFragment().apply {
            arguments = Bundle().apply { putParcelable(ARG_POKEMON, p) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 3) Recupera o item do Bundle
        item = requireArguments().getParcelable(ARG_POKEMON)!!
    }

    override fun onCreateView(inflater: LayoutInflater, c: ViewGroup?, s: Bundle?) =
        FragmentPokemonDetailBinding
            .inflate(inflater, c, false)
            .also { _binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // 1) Extrai ID da URL
        val id = item.url.trimEnd('/').split("/").last().toInt()

        // 2) Dispara o fetch de detalhe
        viewModel.fetchPokemonDetail(id)

        // 3) Observa o StateFlow e atualiza UI
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.detail.collectLatest { d ->
                d?.let {
                    binding.textViewNameDetail.text = it.name.replaceFirstChar(Char::titlecase)
                    binding.textViewStats.text = "Altura: ${it.height} | Peso: ${it.weight}"
                    val tipos = it.types.joinToString { ts -> ts.type.name.replaceFirstChar(Char::titlecase) }
                    binding.textViewTypes.text = "Tipos: $tipos"
                    Log.d("DetailFragment", "URL da imagem: ${it.sprites.frontDefault}")

                    val artworkUrl =
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/" +
                                "sprites/pokemon/other/official-artwork/$id.png"
                    Log.d("DetailFragment", "Usando artworkUrl = $artworkUrl")

                    // 2) Carrega via Glide
                    Glide.with(this@PokemonDetailFragment)
                        .load(artworkUrl)
                        .placeholder(R.drawable.ic_placeholder)
                        .error(R.drawable.ic_error)
                        .into(binding.imageViewDetail)

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}