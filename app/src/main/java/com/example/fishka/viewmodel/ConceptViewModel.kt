package com.example.fishka.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.fishka.data.ConceptDatabase
import com.example.fishka.repository.ConceptRepository
import com.example.fishka.model.Concept
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConceptViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Concept>>
    private val repository: ConceptRepository

    init {
        val conceptDao = ConceptDatabase.getDatabase(application).conceptDao()
        repository = ConceptRepository(conceptDao)
        readAllData = repository.readAllData
    }


    fun addConcept(concept: Concept){
        viewModelScope.launch(Dispatchers.IO){
            repository.addConcept(concept)
        }
    }
    fun updateConcept(concept: Concept){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateConcept(concept)
        }
    }
    fun deleteConcept(concept: Concept){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteConcept(concept)
        }
    }
    fun deleteAllConcepts(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllConcepts()
        }
    }
}