package com.tom.testfitness.domain.repository

import com.tom.testfitness.domain.model.FitnessVideo
import com.tom.testfitness.domain.model.FitnessWorkout
import com.tom.testfitness.domain.utils.Resource

interface RemoteRepository {
    suspend fun getWorkouts(): Resource<List<FitnessWorkout>>
    suspend fun getVideoById(id: Int): Resource<FitnessVideo>
}