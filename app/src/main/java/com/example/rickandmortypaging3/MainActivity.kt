package com.example.rickandmortypaging3

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortypaging3.adapters.CharacterAdapter
import com.example.rickandmortypaging3.adapters.CharacterLoaderStateAdapter
import com.example.rickandmortypaging3.di.App
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: CharacterViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        recyclerView = findViewById(R.id.RecyclerViewItem)
        progressBar = findViewById(R.id.progressBar)
        viewModel = ViewModelProvider(this)[CharacterViewModel::class.java]
        val adapter = CharacterAdapter()
        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = CharacterLoaderStateAdapter(),
            footer = CharacterLoaderStateAdapter()
        )
        lifecycleScope.launchWhenCreated {
            viewModel.getAllCharacter().collectLatest {
                adapter.submitData(it)
            }
        }
        (adapter).addLoadStateListener { state: CombinedLoadStates ->
            val refreshState = state.refresh
            recyclerView.isVisible = state.refresh != LoadState.Loading
            progressBar.isVisible = state.refresh == LoadState.Loading
            if (refreshState is LoadState.Error) {
                Snackbar.make(
                    recyclerView.rootView,
                    refreshState.error.localizedMessage ?: "Unknown Error",
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }
        }
    }
}