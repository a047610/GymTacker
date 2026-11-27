package com.example.gymtrackermobile.model

data class WorkoutSummary(
    val id: Long,
    val name: String,
    val createdAt: Long,
    val updatedAt: Long,
    val startedAt: Long,
    val finishedAt: Long?,
    val totalVolume: Double,
    val setCount: Int,
    val exerciseCount: Int
)