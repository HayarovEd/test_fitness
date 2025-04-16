package com.tom.testfitness.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tom.testfitness.databinding.FragmentHomeBinding
import com.tom.testfitness.domain.model.FitnessWorkout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HomeState.Error -> {
                    binding.progress.isVisible = false
                    binding.searchView.isVisible = false
                    binding.typeRadioGroup.isVisible = false
                    binding.recyclerView.isVisible = false
                    binding.errorMessage.isVisible = true
                    binding.errorMessage.text = state.message
                }

                HomeState.Loading -> {
                    binding.progress.isVisible = true
                    binding.searchView.isVisible = false
                    binding.typeRadioGroup.isVisible = false
                    binding.recyclerView.isVisible = false
                    binding.errorMessage.isVisible = false
                }

                is HomeState.Success -> {
                    binding.progress.isVisible = false
                    binding.searchView.isVisible = true
                    binding.typeRadioGroup.isVisible = true
                    binding.recyclerView.isVisible = true
                    binding.errorMessage.isVisible = false
                    initRecyclerView(state.workouts)
                }
            }
        }
    }

    private fun initRecyclerView(data: List<FitnessWorkout>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        val stateClickListener: WorkoutAdapter.OnStateClickListener =
            object : WorkoutAdapter.OnStateClickListener {
                override fun onStateClick(fitnessWorkout: FitnessWorkout, position: Int) {

                }
            }
        val adapter = WorkoutAdapter(data, stateClickListener, requireContext())
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}