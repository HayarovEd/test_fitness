package com.tom.testfitness.data.remote


import com.google.gson.annotations.SerializedName

data class FitnessVideoDto(
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("link")
    val link: String
)