package com.example.gymtrackermobile

data class WorkoutSummary(
    val id: Long,
    val startedAt: Long,
    val finishedAt: Long?,
    val totalVolume: Double,
    val setCount: Int,
    val exerciseCount: Int
)