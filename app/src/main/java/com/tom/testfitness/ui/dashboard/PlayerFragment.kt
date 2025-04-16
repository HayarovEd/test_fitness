package com.tom.testfitness.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tom.testfitness.databinding.FragmentPlayerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerFragment : Fragment() {

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlayerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PlayerFragmentState.Error -> {
                    binding.progress.isVisible = false
                    binding.playerView.isVisible = false
                    binding.errorMessage.isVisible = true
                    binding.descTv.isVisible = false
                    binding.errorMessage.text = state.message
                }

                PlayerFragmentState.Loading -> {
                    binding.progress.isVisible = true
                    binding.playerView.isVisible = false
                    binding.errorMessage.isVisible = false
                    binding.descTv.isVisible = false
                }

                is PlayerFragmentState.Success -> {
                    binding.progress.isVisible = false
                    binding.playerView.isVisible = true
                    binding.errorMessage.isVisible = false
                    binding.descTv.isVisible = true
                    binding.descTv.text = state.description
                    binding.playerView.player = viewModel.player
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        binding.playerView.player?.pause()
    }

    override fun onResume() {
        super.onResume()
        binding.playerView.player?.play()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}