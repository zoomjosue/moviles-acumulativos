package com.example.lab8moviles.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab8moviles.data.LocationDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class LocationsViewModel : ViewModel() {

    private val locationDb = LocationDb()

    private val _uiState = MutableStateFlow(LocationsUiState())
    val uiState: StateFlow<LocationsUiState> = _uiState.asStateFlow()

    init {
        loadLocations()
    }

    fun loadLocations() {
        viewModelScope.launch {
            _uiState.value = LocationsUiState(isLoading = true)

            // Simular delay de 4 segundos
            delay(4000)

            // Generar número aleatorio
            val randomNumber = Random.nextInt(1, 11)

            if (randomNumber % 2 == 0) {
                // Par: mostrar datos
                val locations = locationDb.getAllLocations()
                _uiState.value = LocationsUiState(
                    isLoading = false,
                    data = locations,
                    hasError = false
                )
            } else {
                // Impar: mostrar error
                _uiState.value = LocationsUiState(
                    isLoading = false,
                    data = emptyList(),
                    hasError = true
                )
            }
        }
    }
}