package com.example.gymtrackermobile.model

import org.junit.Assert.assertEquals
import org.junit.Test

class WorkoutVolumeTest {

    private fun set(weight: Double, reps: Int) = WorkoutSetEntity(
        workoutId = 1,
        exerciseId = "bench_press",
        exerciseName = "Supino reto",
        setNumber = 1,
        weightKg = weight,
        reps = reps,
        createdAt = 0L
    )

    @Test
    fun volumeOfSingleSetIsWeightTimesReps() {
        assertEquals(800.0, set(80.0, 10).volume, 0.001)
    }

    @Test
    fun totalVolumeSumsAllSets() {
        val sets = listOf(set(80.0, 10), set(60.0, 12), set(100.0, 5))
        assertEquals(2020.0, sets.totalVolume(), 0.001)
    }

    @Test
    fun totalVolumeOfEmptyListIsZero() {
        assertEquals(0.0, emptyList<WorkoutSetEntity>().totalVolume(), 0.001)
    }
}