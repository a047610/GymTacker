package com.example.gymtrackermobile.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.gymtrackermobile.model.UserStats
import com.example.gymtrackermobile.model.WorkoutEntity
import com.example.gymtrackermobile.model.WorkoutSetEntity
import com.example.gymtrackermobile.model.WorkoutSummary
import com.example.gymtrackermobile.model.WorkoutWithSets
import kotlinx.coroutines.flow.Flow

private const val SUMMARY_SELECT =
    "SELECT w.id AS id, " +
            "w.name AS name, " +
            "w.createdAt AS createdAt, " +
            "w.updatedAt AS updatedAt, " +
            "w.startedAt AS startedAt, " +
            "w.finishedAt AS finishedAt, " +
            "COALESCE(SUM(s.weightKg * s.reps), 0.0) AS totalVolume, " +
            "COUNT(s.id) AS setCount, " +
            "COUNT(DISTINCT s.exerciseId) AS exerciseCount " +
            "FROM workouts w " +
            "LEFT JOIN workout_sets s ON s.workoutId = w.id "

@Dao
abstract class WorkoutDao {

    // ---------- Treinos ----------

    @Insert
    abstract suspend fun insertWorkout(workout: WorkoutEntity): Long

    @Update
    abstract suspend fun updateWorkout(workout: WorkoutEntity)

    @Query("DELETE FROM workouts WHERE id = :workoutId AND userId = :userId")
    abstract suspend fun deleteWorkout(workoutId: Long, userId: String)

    @Query("DELETE FROM workouts WHERE userId = :userId")
    abstract suspend fun deleteAllForUser(userId: String)

    @Query("UPDATE workouts SET startedAt = :startedAt, updatedAt = :startedAt WHERE id = :workoutId AND startedAt IS NULL")
    abstract suspend fun startWorkout(workoutId: Long, startedAt: Long)

    @Query("UPDATE workouts SET finishedAt = :finishedAt, updatedAt = :finishedAt WHERE id = :workoutId")
    abstract suspend fun finishWorkout(workoutId: Long, finishedAt: Long)

    @Query("UPDATE workouts SET remoteId = :remoteId WHERE id = :workoutId")
    abstract suspend fun setRemoteId(workoutId: Long, remoteId: String)

    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    abstract suspend fun getWorkoutById(workoutId: Long): WorkoutEntity?

    @Query("SELECT * FROM workouts WHERE userId = :userId AND remoteId = :remoteId LIMIT 1")
    abstract suspend fun getByRemoteId(userId: String, remoteId: String): WorkoutEntity?

    // ---------- Series ----------

    @Insert
    abstract suspend fun insertSet(set: WorkoutSetEntity): Long

    @Insert
    abstract suspend fun insertSets(sets: List<WorkoutSetEntity>)

    @Update
    abstract suspend fun updateSet(set: WorkoutSetEntity)

    @Delete
    abstract suspend fun deleteSet(set: WorkoutSetEntity)

    @Query("DELETE FROM workout_sets WHERE workoutId = :workoutId")
    abstract suspend fun deleteSetsForWorkout(workoutId: Long)

    @Query("DELETE FROM workout_sets WHERE workoutId = :workoutId AND exerciseId = :exerciseId")
    abstract suspend fun deleteSetsForExercise(workoutId: Long, exerciseId: String)

    // ---------- Leitura com series ----------

    @Transaction
    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    abstract fun observeWorkoutWithSets(workoutId: Long): Flow<WorkoutWithSets?>

    @Transaction
    @Query("SELECT * FROM workouts WHERE userId = :userId ORDER BY updatedAt DESC")
    abstract suspend fun getAllWithSets(userId: String): List<WorkoutWithSets>

    // ---------- Listas e metricas ----------

    @Query(SUMMARY_SELECT + "WHERE w.userId = :userId GROUP BY w.id ORDER BY w.updatedAt DESC")
    abstract fun observeSummaries(userId: String): Flow<List<WorkoutSummary>>

    @Query(
        SUMMARY_SELECT +
                "WHERE w.userId = :userId AND w.finishedAt IS NOT NULL " +
                "GROUP BY w.id ORDER BY w.finishedAt DESC"
    )
    abstract fun observeCompletedSummaries(userId: String): Flow<List<WorkoutSummary>>

    @Query(
        SUMMARY_SELECT +
                "WHERE w.userId = :userId AND w.finishedAt IS NOT NULL " +
                "GROUP BY w.id ORDER BY w.finishedAt DESC LIMIT 1"
    )
    abstract fun observeLastCompletedSummary(userId: String): Flow<WorkoutSummary?>

    @Query(
        "SELECT COUNT(DISTINCT w.id) AS completedWorkouts, " +
                "COALESCE(SUM(s.weightKg * s.reps), 0.0) AS totalVolume " +
                "FROM workouts w " +
                "LEFT JOIN workout_sets s ON s.workoutId = w.id " +
                "WHERE w.userId = :userId AND w.finishedAt IS NOT NULL"
    )
    abstract fun observeUserStats(userId: String): Flow<UserStats>

    @Query("SELECT COALESCE(SUM(weightKg * reps), 0.0) FROM workout_sets WHERE workoutId = :workoutId")
    abstract fun observeWorkoutVolume(workoutId: Long): Flow<Double>

    // ---------- Guardar tudo de uma vez ----------

    @Transaction
    open suspend fun saveWorkoutWithSets(
        workout: WorkoutEntity,
        sets: List<WorkoutSetEntity>
    ): Long {
        val workoutId = if (workout.id == 0L) {
            insertWorkout(workout)
        } else {
            updateWorkout(workout)
            workout.id
        }
        deleteSetsForWorkout(workoutId)
        if (sets.isNotEmpty()) {
            insertSets(sets.map { it.copy(id = 0, workoutId = workoutId) })
        }
        return workoutId
    }
}