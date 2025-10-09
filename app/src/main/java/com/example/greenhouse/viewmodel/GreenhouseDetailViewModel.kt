package com.example.greenhouse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenhouse.model.Section
import com.example.greenhouse.repository.GreenhouseRepository
import com.example.greenhouse.repository.SectionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class GreenhouseDetailViewModel(
    private val sectionRepository: SectionRepository = SectionRepository(),
    private val greenhouseRepository: GreenhouseRepository = GreenhouseRepository()
) : ViewModel() {

    private val _sections = MutableStateFlow<List<Section>>(emptyList())
    private val _errorMessage = MutableStateFlow<String?>(null)
    val sections: StateFlow<List<Section>> = _sections
    val errorMessage = _errorMessage.asStateFlow()

    fun observeSections(greenhouseId: String) {
        viewModelScope.launch {
            sectionRepository.observeSections(greenhouseId) { list ->
                _sections.value = list
            }
        }
    }

    fun deleteGreenhouse(greenhouseId: String, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                greenhouseRepository.deleteGreenhouse(greenhouseId)
                onSuccess()
            } catch (e: Exception) {
                _errorMessage.value = "Nepodařilo se smazat skleník."
            }
        }
    }
}
