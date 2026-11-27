package com.example.gymtrackermobile.model


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey val id: String,
    val name: String,
    val level: String?,
    val equipment: String?,
    val category: String?,
    val primaryMuscles: List<String>,
    val secondaryMuscles: List<String>,
    val instructions: List<String>,
    val images: List<String>
)