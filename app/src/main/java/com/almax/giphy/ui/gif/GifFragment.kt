package com.almax.giphy.ui.gif

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.almax.giphy.GifApplication
import com.almax.giphy.databinding.FragmentGifBinding
import com.almax.giphy.di.component.DaggerGifComponent
import com.almax.giphy.di.component.GifComponent
import com.almax.giphy.di.module.GifModule
import com.almax.giphy.ui.base.UiState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject

class GifFragment : Fragment() {

    private lateinit var component: GifComponent

    @Inject
    lateinit var viewModel: GifViewModel

    @Inject
    lateinit var adapter: GifAdapter

    private var _binding: FragmentGifBinding? = null
    private val binding
        get() = _binding!!

    companion object {
        @JvmStatic
        fun newInstance() =
            GifFragment()
    }

    override fun onAttach(context: Context) {
        injectDependencies(context)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGifBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding.rvGif.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@GifFragment.adapter
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun injectDependencies(context: Context) {
        component = DaggerGifComponent
            .builder()
            .applicationComponent((context.applicationContext as GifApplication).component)
            .gifModule(GifModule(this@GifFragment, context))
            .build()
        component.inject(this@GifFragment)
    }
}