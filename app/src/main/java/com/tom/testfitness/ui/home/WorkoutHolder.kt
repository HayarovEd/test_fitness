package com.tom.testfitness.ui.home

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.tom.testfitness.R
import com.tom.testfitness.databinding.ItemWorkoutBinding
import com.tom.testfitness.domain.model.FitnessWorkout

class WorkoutHolder(
    private val binding: ItemWorkoutBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(fitnessWorkout: FitnessWorkout) {
        binding.titleTv.text = fitnessWorkout.title
        binding.descTv.text = fitnessWorkout.description
        binding.typeTv.text = "${context.getString(R.string.type)}: ${context.getString(fitnessWorkout.type.titleInt)}"
        binding.durationTv.text = "${context.getString(R.string.duration)}: ${fitnessWorkout.duration} ${context.getString(R.string.min_unit)}"
    }
}