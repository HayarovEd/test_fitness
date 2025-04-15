package com.tom.testfitness.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiFitness {
    @GET("get_workouts")
    suspend fun getWorkouts(
    ): List<FitnessWorkoutDto>

    @GET("get_video")
    suspend fun getVideoInfo(
        @Query("name") id: Int
    ): FitnessVideoDto
}