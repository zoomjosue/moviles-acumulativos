package com.example.lab8moviles.ui.characters

import com.example.lab8moviles.data.Character

data class CharactersUiState(
    val isLoading: Boolean = false,
    val data: List<Character> = emptyList(),
    val hasError: Boolean = false
)