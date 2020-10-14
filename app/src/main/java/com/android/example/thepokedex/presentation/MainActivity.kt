package com.android.example.thepokedex.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.android.example.thepokedex.R
import com.android.example.thepokedex.domain.Pokemon
import com.android.example.thepokedex.presentation.adapter.PokemonAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val adapter = PokemonAdapter()
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.adapter = adapter

        viewModel.isLoading.observe(this, Observer {
            loadingView.visibility = if(it){
                View.VISIBLE
            }else{
                View.GONE
            }
        })

        viewModel.isError.observe(this, Observer {
            errorView.visibility = if(it){
                View.VISIBLE
            }else{
                View.GONE
            }
        })

        viewModel.content.observe(this, Observer { data ->
            recycler_view.visibility = if(data != null){
                View.VISIBLE
            } else {
                View.GONE
            }
            adapter.submitList(viewModel.content.value)
        })
        viewModel.loadData()
    }

}