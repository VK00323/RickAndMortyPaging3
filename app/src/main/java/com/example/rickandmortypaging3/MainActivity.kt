package com.example.rickandmortypaging3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rickandmortypaging3.di.App

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}