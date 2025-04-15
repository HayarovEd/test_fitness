package com.tom.testfitness.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tom.testfitness.domain.model.FitnessWorkout
import com.tom.testfitness.domain.model.TypeFitness
import com.tom.testfitness.domain.repository.RemoteRepository
import com.tom.testfitness.domain.utils.Resource
import com.tom.testfitness.domain.utils.UNKNOWN_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RemoteRepository
) : ViewModel() {

    private val fullWorkouts = mutableListOf<FitnessWorkout>()

    private val _state = MutableLiveData<HomeState>(HomeState.Loading)
    val state = _state

    init {
        getWorkouts()
    }

    private fun getWorkouts() {
        viewModelScope.launch {
            when (val result = repository.getWorkouts()) {
                is Resource.Error -> {
                    _state.value = HomeState.Error(result.message ?: UNKNOWN_ERROR)
                }

                is Resource.Success -> {
                    fullWorkouts.clear()
                    fullWorkouts.addAll(result.data ?: emptyList())
                    _state.value = HomeState.Success(
                        workouts = result.data ?: emptyList(),
                    )
                }
            }
        }
    }

    fun filterByQuery(query: String) {
        _state.value = (state.value as HomeState.Success).copy(
            query = query
        )
        filterAll()
    }

    fun filterByType(typeFitness: TypeFitness) {
        _state.value = (state.value as HomeState.Success).copy(
            typeFitness = typeFitness
        )
        filterAll()
    }

    private fun filterAll() {
        val query = (state.value as HomeState.Success).query
        val typeFitness = (state.value as HomeState.Success).typeFitness
        _state.value = (state.value as HomeState.Success).copy(
            workouts = fullWorkouts.filter {
                it.title
                    .contains(
                        other = query,
                        ignoreCase = true
                    ) || it.type == typeFitness
            }
        )
    }
}