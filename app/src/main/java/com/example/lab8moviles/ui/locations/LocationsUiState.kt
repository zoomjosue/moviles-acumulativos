package com.example.lab8moviles.ui.locations

import com.example.lab8moviles.data.Location

data class LocationsUiState(
    val isLoading: Boolean = false,
    val data: List<Location> = emptyList(),
    val hasError: Boolean = false
)