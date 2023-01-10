package com.example.fishka.repository

import androidx.lifecycle.LiveData
import com.example.fishka.data.ConceptDao
import com.example.fishka.model.Concept

class ConceptRepository(private val conceptDao: ConceptDao) {
    val readAllData: LiveData<List<Concept>> = conceptDao.readAllData()

    suspend fun addConcept(concept: Concept){
        conceptDao.addConcept(concept)
    }
    suspend fun updateConcept(concept: Concept){
        conceptDao.updateConcept(concept)
    }

    suspend fun deleteConcept(concept: Concept){
        conceptDao.deleteConcept(concept)
    }

    suspend fun deleteAllConcepts(){
        conceptDao.deleteAllConcepts()
    }



}