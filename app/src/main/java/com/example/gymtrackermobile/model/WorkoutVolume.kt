package com.example.gymtrackermobile.model

val WorkoutSetEntity.volume: Double
    get() = weightKg * reps

fun List<WorkoutSetEntity>.totalVolume(): Double = sumOf { it.weightKg * it.reps }

val WorkoutWithSets.totalVolume: Double
    get() = sets.totalVolume()