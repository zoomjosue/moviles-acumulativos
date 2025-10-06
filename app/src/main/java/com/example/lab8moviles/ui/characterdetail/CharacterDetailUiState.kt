package com.example.lab8moviles.ui.characterdetail

import com.example.lab8moviles.data.Character

data class CharacterDetailUiState(
    val isLoading: Boolean = false,
    val data: Character? = null,
    val hasError: Boolean = false
)