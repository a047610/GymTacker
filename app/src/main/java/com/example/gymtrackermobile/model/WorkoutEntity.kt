package com.example.gymtrackermobile.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "workouts",
    indices = [Index("userId")]
)
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val name: String,
    val createdAt: Long,
    val updatedAt: Long = createdAt,
    val startedAt: Long? = null,
    val finishedAt: Long? = null,
    val remoteId: String? = null
)

enum class WorkoutStatus {
    DRAFT,
    IN_PROGRESS,
    COMPLETED
}

fun WorkoutEntity.status(): WorkoutStatus = when {
    finishedAt != null -> WorkoutStatus.COMPLETED
    startedAt != null -> WorkoutStatus.IN_PROGRESS
    else -> WorkoutStatus.DRAFT
}