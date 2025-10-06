package com.example.lab8moviles.ui.locationdetail

import com.example.lab8moviles.data.Location

data class LocationDetailUiState(
    val isLoading: Boolean = false,
    val data: Location? = null,
    val hasError: Boolean = false
)