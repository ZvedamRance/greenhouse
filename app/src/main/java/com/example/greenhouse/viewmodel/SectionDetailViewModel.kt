package com.example.greenhouse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenhouse.model.Section
import com.example.greenhouse.repository.SectionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SectionDetailViewModel(
    private val repository: SectionRepository = SectionRepository()

) : ViewModel() {

    private val _section = MutableStateFlow<Section?>(null)
    val section: StateFlow<Section?> = _section
    private val _errorMessage = MutableStateFlow<String?>(null)

    val errorMessage = _errorMessage.asStateFlow()

    private val _moisture = MutableStateFlow<Float?>(null)
    val moisture: StateFlow<Float?> = _moisture

    fun observeSection(greenhouseId: String, sectionId: String) {
        repository.getSectionByID(greenhouseId, sectionId) { sec ->
            _section.value = sec
        }
    }
    fun deleteSection(greenhouseId: String, sectionId: String, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                repository.deleteSection(greenhouseId, sectionId)
                onSuccess()
            } catch (e: Exception) {
                _errorMessage.value = "Nepodařilo se smazat sekci."
            }
        }
    }

    fun observeMoisture(greenhouseId: String, sectionId: String) {
        repository.observeLatestMoisture(greenhouseId, sectionId) { value ->
            _moisture.value = value
        }
    }

}


