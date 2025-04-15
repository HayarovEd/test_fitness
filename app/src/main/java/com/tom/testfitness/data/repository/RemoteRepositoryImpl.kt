package com.tom.testfitness.data.repository

import com.tom.testfitness.data.mapper.convertToFitnessVideo
import com.tom.testfitness.data.mapper.convertToFitnessWorkout
import com.tom.testfitness.data.remote.ApiFitness
import com.tom.testfitness.domain.model.FitnessVideo
import com.tom.testfitness.domain.model.FitnessWorkout
import com.tom.testfitness.domain.repository.RemoteRepository
import com.tom.testfitness.domain.utils.Resource
import com.tom.testfitness.domain.utils.UNKNOWN_ERROR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val api: ApiFitness
) : RemoteRepository {

    override suspend fun getWorkouts(): Resource<List<FitnessWorkout>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getWorkouts()
                Resource.Success(response.map { it.convertToFitnessWorkout() })
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage ?: UNKNOWN_ERROR)
            }
        }
    }

    override suspend fun getVideoById(id: Int): Resource<FitnessVideo> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getVideoInfo(id)
                Resource.Success(response.convertToFitnessVideo())
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage ?: UNKNOWN_ERROR)
            }
        }
    }
}