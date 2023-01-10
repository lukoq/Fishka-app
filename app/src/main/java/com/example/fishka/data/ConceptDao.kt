package com.example.fishka.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fishka.model.Concept

@Dao
interface ConceptDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addConcept(concept: Concept)

    @Update
    suspend fun updateConcept(concept: Concept)

    @Query("SELECT * FROM concept_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Concept>>

    @Delete
    suspend fun deleteConcept(concept: Concept)

    @Query("DELETE FROM concept_table")
    suspend fun deleteAllConcepts()





}