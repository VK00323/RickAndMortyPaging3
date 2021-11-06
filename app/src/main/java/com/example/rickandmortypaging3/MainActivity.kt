package com.example.rickandmortypaging3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortypaging3.adapters.CharacterAdapter
import com.example.rickandmortypaging3.di.App
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: CharacterViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        recyclerView = findViewById(R.id.RecyclerViewItem)
        viewModel = ViewModelProvider(this)[CharacterViewModel::class.java]
        val adapter = CharacterAdapter()
        recyclerView.adapter = adapter
        lifecycleScope.launchWhenCreated {
            viewModel.getAllCharacter().collectLatest {
                adapter.submitData(it)
            }
        }




    }
}