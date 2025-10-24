package com.example.lab8moviles.ui.characterdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.lab8moviles.data.local.AppDatabase
import com.example.lab8moviles.data.network.RetrofitClient
import com.example.lab8moviles.data.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val characterRepository = CharacterRepository(
        characterDao = database.characterDao(),
        apiService = RetrofitClient.apiService
    )

    private val _uiState = MutableStateFlow(CharacterDetailUiState())
    val uiState: StateFlow<CharacterDetailUiState> = _uiState.asStateFlow()

    private val characterId: Int = checkNotNull(savedStateHandle["characterId"])

    init {
        loadCharacter()
    }

    fun loadCharacter() {
        viewModelScope.launch {
            _uiState.value = CharacterDetailUiState(isLoading = true)

            try {
                val character = characterRepository.getCharacterById(characterId)

                if (character != null) {
                    _uiState.value = CharacterDetailUiState(
                        isLoading = false,
                        data = character,
                        hasError = false
                    )
                } else {
                    _uiState.value = CharacterDetailUiState(
                        isLoading = false,
                        data = null,
                        hasError = true
                    )
                }
            } catch (e: Exception) {
                _uiState.value = CharacterDetailUiState(
                    isLoading = false,
                    data = null,
                    hasError = true
                )
            }
        }
    }
}