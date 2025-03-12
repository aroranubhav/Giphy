package com.almax.giphy.ui.gif

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.almax.giphy.GifApplication
import com.almax.giphy.databinding.ActivityGifBinding
import com.almax.giphy.di.component.DaggerGifComponent
import com.almax.giphy.di.component.GifComponent
import com.almax.giphy.di.module.GifModule
import com.almax.giphy.ui.base.UiState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject

class GifActivity : AppCompatActivity() {

    private lateinit var component: GifComponent
    private lateinit var binding: ActivityGifBinding

    @Inject
    lateinit var adapter: GifAdapter

    @Inject
    lateinit var viewModel: GifViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityGifBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()
    }

    private fun setupUi() {
        binding.rvGif.apply {
            layoutManager = LinearLayoutManager(this@GifActivity)
            adapter = this@GifActivity.adapter
            setHasFixedSize(true)
            //TODO: set item decorator
        }
        observeDataAndUpdateUi()
    }

    private fun observeDataAndUpdateUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->

                    when (state) {
                        is UiState.Success -> {
                            updateProgressBarVisibility(false)
                            adapter.setData(state.data)
                        }

                        is UiState.Error -> {
                            updateProgressBarVisibility(false)
                            Snackbar.make(
                                binding.root,
                                state.error,
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }

                        is UiState.Loading -> {
                            updateProgressBarVisibility(true)
                        }
                    }
                }
            }
        }
    }

    private fun updateProgressBarVisibility(state: Boolean) {
        binding.pbGif.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun injectDependencies() {
        component = DaggerGifComponent
            .builder()
            .applicationComponent((application as GifApplication).component)
            .gifModule(GifModule(this@GifActivity))
            .build()
        component.inject(this@GifActivity)
    }
}