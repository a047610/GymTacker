package com.example.gymtrackermobile.model

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutWithSets(
    @Embedded val workout: WorkoutEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "workoutId"
    )
    val sets: List<WorkoutSetEntity>
)