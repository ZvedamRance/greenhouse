package com.example.greenhouse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenhouse.model.Section
import com.example.greenhouse.repository.SectionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GreenhouseDetailViewModel(
    private val repository: SectionRepository = SectionRepository()
) : ViewModel() {

    private val _sections = MutableStateFlow<List<Section>>(emptyList())
    val sections: StateFlow<List<Section>> = _sections

    fun observeSections(greenhouseId: String) {
        viewModelScope.launch {
            repository.observeSections(greenhouseId) { list ->
                _sections.value = list
            }
        }
    }
}
