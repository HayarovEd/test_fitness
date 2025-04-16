package com.tom.testfitness.data.remote


import com.google.gson.annotations.SerializedName

data class FitnessWorkoutDto(
    @SerializedName("description")
    val description: String?,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: Int
)