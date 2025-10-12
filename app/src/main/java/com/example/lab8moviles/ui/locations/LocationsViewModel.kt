package com.example.lab8moviles.ui.locations

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab8moviles.data.local.AppDatabase
import com.example.lab8moviles.data.repository.LocationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class LocationsViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val locationRepository = LocationRepository(database.locationDao())

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
                //Mostrar datos desde Room
                try {
                    val locations = locationRepository.getAllLocations()
                    _uiState.value = LocationsUiState(
                        isLoading = false,
                        data = locations,
                        hasError = false
                    )
                } catch (e: Exception) {
                    _uiState.value = LocationsUiState(
                        isLoading = false,
                        data = emptyList(),
                        hasError = true
                    )
                }
            } else {
                //Mostrar error
                _uiState.value = LocationsUiState(
                    isLoading = false,
                    data = emptyList(),
                    hasError = true
                )
            }
        }
    }
}