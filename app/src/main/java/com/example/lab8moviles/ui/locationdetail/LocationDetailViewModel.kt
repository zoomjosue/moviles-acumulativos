package com.example.lab8moviles.ui.locationdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab8moviles.data.LocationDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class LocationDetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val locationDb = LocationDb()

    private val _uiState = MutableStateFlow(LocationDetailUiState())
    val uiState: StateFlow<LocationDetailUiState> = _uiState.asStateFlow()

    // Obtener el ID del SavedStateHandle directamente
    private val locationId: Int = checkNotNull(savedStateHandle["locationId"])

    init {
        loadLocation()
    }

    fun loadLocation() {
        viewModelScope.launch {
            _uiState.value = LocationDetailUiState(isLoading = true)

            // Simular delay de 2 segundos
            delay(2000)

            // Generar número aleatorio
            val randomNumber = Random.nextInt(1, 11)

            if (randomNumber % 2 == 0) {
                // Par: mostrar datos
                try {
                    val location = locationDb.getLocationById(locationId)
                    _uiState.value = LocationDetailUiState(
                        isLoading = false,
                        data = location,
                        hasError = false
                    )
                } catch (e: Exception) {
                    _uiState.value = LocationDetailUiState(
                        isLoading = false,
                        data = null,
                        hasError = true
                    )
                }
            } else {
                // Impar: mostrar error
                _uiState.value = LocationDetailUiState(
                    isLoading = false,
                    data = null,
                    hasError = true
                )
            }
        }
    }
}