package com.example.lab8moviles.ui.locations

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab8moviles.data.local.AppDatabase
import com.example.lab8moviles.data.network.RetrofitClient
import com.example.lab8moviles.data.repository.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationsViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val locationRepository = LocationRepository(
        locationDao = database.locationDao(),
        apiService = RetrofitClient.apiService
    )

    private val _uiState = MutableStateFlow(LocationsUiState())
    val uiState: StateFlow<LocationsUiState> = _uiState.asStateFlow()

    init {
        loadLocations()
    }

    fun loadLocations() {
        viewModelScope.launch {
            _uiState.value = LocationsUiState(isLoading = true)

            try {
                val locations = locationRepository.getAllLocations()

                if (locations.isNotEmpty()) {
                    _uiState.value = LocationsUiState(
                        isLoading = false,
                        data = locations,
                        hasError = false
                    )
                } else {
                    _uiState.value = LocationsUiState(
                        isLoading = false,
                        data = emptyList(),
                        hasError = true
                    )
                }
            } catch (e: Exception) {
                _uiState.value = LocationsUiState(
                    isLoading = false,
                    data = emptyList(),
                    hasError = true
                )
            }
        }
    }
}