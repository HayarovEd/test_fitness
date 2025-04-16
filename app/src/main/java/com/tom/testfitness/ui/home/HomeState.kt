package com.tom.testfitness.ui.home

import com.tom.testfitness.domain.model.FitnessWorkout

sealed class HomeState {
    data object Loading : HomeState()
    class Error(val message: String) : HomeState()
    data class Success(
        val workouts: List<FitnessWorkout> = emptyList(),
        /*val typeFitness: TypeFitness = TypeFitness.TRENING,
        val query: String = ""*/
    ) : HomeState()
}