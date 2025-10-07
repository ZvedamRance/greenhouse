package com.example.greenhouse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenhouse.model.Greenhouse
import com.example.greenhouse.repository.GreenhouseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val repository = GreenhouseRepository()

    private val _greenhouses = MutableStateFlow<List<Greenhouse>>(emptyList())
    val greenhouses: StateFlow<List<Greenhouse>> = _greenhouses

    init {
        observeGreenhouses()
    }
    private fun observeGreenhouses() {
        repository.observeGreenhouses { list ->
            _greenhouses.value = list
        }
    }
}
