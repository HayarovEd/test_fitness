package com.tom.testfitness.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tom.testfitness.R
import com.tom.testfitness.databinding.FragmentHomeBinding
import com.tom.testfitness.domain.model.FitnessWorkout
import com.tom.testfitness.domain.model.TypeFitness
import com.tom.testfitness.domain.utils.BUNGLE_DESCRIPTION
import com.tom.testfitness.domain.utils.BUNGLE_ID
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
        viewModel.stateTypeFitness.observe(viewLifecycleOwner) { type ->
            when (type) {
                TypeFitness.TRENING -> binding.typeRadioGroup.check(binding.rbTraning.id)
                TypeFitness.AIR -> binding.typeRadioGroup.check(binding.rbAir.id)
                TypeFitness.COMPLEX -> binding.typeRadioGroup.check(binding.rbComplex.id)
                null -> {}
            }
        }
        binding.typeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbTraning.id -> viewModel.filterByType(TypeFitness.TRENING)
                binding.rbAir.id -> viewModel.filterByType(TypeFitness.AIR)
                binding.rbComplex.id -> viewModel.filterByType(TypeFitness.COMPLEX)
            }
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.filterByQuery(it)
                }
                return true
            }
        })
    }

    private fun initRecyclerView(data: List<FitnessWorkout>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        val stateClickListener: WorkoutAdapter.OnStateClickListener =
            object : WorkoutAdapter.OnStateClickListener {
                override fun onStateClick(fitnessWorkout: FitnessWorkout, position: Int) {
                    val bundle = bundleOf(
                        BUNGLE_ID to fitnessWorkout.id,
                        BUNGLE_DESCRIPTION to fitnessWorkout.description
                    )
                    requireView().findNavController()
                        .navigate(R.id.action_navigation_home_to_navigation_player, bundle)
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