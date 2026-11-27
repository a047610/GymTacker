package com.example.gymtrackermobile.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.gymtrackermobile.model.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ExerciseDao {

    @Query(
        "SELECT * FROM exercises " +
                "WHERE name LIKE '%' || :query || '%' " +
                "AND (:muscle IS NULL OR primaryMuscles LIKE '%\"' || :muscle || '\"%') " +
                "ORDER BY name ASC"
    )
    abstract fun observeExercises(query: String, muscle: String?): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE id IN (:ids)")
    abstract suspend fun getByIds(ids: List<String>): List<ExerciseEntity>

    @Query("SELECT COUNT(*) FROM exercises")
    abstract suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(exercises: List<ExerciseEntity>)

    @Query("DELETE FROM exercises")
    abstract suspend fun clearAll()

    @Transaction
    open suspend fun replaceAll(exercises: List<ExerciseEntity>) {
        clearAll()
        insertAll(exercises)
    }
}