package com.example.greenhouse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenhouse.repository.SectionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout

    class EditSectionViewModel : ViewModel() {
        private val repository: SectionRepository = SectionRepository()

        private val _isLoading = MutableStateFlow(false)
        val isLoading = _isLoading.asStateFlow()

        private val _errorMessage = MutableStateFlow<String?>(null)
        val errorMessage = _errorMessage.asStateFlow()

        private val _success = MutableStateFlow(false)
        val success = _success.asStateFlow()

        fun editSection(greenhouseId: String, sectionId: String, name: String, plant: String, water: String) {

            val waterInt = water.toIntOrNull()

            if (name.isBlank()) {
                _errorMessage.value = "Název sekce nesmí být prázdný."
                return
            }

            if (waterInt == null || waterInt < 0 || waterInt > 100) {
                _errorMessage.value = "Zadejte celé číslo. Platné hodnoty pro zalévání: 0–100."
                return
            }

            viewModelScope.launch {
                try {
                    _isLoading.value = true
                    _errorMessage.value = null

                    withTimeout(10_000L) {
                        repository.updateSection(
                            greenhouseId = greenhouseId,
                            sectionId = sectionId,
                            name = name,
                            plant = plant,
                            water = waterInt
                        )
                    }

                    _success.value = true
                } catch (e: TimeoutCancellationException) {
                    _errorMessage.value = "Nepodařilo se upravit sekci – zkontrolujte připojení k internetu."
                } catch (e: Exception) {
                    _errorMessage.value = e.message
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }
