package com.tom.testfitness.data.mapper

import com.tom.testfitness.data.remote.FitnessVideoDto
import com.tom.testfitness.data.remote.FitnessWorkoutDto
import com.tom.testfitness.domain.model.FitnessVideo
import com.tom.testfitness.domain.model.FitnessWorkout
import com.tom.testfitness.domain.utils.BASE_URL
import com.tom.testfitness.domain.utils.converToType

fun FitnessWorkoutDto.convertToFitnessWorkout(): FitnessWorkout {
    return FitnessWorkout(
        description = description,
        duration = duration,
        id = id,
        title = title,
        type = type.converToType()
    )
}

fun FitnessVideoDto.convertToFitnessVideo(): FitnessVideo {
    return FitnessVideo(
        duration = duration,
        id = id,
        link = "$BASE_URL${link.drop(1)}"
    )
}