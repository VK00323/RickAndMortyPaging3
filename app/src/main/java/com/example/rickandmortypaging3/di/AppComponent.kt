package com.example.rickandmortypaging3.di

import com.example.rickandmortypaging3.MainActivity
import dagger.Component

@Component(modules = [RetrofitModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
}