package com.example.lab8moviles.ui.characterdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab8moviles.data.CharacterDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class CharacterDetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val characterDb = CharacterDb()

    private val _uiState = MutableStateFlow(CharacterDetailUiState())
    val uiState: StateFlow<CharacterDetailUiState> = _uiState.asStateFlow()

    // Obtener el ID del SavedStateHandle directamente
    private val characterId: Int = checkNotNull(savedStateHandle["characterId"])

    init {
        loadCharacter()
    }

    fun loadCharacter() {
        viewModelScope.launch {
            _uiState.value = CharacterDetailUiState(isLoading = true)

            // Simular delay de 2 segundos
            delay(2000)

            // Generar número aleatorio
            val randomNumber = Random.nextInt(1, 11)

            if (randomNumber % 2 == 0) {
                // Par: mostrar datos
                try {
                    val character = characterDb.getCharacterById(characterId)
                    _uiState.value = CharacterDetailUiState(
                        isLoading = false,
                        data = character,
                        hasError = false
                    )
                } catch (e: Exception) {
                    _uiState.value = CharacterDetailUiState(
                        isLoading = false,
                        data = null,
                        hasError = true
                    )
                }
            } else {
                // Impar: mostrar error
                _uiState.value = CharacterDetailUiState(
                    isLoading = false,
                    data = null,
                    hasError = true
                )
            }
        }
    }
}