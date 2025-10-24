package com.example.lab8moviles.ui.locationdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.lab8moviles.data.local.AppDatabase
import com.example.lab8moviles.data.network.RetrofitClient
import com.example.lab8moviles.data.repository.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationDetailViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val locationRepository = LocationRepository(
        locationDao = database.locationDao(),
        apiService = RetrofitClient.apiService
    )

    private val _uiState = MutableStateFlow(LocationDetailUiState())
    val uiState: StateFlow<LocationDetailUiState> = _uiState.asStateFlow()

    private val locationId: Int = checkNotNull(savedStateHandle["locationId"])

    init {
        loadLocation()
    }

    fun loadLocation() {
        viewModelScope.launch {
            _uiState.value = LocationDetailUiState(isLoading = true)

            try {
                val location = locationRepository.getLocationById(locationId)

                if (location != null) {
                    _uiState.value = LocationDetailUiState(
                        isLoading = false,
                        data = location,
                        hasError = false
                    )
                } else {
                    _uiState.value = LocationDetailUiState(
                        isLoading = false,
                        data = null,
                        hasError = true
                    )
                }
            } catch (e: Exception) {
                _uiState.value = LocationDetailUiState(
                    isLoading = false,
                    data = null,
                    hasError = true
                )
            }
        }
    }
}