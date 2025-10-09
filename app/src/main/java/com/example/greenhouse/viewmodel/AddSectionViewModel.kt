package com.example.greenhouse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenhouse.repository.SectionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout

class AddSectionViewModel : ViewModel() {
    private val repository: SectionRepository = SectionRepository()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _success = MutableStateFlow(false)
    val success = _success.asStateFlow()

    fun addSection(greenhouseId: String, sectionId: String, name: String, plant: String) {
        if (name.isBlank()) {
            _errorMessage.value = "Název sekce nesmí být prázdný."
            return
        }

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null
                withTimeout(10_000L) {
                    repository.addSection(greenhouseId,
                        sectionId?.ifBlank { null },
                        name,
                        plant)

                }
                _success.value = true
            } catch (e: TimeoutCancellationException) {
                _errorMessage.value = "Nepodařilo se přidat sekci – zkontrolujte připojení k internetu."
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}