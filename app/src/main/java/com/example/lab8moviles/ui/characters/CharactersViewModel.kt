package com.example.lab8moviles.ui.characters

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab8moviles.data.local.AppDatabase
import com.example.lab8moviles.data.network.RetrofitClient
import com.example.lab8moviles.data.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharactersViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val characterRepository = CharacterRepository(
        characterDao = database.characterDao(),
        apiService = RetrofitClient.apiService
    )

    private val _uiState = MutableStateFlow(CharactersUiState())
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            _uiState.value = CharactersUiState(isLoading = true)

            try {
                val characters = characterRepository.getAllCharacters()

                if (characters.isNotEmpty()) {
                    _uiState.value = CharactersUiState(
                        isLoading = false,
                        data = characters,
                        hasError = false
                    )
                } else {
                    _uiState.value = CharactersUiState(
                        isLoading = false,
                        data = emptyList(),
                        hasError = true
                    )
                }
            } catch (e: Exception) {
                _uiState.value = CharactersUiState(
                    isLoading = false,
                    data = emptyList(),
                    hasError = true
                )
            }
        }
    }
}