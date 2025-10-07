package com.example.greenhouse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenhouse.repository.GreenhouseRepository
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class AddGreenhouseViewModel(
    private val repository: GreenhouseRepository = GreenhouseRepository()
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _success = MutableStateFlow(false)
    val success = _success.asStateFlow()

    fun addGreenhouse(name: String) {
        if (name.isBlank()) {
            _errorMessage.value = "Název nesmí být prázdný."
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                withTimeout(10_000L) {
                    repository.addGreenhouse(name)
                }
                _success.value = true
            } catch (e: TimeoutCancellationException) {
                _errorMessage.value = "Nepodařilo se uložit skleník – zkontrolujte připojení."
            } catch (e: Exception) {
                _errorMessage.value = "Nepodařilo se uložit skleník. Zkuste to znovu."
            } finally {
                _isLoading.value = false
            }
        }
    }
}
