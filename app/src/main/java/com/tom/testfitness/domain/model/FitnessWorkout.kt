package com.tom.testfitness.domain.model


data class FitnessWorkout(
    val description: String?,
    val duration: String,
    val id: Int,
    val title: String,
    val type: TypeFitness
)