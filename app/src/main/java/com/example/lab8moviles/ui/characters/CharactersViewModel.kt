package com.example.lab8moviles.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab8moviles.data.CharacterDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class CharactersViewModel : ViewModel() {

    private val characterDb = CharacterDb()

    private val _uiState = MutableStateFlow(CharactersUiState())
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            _uiState.value = CharactersUiState(isLoading = true)

            //Simular delay de 4 segundos
            delay(4000)

            //Número aleatorio
            val randomNumber = Random.nextInt(1, 11)

            if (randomNumber % 2 == 0) {
                // Par: mostrar datos
                val characters = characterDb.getAllCharacters()
                _uiState.value = CharactersUiState(
                    isLoading = false,
                    data = characters,
                    hasError = false
                )
            } else {
                // Impar: mostrar error
                _uiState.value = CharactersUiState(
                    isLoading = false,
                    data = emptyList(),
                    hasError = true
                )
            }
        }
    }
}
