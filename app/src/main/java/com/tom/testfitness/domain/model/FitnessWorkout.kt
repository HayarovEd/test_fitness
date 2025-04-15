package com.tom.testfitness.domain.model


data class FitnessWorkout(
    val description: String?,
    val duration: Int,
    val id: Int,
    val title: String,
    val type: TypeFitness
)