package com.santos.pokedexapp

import android.app.Application
import com.santos.pokedexapp.home.appModule
import com.santos.pokedexapp.home.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class PokedexApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PokedexApp)
            modules(listOf(networkModule, appModule))
        }
    }
}