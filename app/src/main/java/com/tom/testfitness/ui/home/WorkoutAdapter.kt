package com.tom.testfitness.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tom.testfitness.databinding.ItemWorkoutBinding
import com.tom.testfitness.domain.model.FitnessWorkout

class WorkoutAdapter(
    private val workouts: List<FitnessWorkout>,
    private val onClickListener: OnStateClickListener,
    private val context: Context
) : RecyclerView.Adapter<WorkoutHolder>() {

    interface OnStateClickListener {
        fun onStateClick(fitnessWorkout: FitnessWorkout, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutHolder {
        val inflater = LayoutInflater.from(parent.context)
        return WorkoutHolder(ItemWorkoutBinding.inflate(inflater, parent, false), context)
    }

    override fun getItemCount(): Int {
        return workouts.size
    }

    override fun onBindViewHolder(holder: WorkoutHolder, position: Int) {
        val item: FitnessWorkout = workouts[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickListener.onStateClick(item, position)
        }
    }
}