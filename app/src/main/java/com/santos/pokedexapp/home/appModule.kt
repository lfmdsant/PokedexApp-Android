package com.santos.pokedexapp.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.santos.pokedexapp.api.PokeApiService
import com.santos.pokedexapp.model.PokemonRepository
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Helper para verificar conectividade
fun hasNetwork(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val nw = cm.activeNetwork ?: return false
    val caps = cm.getNetworkCapabilities(nw) ?: return false
    return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

// Módulo de rede: cache + logging + interceptors
val networkModule = module {

    // Cache de 5 MiB
    single {
        val cacheSize = 5L * 1024 * 1024
        Cache(androidContext().cacheDir, cacheSize)
    }

    single {
        val cache = get<Cache>()

        // Interceptor para respostas de rede (cache em 60s)
        val networkInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request())
            response.newBuilder()
                .header("Cache-Control", "public, max-age=60")
                .build()
        }

        // Interceptor offline (até 7 dias)
        val offlineInterceptor = Interceptor { chain ->
            var request = chain.request()
            if (!hasNetwork(androidContext())) {
                request = request.newBuilder()
                    .header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=${7 * 24 * 60 * 60}"
                    )
                    .build()
            }
            chain.proceed(request)
        }

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(networkInterceptor)
            .addInterceptor(offlineInterceptor)
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<PokeApiService> {
        get<Retrofit>().create(PokeApiService::class.java)
    }
}

// Módulo de app: repositório e ViewModel
val appModule = module {
    single { PokemonRepository(get()) }
    viewModel { PokemonViewModel(get()) }
}